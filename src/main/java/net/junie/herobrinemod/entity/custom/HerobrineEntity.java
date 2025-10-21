package net.junie.herobrinemod.entity.custom;

import net.junie.herobrinemod.sound.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;


public class HerobrineEntity extends Monster {
    public HerobrineEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        this.setHealth(this.getMaxHealth());
        this.xpReward = 50;
    }

    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR = (p_31504_) -> {
        return p_31504_.getMobType() != MobType.UNDEAD && p_31504_.attackable();
    };
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forCombat().range(10.0D).selector(LIVING_ENTITY_SELECTOR);


    @Override
    protected void registerGoals() {
        //avoid drowning
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.6f));

        //attack player
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 0, false, false, LIVING_ENTITY_SELECTOR));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3,new FollowBrotherGoal(this, 0.6D, 2f, 40f));
        //this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));

        //gets hurt
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));

        //looking goals
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    private int musicTimer = 0;
    private final int MUSIC_LENGTH_TICKS = 20 * 171; //length of track in ticks (assuming player is running at 20tps, this is the best solution i got)
    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) return;

        this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(40.0D))
                .forEach(player -> {
                    musicTimer++;

                    // Replay music every time it ends
                    if (musicTimer >= MUSIC_LENGTH_TICKS) {
                        musicTimer = 0;
                        player.getPersistentData().putBoolean("herobrine_music_playing", true);
                        player.playNotifySound(ModSounds.HEROBRINE_BOSS_THEME.get(), SoundSource.MUSIC, 1.0F, 1.0F);
                    }

                    // If not already playing music
                    if (!player.getPersistentData().getBoolean("herobrine_music_playing")) {
                        player.getPersistentData().putBoolean("herobrine_music_playing", true);
                        player.playNotifySound(ModSounds.HEROBRINE_BOSS_THEME.get(), SoundSource.MUSIC, 1.0F, 1.0F);
                    }
                });

        // Stop music if player moves away
        this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(60.0D))
                .forEach(player -> {
                    double distance = player.distanceTo(this);
                    if (distance > 50.0D) {
                        player.getPersistentData().putBoolean("herobrine_music_playing", false);
                    }
                });

    }

    @Override
    public void die(DamageSource source) {
        super.die(source);

        // Stop music when Herobrine dies
        this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(60.0D))
                .forEach(player -> player.getPersistentData().putBoolean("herobrine_music_playing", false));
    }


    static class FollowBrotherGoal extends Goal {
        private final HerobrineEntity herobrine;
        @Nullable
        private BrotherEntity targetBrother;
        private final double speedModifier;
        private final float stopDistance;
        private final float followRadius;

        public FollowBrotherGoal(HerobrineEntity pHerobrine, double pSpeed, float pStopDistance, float pFollowRadius) {
            this.herobrine = pHerobrine;
            this.speedModifier = pSpeed;
            this.stopDistance = pStopDistance;
            this.followRadius = pFollowRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            // Find nearest MikuEntity within radius
            List<BrotherEntity> nearby = this.herobrine.level().getEntitiesOfClass(
                    BrotherEntity.class,
                    this.herobrine.getBoundingBox().inflate(followRadius)
            );
            if (nearby.isEmpty()) {
                return false;
            } else {
                this.targetBrother = nearby.get(0);
                return this.herobrine.distanceToSqr(targetBrother) > (stopDistance * stopDistance);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return targetBrother != null
                    && targetBrother.isAlive()
                    && this.herobrine.distanceToSqr(targetBrother) > (stopDistance * stopDistance)
                    && this.herobrine.distanceToSqr(targetBrother) < (followRadius * followRadius);
        }

        @Override
        public void tick() {
            if (targetBrother != null && targetBrother.isAlive()) {
                this.herobrine.getLookControl().setLookAt(targetBrother, 10.0F, this.herobrine.getMaxHeadXRot());
                this.herobrine.getNavigation().moveTo(targetBrother, speedModifier);
            }
        }

        @Override
        public void stop() {
            this.targetBrother = null;
            this.herobrine.getNavigation().stop();
        }
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }

    }

    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
        this.bossEvent.setName(this.getDisplayName());
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource)
    {
        return SoundEvents.PILLAGER_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.PILLAGER_DEATH;
    }

    protected void customServerAiStep() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    public void startSeenByPlayer(ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
    }

    public void stopSeenByPlayer(ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        this.bossEvent.removePlayer(pPlayer);
    }

    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        super.dropCustomDeathLoot(pSource, pLooting, pRecentlyHit);
        ItemEntity itementity = this.spawnAtLocation(Items.ENCHANTED_GOLDEN_APPLE);
        if (itementity != null) {
            itementity.setExtendedLifetime();
        }

    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

   protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
   }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.4D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.ARMOR, 4.0D);
    }
}