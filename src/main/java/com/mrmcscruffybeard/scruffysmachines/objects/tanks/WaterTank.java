package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class WaterTank extends ModFluidTank implements IFluidHandler, IFluidTank{
	
	public WaterTank(final int capacity) {
		super(capacity);
		
	}
	
	public WaterTank(final int capacity, FluidTankTileEntityBase tileIn) {
		super(capacity);
		
	}
	
	
	public boolean isWaterValid(FluidStack stack) {
		
		return super.isFluidValid(stack) && FluidHelper.isWater(stack);
	}
	
	@Override
	public WaterTank setCapacity(int cap) {
		
		this.capacity = cap;
		return this;
	}
	
	@Override
	public int fill(FluidStack resource, FluidAction action) {
		
		int amountIn = resource.getAmount();
		
		if (resource.isEmpty() || !isWaterValid(resource)) { return 0; }
		
		if (action.simulate() ) {
			
			if (fluid.isEmpty()) { return Math.min(capacity, amountIn); }
			
			if (!FluidHelper.isWater(resource)) { return 0; }
			
			return Math.min(capacity - fluid.getAmount(), amountIn);
		}//if simulate
		
		if(fluid.isEmpty()) {
			
			fluid = new FluidStack(resource, Math.min(capacity, amountIn));
			this.onContentsFilled();
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
		
		if (filled > 0) { this.onContentsFilled(); }
		
		return filled;
		
	}//fill
	
	@Override
	public boolean canHoldFluidType(FluidStack fluidStack) {
		
		return FluidHelper.isWater(fluidStack);
	}

}
