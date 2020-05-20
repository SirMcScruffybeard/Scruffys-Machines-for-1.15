package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.IFluidWorkerHandler.FluidAction;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities.FluidWorkerTileEntity;
import com.mrmcscruffybeard.scruffysmachines.util.ModMath;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public abstract class FluidWorker implements IFluidWorker{

	protected Predicate<FluidStack> validator;

	protected FluidWorkerTileEntity tile;

	protected int id;

	@Nonnull
	protected FluidStack fluid = FluidStack.EMPTY;

	protected int capacity;

	private FluidWorker(int capacityIn, Predicate<FluidStack> validatorIn) {

		this.validator = validatorIn;

		this.setCapacity(capacityIn);
	}

	public FluidWorker(int capacityIn) {

		this(capacityIn, e -> true);
	}

	public FluidWorker() {

		this(0);
	}

	public FluidWorker readFromNBT(CompoundNBT nbt) {

		FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);
		setFluid(fluid);
		return this;
	}

	public CompoundNBT writeToNBT(CompoundNBT nbt) {

		fluid.writeToNBT(nbt);

		return nbt;
	}

	public FluidWorker setValidator(Predicate<FluidStack> validator)
	{
		if (validator != null) {
			this.validator = validator;
		}
		return this;
	}

	public FluidWorkerTileEntity getTileEntity() {

		return this.tile;
	}

	public void setTileEntity(FluidWorkerTileEntity fcte) {

		this.tile = fcte;
	}

	@Override
	public int getCapacity() {

		return this.capacity;
	}

	@Override
	public void setCapacity(int capacityIn) {

		this.capacity = capacityIn;
	}


	@Override
	public Fluid getFluidType() {

		return this.fluid.getFluid();
	}

	@Override
	public FluidStack getFluid() {

		return this.getFluid();
	}

	@Override
	public void setFluid(FluidStack fluidStack) {

		this.fluid = fluidStack;
	}

	@Override
	public boolean isEmpty() {

		return this.fluid.isEmpty(); 
	}

	@Override
	public int getAmount() {

		return this.fluid.getAmount();
	}

	@Override
	public int getId() {

		return this.id;
	}

	@Override
	public void setId(int idIn) {

		this.id = idIn;

	}

	@Override
	public int getSpace() {

		int space = getCapacity() - getAmount();

		return space > 0 ? space : 0;
	}

	@Override
	public boolean isFluidValid(FluidStack resource) {

		return validator.test(resource);
	}


	protected int fillFromDirection(FluidStack resource, FluidAction action, Direction dir) {

		if(resource.isEmpty() || !isFluidValid(resource)) {return 0;}

		if(action.simulate()) { this.simulateFill(resource, action); }

		if(isEmpty()) {
			
			fluid = new FluidStack(resource, ModMath.min(capacity, resource.getAmount()));
			onContentsFilled(dir);
			return fluid.getAmount();
		}
		
		if(!fluid.isFluidEqual(resource)) { return 0; }
		
		int filled = capacity - getAmount();
		
		if(resource.getAmount() < filled) {
			
			fluid.grow(resource.getAmount());
			filled = resource.getAmount();
		}
		
		else { fluid.setAmount(capacity); }
		
		if (filled > 0) {
			
			onContentsFilled(dir);
		}
		
		return filled;
		
	}

	@Nonnull
	protected FluidStack drainFromDirection(int maxDrain, FluidAction action, Direction dir) {

		int drained = maxDrain;
		
		if(getAmount() < drained) { drained = getAmount(); }
		
		FluidStack stack = new FluidStack(fluid, drained);
		
		if(action.execute() && drained > 0) {
			
			fluid.shrink(drained);
		}
		
		if( drained > 0) {
			
			this.onContentsDrained(dir);
		}
		
		return stack;
	}

	@Nonnull
	protected FluidStack drainFromDirection(FluidStack resource, FluidAction action, Direction dir) {

		if(resource.isEmpty() || !fluid.isFluidEqual(resource)) {
			
			return FluidStack.EMPTY;
			
		}
		
		return drainFromDirection(resource.getAmount(), action, dir);
	}

	protected void onContentsFilled(Direction dir) {


	}

	protected void onContentsDrained(Direction dir) {


	}

	public int simulateFill(FluidStack resource, FluidAction action) {

		if(!action.simulate()) {return 0;}

		else {

			if(isEmpty()) { return ModMath.min(capacity, resource.getAmount()); }

			if (!fluid.isFluidEqual(resource)) {return 0; }

			return ModMath.min(this.getSpace(), resource.getAmount());

		}
	}

}
