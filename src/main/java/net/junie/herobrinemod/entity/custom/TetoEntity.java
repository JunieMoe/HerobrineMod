package net.junie.herobrinemod.entity.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;


public class TetoEntity extends Monster {
    public TetoEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8f, 0.7D, 1.5D));
        this.goalSelector.addGoal(2, new FollowMikuGoal(this, 0.5D, 2.0F, 20.0F));
        this.goalSelector.addGoal(3, new EndermanFreezeWhenLookedAt(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Endermite.class, true, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    boolean isLookingAtMe(Player pPlayer) {
        Vec3 vec3 = pPlayer.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - pPlayer.getX(), this.getEyeY() - pPlayer.getEyeY(), this.getZ() - pPlayer.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 > 1.0D - 0.025D / d0 ? pPlayer.hasLineOfSight(this) : false;
    }

    static class FollowMikuGoal extends Goal {
        private final TetoEntity teto;
        @Nullable
        private MikuEntity targetMiku;
        private final double speedModifier;
        private final float stopDistance;
        private final float followRadius;

        public FollowMikuGoal(TetoEntity pTeto, double pSpeed, float pStopDistance, float pFollowRadius) {
            this.teto = pTeto;
            this.speedModifier = pSpeed;
            this.stopDistance = pStopDistance;
            this.followRadius = pFollowRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            // Find nearest MikuEntity within radius
            List<MikuEntity> nearby = this.teto.level().getEntitiesOfClass(
                    MikuEntity.class,
                    this.teto.getBoundingBox().inflate(followRadius)
            );
            if (nearby.isEmpty()) {
                return false;
            } else {
                this.targetMiku = nearby.get(0);
                return this.teto.distanceToSqr(targetMiku) > (stopDistance * stopDistance);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return targetMiku != null
                    && targetMiku.isAlive()
                    && this.teto.distanceToSqr(targetMiku) > (stopDistance * stopDistance)
                    && this.teto.distanceToSqr(targetMiku) < (followRadius * followRadius);
        }

        @Override
        public void tick() {
            if (targetMiku != null && targetMiku.isAlive()) {
                this.teto.getLookControl().setLookAt(targetMiku, 10.0F, this.teto.getMaxHeadXRot());
                this.teto.getNavigation().moveTo(targetMiku, speedModifier);
            }
        }

        @Override
        public void stop() {
            this.targetMiku = null;
            this.teto.getNavigation().stop();
        }
    }


    static class EndermanFreezeWhenLookedAt extends Goal {
        private final TetoEntity enderman;
        @Nullable
        private LivingEntity target;

        public EndermanFreezeWhenLookedAt(TetoEntity pEnderman) {
            this.enderman = pEnderman;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            this.target = this.enderman.getTarget();
            if (!(this.target instanceof Player)) {
                return false;
            } else {
                double d0 = this.target.distanceToSqr(this.enderman);
                return d0 > 256.0D ? false : this.enderman.isLookingAtMe((Player)this.target);
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            this.enderman.getNavigation().stop();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }
}