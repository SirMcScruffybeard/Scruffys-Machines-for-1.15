package com.mrmcscruffybeard.scruffysmachines.fluidworks.pumps;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.IFluidWorker;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidPump extends IFluidWorker {

	int drawFromSource(BlockPos pos, World world, Direction dir);
	
	FluidStack pumpOut(BlockPos pos, World world, Direction dir);
	
	
	
}
