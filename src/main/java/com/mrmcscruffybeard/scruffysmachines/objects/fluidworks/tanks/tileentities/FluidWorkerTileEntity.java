package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.FluidWorker;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.IFluidWorkerHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

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
	
}
