package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import javax.annotation.Nonnull;

import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.TankHelper;

import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;


public class ModFluidTank extends FluidTank implements IFluidHandler, IModFluidTank{

	public static int BUCKET_VOLUME = FluidAttributes.BUCKET_VOLUME;

	private FluidTankTileEntityBase tile;


	public ModFluidTank(int capacity)
	{
		super(capacity);
		
	}

	public void setTankTileEntity(FluidTankTileEntityBase tileIn) {

		this.tile = tileIn;
	}
	
	public boolean hasBucketWorth() {
		
		return this.getFluidAmount() >= BUCKET_VOLUME;
	}
	
	public boolean canHoldBucketWorth() {
		
		return this.getSpace() >= BUCKET_VOLUME;
	}

	
	@Override
	public void empty() { this.fluid = FluidStack.EMPTY; }

	@Override
	public boolean isFull() { return this.getSpace() == 0; }

	public ModFluidTank setCapacity(int cap) {

		this.capacity = cap;
		return this;
	}


	protected void onContentsFilled() {

		TankHelper.drainToLower(tile);
		
	}
	
	protected void onContentsDrained() {
		
		TankHelper.fillFromUpper(tile);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		if (resource.isEmpty() || !isFluidValid(resource)) { return 0; }
		
		if (action.simulate()){
			
			if (fluid.isEmpty()) { return Math.min(capacity, resource.getAmount()); }
		
			if (!fluid.isFluidEqual(resource)) { return 0; }
			
			return Math.min(capacity - fluid.getAmount(), resource.getAmount());
		}
		
		if (fluid.isEmpty()) {
			
			fluid = new FluidStack(resource, Math.min(capacity, resource.getAmount()));
			onContentsFilled();
			return fluid.getAmount();
		}
		
		if (!fluid.isFluidEqual(resource)) { return 0; }
		
		int filled = capacity - fluid.getAmount();

		if (resource.getAmount() < filled) {
			
			fluid.grow(resource.getAmount());
			filled = resource.getAmount();
		}
		
		else { fluid.setAmount(capacity); }
		
		if (filled > 0) { onContentsFilled(); }
			
		return filled;
	}

	@Nonnull
	@Override
	public FluidStack drain(FluidStack resource, FluidAction action)
	{
		if (resource.isEmpty() || !resource.isFluidEqual(fluid))
		{
			return FluidStack.EMPTY;
		}
		return drain(resource.getAmount(), action);
	}

	@Nonnull
	@Override
	public FluidStack drain(int maxDrain, FluidAction action)
	{
		int drained = maxDrain;
		if (fluid.getAmount() < drained)
		{
			drained = fluid.getAmount();
		}
		FluidStack stack = new FluidStack(fluid, drained);
		if (action.execute() && drained > 0)
		{
			fluid.shrink(drained);
		}
		if (drained > 0) {
			
			this.onContentsDrained();

		}
			
		return stack;
	}

	@Override
	public boolean canHoldFluidType(FluidStack fluidStack) { return true; }

}
