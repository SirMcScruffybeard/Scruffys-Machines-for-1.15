package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities.FluidWorkerTileEntity;

public class FluidWorkerStack {

	private int totalCapacity = 0;
	private int totalFluidAmount = 0;
	

	
	public FluidWorkerStack(FluidWorkerTileEntity fwte) {
		
		new FluidWorkerHolder(fwte);
	}
	
	public int getTotalCapacity() {
		
		return this.totalCapacity;
	}
	
	public void setTotalCapacity(int totalCapacityIn) {
		
		this.totalCapacity = totalCapacityIn;	
	}
	
	public int getTotalFluidAmount() {
		
		return this.totalFluidAmount;
	}
	
	public void setTotalFluidAmount(int totalFluidAmountIn) {
		
		this.totalFluidAmount = totalFluidAmountIn;
	}
	
	protected void onAddHolder() {
		
	}
	
	protected void onRemoveHolder() {
		
		
	}
	
	
	private class FluidWorkerHolder {
		
		FluidWorkerTileEntity worker;
		
		public FluidWorkerHolder(FluidWorkerTileEntity fwte) {

			worker = fwte;
		}
	}
}
