package net.junie.herobrinemod.events;

import net.junie.herobrinemod.HerobrineMod;
import net.junie.herobrinemod.registry.ModEntities;
import net.junie.herobrinemod.registry.ModBlocks;
import net.junie.herobrinemod.entity.HerobrineEntity;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles detecting the Herobrine ritual structure and spawning the entity.
 * Compatible with Parchment mappings (no BlockPattern usage).
 */
@Mod.EventBusSubscriber(modid = "herobrinemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Level level = (Level) event.getLevel();
        if (level.isClientSide()) return; // Only run on server side

        BlockState placedState = event.getPlacedBlock();
        BlockPos placedPos = event.getPos();

        // Only react when fire is placed
        if (!placedState.is(Blocks.FIRE)) return;

        // Check that the fire is placed on top of the Ghastly Totem
        BlockPos below = placedPos.below();
        BlockState belowState = level.getBlockState(below);
        if (!belowState.is(ModBlocks.GHASTLY_TOTEM.get())) return;

        // Check if the ritual structure is valid
        if (!isValidHerobrineStructure(level, below)) return;

        // Clear the ritual and spawn Herobrine
        clearStructure(level, below);

        if (level instanceof ServerLevel serverLevel) {
            spawnHerobrine(serverLevel, below.above());
        }
    }

    private static boolean isValidHerobrineStructure(Level level, BlockPos totemPos) {
        int baseY = totemPos.getY() - 1;

        // Check 3x3 gold block base
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos basePos = new BlockPos(totemPos.getX() + dx, baseY, totemPos.getZ() + dz);
                if (!level.getBlockState(basePos).is(Blocks.GOLD_BLOCK)) {
                    return false;
                }
            }
        }

        if (!level.getBlockState(totemPos.north()).is(Blocks.REDSTONE_TORCH)) return false;
        if (!level.getBlockState(totemPos.south()).is(Blocks.REDSTONE_TORCH)) return false;
        if (!level.getBlockState(totemPos.east()).is(Blocks.REDSTONE_TORCH)) return false;
        if (!level.getBlockState(totemPos.west()).is(Blocks.REDSTONE_TORCH)) return false;

        return true;
    }

    private static void clearStructure(Level level, BlockPos totemPos) {
        int baseY = totemPos.getY() - 1;


        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos basePos = new BlockPos(totemPos.getX() + dx, baseY, totemPos.getZ() + dz);
                level.setBlock(basePos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        level.setBlock(totemPos.north(), Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(totemPos.south(), Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(totemPos.east(), Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(totemPos.west(), Blocks.AIR.defaultBlockState(), 3);

        // Remove totem and fire
        level.setBlock(totemPos, Blocks.AIR.defaultBlockState(), 3);
        level.setBlock(totemPos.above(), Blocks.AIR.defaultBlockState(), 3);
    }

    private static void spawnHerobrine(ServerLevel serverLevel, BlockPos spawnPos) {
        HerobrineEntity herobrine = (HerobrineEntity) ModEntities.HEROBRINE.get().create(serverLevel);
        if (herobrine != null) {
            herobrine.moveTo(
                    spawnPos.getX() + 0.5,
                    spawnPos.getY(),
                    spawnPos.getZ() + 0.5,
                    serverLevel.random.nextFloat() * 360F,
                    0.0F
            );
            serverLevel.addFreshEntity(herobrine);
        }
    }
}