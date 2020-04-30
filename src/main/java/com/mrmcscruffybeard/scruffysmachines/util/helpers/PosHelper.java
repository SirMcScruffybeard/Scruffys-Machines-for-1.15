package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class PosHelper {

	public static BlockPos getAbovePos(TileEntity tile) {

		return new BlockPos(tile.getPos().getX(), tile.getPos().getY() + 1, tile.getPos().getZ());
	}

	public static BlockPos getBelowPos(TileEntity tile) {

		return new BlockPos(tile.getPos().getX(), tile.getPos().getY() - 1, tile.getPos().getZ());
	}
	
}
