package com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.objects.tanks.ModFluidTank;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class FluidPumpTileEntityBase extends TileEntity{

	protected int pumpCapacity = 0;
	
	protected ModFluidTank tank;
	
	protected int amountPumped = 0;
	
	
	protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

	public FluidPumpTileEntityBase(TileEntityType<?> tileEntityTypeIn, ModFluidTank tankIn) {
		super(tileEntityTypeIn);

		this.tank = tankIn;
	}

	@Override
	public void read(CompoundNBT compound) {

		super.read(compound);

		tank.readFromNBT(compound);

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {

		compound = super.write(compound);

		tank.writeToNBT(compound);

		return compound;

	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction facing) {

		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {

			return holder.cast();
		}

		return super.getCapability(cap, facing);
	}

	public FluidStack getFluidInTank(int tank) {

		return this.tank.getFluid();
	}
	
	public int getTankCapacity(int tank) { return pumpCapacity;}

}
