package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DebugHelper {
	
	public static void blockLogger(BlockPos pos, World world) {
		
		ScruffysMachines.LOGGER.info(world.getBlockState(pos).getBlock().getNameTextComponent().getFormattedText());
	}
	
	public static void blockStateLogger(BlockPos pos, World world) {
		
		blockLogger(pos, world);
	}
	
	public static void posLogger(BlockPos pos) {
		
		ScruffysMachines.LOGGER.info("X :" + pos.getX() + " Y: " + pos.getY() + " Z: " + pos.getZ());
	}
}
