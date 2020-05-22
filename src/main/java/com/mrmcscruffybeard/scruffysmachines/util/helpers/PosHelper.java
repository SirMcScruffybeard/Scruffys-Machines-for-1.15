package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PosHelper {

	public static boolean isAirAtPos(final BlockPos pos, final World world) {

		return isValidBlockAtPos(pos, world, Blocks.AIR);
	}

	public static boolean isValidBlockAtPos(final BlockPos pos, final World world, Block block) {

		return world.getBlockState(pos) == block.getDefaultState();
	}

	public static boolean isValidBlockAtPos(final BlockPos pos, final World world, Block block1, Block block2) {

		return isValidBlockAtPos(pos, world, block1) || isValidBlockAtPos(pos, world, block2);
	}

	public static BlockPos goUp(BlockPos pos) {

		return pos.add(Direction.UP.getDirectionVec());
	}

	public static BlockPos goDown(BlockPos pos) {

		return pos.add(Direction.DOWN.getDirectionVec());
	}
	
	public static TileEntity getTileEntityFromDirction(Direction dir, World world, BlockPos pos) {
		
		return world.getTileEntity(pos.add(dir.getDirectionVec()));
	}
}
