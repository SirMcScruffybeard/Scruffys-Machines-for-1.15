package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class WaterTank extends FluidTank implements IFluidHandler, IFluidTank{
	
	public static int BUCKET_VOLUME = FluidAttributes.BUCKET_VOLUME;
	
	public WaterTank(final int capacity) {
		super(capacity);
		
	}
	
	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		
		return this.isFluidValid(stack);
	}
	
	@Override
	public boolean isFluidValid(FluidStack stack) {
		
		return super.isFluidValid(stack) && stack.isFluidEqual(new FluidStack(Fluids.WATER, 1));
	}
	
	@Override
	public WaterTank setCapacity(int cap) {
		
		this.capacity = cap;
		return this;
	}
	
	@Override
	public int fill(FluidStack resource, FluidAction action) {
		
		int amountIn = resource.getAmount();
		
		if (resource.isEmpty() || !isFluidValid(resource)) { return 0; }
		
		if (action.simulate() ) {
			
			if (fluid.isEmpty()) { return Math.min(capacity, amountIn); }
			
			if (!fluid.isFluidEqual(resource)) { return 0; }
			
			return Math.min(capacity - fluid.getAmount(), amountIn);
		}//if simulate
		
		if(fluid.isEmpty()) {
			
			fluid = new FluidStack(resource, Math.min(capacity, amountIn));
			this.onContentsChanged();
			return fluid.getAmount();
		}
		
		if (!fluid.isFluidEqual(resource)) {return 0; }
		
		int filled = capacity - fluid.getAmount();
		
		if (amountIn < filled) {
			
			fluid.grow(amountIn);
			filled = amountIn;
		}
		
		else {
			
			fluid.setAmount(capacity);
		}
		
		if (filled > 0) { this.onContentsChanged(); }
		
		return filled;
	}//fill
	
	@Override
	protected void onContentsChanged() {
		
	}

}
