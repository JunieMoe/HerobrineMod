package net.junie.herobrinemod.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        //this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0D, 40, 20.0F));

        //gets hurt
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));

        //looking goals
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
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
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.ARMOR, 4.0D);
    }
}