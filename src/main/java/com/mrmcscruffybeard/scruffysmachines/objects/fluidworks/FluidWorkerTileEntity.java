package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public abstract class FluidWorkerTileEntity extends TileEntity implements IFluidWorkerHandler {

	protected FluidWorker worker;
	
	public FluidWorkerTileEntity(TileEntityType<?> tileEntityTypeIn, FluidWorker workerIn) {
		
		super(tileEntityTypeIn);
		
		this.worker = workerIn;
		
	}

	protected FluidWorker getFluidWorker() {
		
		return this.worker;
	}
	
	protected void setFluidWorker(FluidWorker workerIn) {
		
		this.worker = workerIn;
	}
	
	protected int getCapacity() {
		
		return worker.getCapacity();
	}
	
	protected void setCapacity(int capacityIn) {
		
		worker.setCapacity(capacityIn);
	}
	
	@Override
	public int fill(FluidStack resource, FluidAction action, Direction dir, FluidWorker worker) {

		return worker.fill(resource, action, dir);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action, Direction dir, FluidWorker worker) {

		return worker.drain(maxDrain, action, dir);
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action, Direction dir, FluidWorker worker) {

		return worker.drain(resource, action, dir);
	}
	
}
