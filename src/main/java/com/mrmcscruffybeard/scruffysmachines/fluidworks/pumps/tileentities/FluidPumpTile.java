package com.mrmcscruffybeard.scruffysmachines.fluidworks.pumps.tileentities;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.FluidWorkerTile;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.pumps.IFluidPump;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class FluidPumpTile extends FluidWorkerTile implements IFluidPump{

	public FluidPumpTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {
		super(tileEntityTypeIn, capacityIn);
		
	}

	protected int draw(FluidStack resource, FluidAction action) {
		
		return holder.fill(resource, action);
	}
	
	protected FluidStack pump (int maxPump, FluidAction action) {
		
		return holder.drain(maxPump, action);
	}
	
	@Override
	public FluidStack pumpOut(BlockPos pos, World world, Direction dir) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
