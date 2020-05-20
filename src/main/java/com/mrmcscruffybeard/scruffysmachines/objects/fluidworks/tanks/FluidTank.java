package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.FluidWorker;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.IFluidWorkerHandler.FluidAction;

import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public class FluidTank extends FluidWorker implements IFluidTank {

	public FluidTank(int capacityIn) {
		
		super(capacityIn);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action, Direction dir) {
		
		return super.fillFromDirection(resource, action, dir);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action, Direction dir) {
		
		return super.drainFromDirection(maxDrain, action, dir);
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action, Direction dir) {
		
		return super.drainFromDirection(resource, action, dir);
	}

}
