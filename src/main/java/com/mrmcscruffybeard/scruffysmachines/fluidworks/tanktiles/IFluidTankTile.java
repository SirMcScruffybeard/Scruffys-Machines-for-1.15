package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanktiles;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.IFluidWorker;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public interface IFluidTankTile extends IFluidWorker{

	int fillFromSide(FluidStack resource, FluidAction action, Direction dir);

	FluidStack drainFromSide(int maxDrain, FluidAction action, Direction dir);

	FluidStack drainFromSide(FluidStack resource, FluidAction action, Direction dir);

	boolean canSpill();

	boolean canFillWithBucket();

	boolean canDrainWithBucket();

	void spill(BlockPos pos, World worldIn);

	void drainWithBucket();

	void fillWithBucket();

	static boolean isFluidTankTile(TileEntity tileIn) {
		
		return tileIn instanceof FluidTankTile;
	}
	
	static boolean isFluidTankAtPos(BlockPos pos, World world) {
		
		return isFluidTankTile(world.getTileEntity(pos));
	}
}
