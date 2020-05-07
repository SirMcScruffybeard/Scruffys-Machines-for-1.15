package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PosHelper {

	public static BlockPos getAbovePos(TileEntity tile) {

		return new BlockPos(tile.getPos().getX(), tile.getPos().getY() + 1, tile.getPos().getZ());
	}

	public static BlockPos getBelowPos(TileEntity tile) {

		return new BlockPos(tile.getPos().getX(), tile.getPos().getY() - 1, tile.getPos().getZ());
	}

	public static boolean isAirAtPos(final BlockPos pos, final World world) {

		return isValidBlockAtPos(pos, world, Blocks.AIR);
	}
	
	public static boolean isValidBlockAtPos(final BlockPos pos, final World world, Block block) {
		
		return world.getBlockState(pos) == block.getDefaultState();
	}
}
