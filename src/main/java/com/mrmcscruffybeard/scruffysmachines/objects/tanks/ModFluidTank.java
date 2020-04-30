package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.TankHelper;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

/*
 * Based on the FluidTank provided by Forge
 */
public class ModFluidTank  implements IFluidHandler, IFluidTank{

	public static int BUCKET_VOLUME = FluidAttributes.BUCKET_VOLUME;

	private FluidTankTileEntityBase tile;

	protected Predicate<FluidStack> validator;
	@Nonnull
	protected FluidStack fluid = FluidStack.EMPTY;
	protected int capacity;

	public ModFluidTank(int capacity, Predicate<FluidStack> validator)
	{
		this.capacity = capacity;
		this.validator = validator;
	}

	public ModFluidTank(int capacity) {
		this(capacity, e -> true);
	}

	public void setTankTileEntity(FluidTankTileEntityBase tileIn) {

		this.tile = tileIn;
	}

	public ModFluidTank readFromNBT(CompoundNBT nbt) {

		FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
		setFluid(fluid);
		return this;
	}

	public CompoundNBT writeToNBT(CompoundNBT nbt) {

		fluid.writeToNBT(nbt);

		return nbt;
	}

	@Nonnull
	public FluidStack getFluid()
	{
		return fluid;
	}

	public int getFluidAmount()
	{
		return fluid.getAmount();
	}

	@Override
	public int getTanks() {

		return 1;
	}

	@Nonnull
	@Override
	public FluidStack getFluidInTank(int tank) {

		return getFluid();
	}

	@Override
	public int getTankCapacity(int tank) {

		return getCapacity();
	}

	public void empty() {

		this.fluid = FluidStack.EMPTY;
	}

	public boolean isFull() {

		return this.getSpace() == 0;
	}

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
	public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {

		return isFluidValid(stack);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action)
	{
		if (resource.isEmpty() || !isFluidValid(resource))
		{
			return 0;
		}
		if (action.simulate())
		{
			if (fluid.isEmpty())
			{
				return Math.min(capacity, resource.getAmount());
			}
			if (!fluid.isFluidEqual(resource))
			{
				return 0;
			}
			return Math.min(capacity - fluid.getAmount(), resource.getAmount());
		}
		if (fluid.isEmpty())
		{
			fluid = new FluidStack(resource, Math.min(capacity, resource.getAmount()));
			onContentsFilled();
			return fluid.getAmount();
		}
		if (!fluid.isFluidEqual(resource))
		{
			return 0;
		}
		int filled = capacity - fluid.getAmount();

		if (resource.getAmount() < filled)
		{
			fluid.grow(resource.getAmount());
			filled = resource.getAmount();
		}
		else
		{
			fluid.setAmount(capacity);
		}
		if (filled > 0) {
			
			onContentsFilled();
		}
			
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

	
	public void setFluid(FluidStack stack)
	{
		this.fluid = stack;
	}

	public boolean isEmpty()
	{
		return fluid.isEmpty();
	}

	
	public int getSpace()
	{
		return Math.max(0, capacity - fluid.getAmount());
	}

	
	public ModFluidTank setValidator(Predicate<FluidStack> validator)
	{
		if (validator != null) {
			this.validator = validator;
		}
		return this;
	}

	@Override
	public boolean isFluidValid(FluidStack stack)
	{
		return validator.test(stack);
	}

	@Override
	public int getCapacity()
	{
		return capacity;
	}

}
