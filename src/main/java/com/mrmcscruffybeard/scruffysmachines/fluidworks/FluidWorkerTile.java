package com.mrmcscruffybeard.scruffysmachines.fluidworks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWorkerSpecifier;

import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class FluidWorkerTile extends TileEntity implements IFluidWorker, IWorkerSpecifier{

	protected FluidHolder holder = new FluidHolder(FluidAttributes.BUCKET_VOLUME);

	private final LazyOptional<IFluidHandler> handler = LazyOptional.of(() -> holder);

	private FluidWorkerTile(TileEntityType<?> tileEntityTypeIn) {

		super(tileEntityTypeIn);

	}

	public FluidWorkerTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {

		this(tileEntityTypeIn);

		holder = new FluidHolder(capacityIn);
	}

	protected void setTileInTank(TileEntity tileIn) {

		holder.setTileEntity(tileIn);
	}

	@Override
	public boolean isEmpty() { return holder.isEmpty(); }

	@Override
	public boolean isFull() { return holder.isFull(); }

	@Override
	public int getFluidAmount() { return holder.getFluidAmount(); }
	
	@Override
	public int getSpace() { return holder.getSpace(); }
	
	@Override
	public FluidStack getFluid() { return holder.getFluid(); }

	@Override
	public Fluid getFluidType() { return holder.getFluidType(); }

	@Override
	public boolean isFluidValid(FluidStack resource) {
		
		return holder.isFluidValid(resource);
	}
	
	@Override
	public void read(CompoundNBT tag)
	{
		super.read(tag);
		holder.readFromNBT(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag)
	{
		tag = super.write(tag);
		holder.writeToNBT(tag);
		return tag;
	}
	
	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing)
	{
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return handler.cast();
		return super.getCapability(capability, facing);
	}
	
	public int empty() {
		
		int emptied =  getFluidAmount();
		
		holder.isEmpty();
		
		return emptied;
	}
}
