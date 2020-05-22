package com.mrmcscruffybeard.scruffysmachines.fluidworks;

import com.mrmcscruffybeard.scruffysmachines.util.ModMath;

import net.minecraft.fluid.Fluid;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class FluidHolder extends FluidTank implements IFluidHandler, IFluidHolder{

	TileEntity tile;
	
	public FluidHolder(int capacity) {
		super(capacity);
		
	}

	@Override
	public int getTanks() {
		
		return 1;
	}
	
	@Override
	public int getSpace() {

		int space = getCapacity() - getFluidAmount();

		return ModMath.min(space, 0);
	}
	
	@Override
	public boolean isFull() {
		
		return getSpace() == 0;
	}
	
	@Override
	public FluidStack getFluidInTank(int tank) {
		
		return getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {
		
		return capacity;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		
		return isFluidValid(stack);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		
		return super.fill(resource, action);
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		
		return super.drain(resource, action);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {

		return super.drain(maxDrain, action);
	}

	@Override
	public Fluid getFluidType() {
		
		return fluid.getFluid();
	}

	@Override
	public TileEntity getTileEntity() {
		
		return tile;
	}

	@Override
	public void setTileEntity(TileEntity tileIn) {
		
		tile = tileIn;
	}

	@Override
	public boolean canHoldBuckWorth() {
		
		return this.getSpace() >= FluidAttributes.BUCKET_VOLUME;
	}

	@Override
	public boolean hasBucketWorth() {
		
		return this.getFluidAmount() >= FluidAttributes.BUCKET_VOLUME;
	}

}
