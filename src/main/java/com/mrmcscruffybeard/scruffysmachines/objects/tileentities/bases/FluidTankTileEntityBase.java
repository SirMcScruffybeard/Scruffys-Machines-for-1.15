package com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public abstract class FluidTankTileEntityBase extends TileEntity{

	public static final int BUCKET_VOLUME = FluidAttributes.BUCKET_VOLUME;

	private int capacity = BUCKET_VOLUME;

	protected final WaterTank TANK = new WaterTank(BUCKET_VOLUME);

	protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> TANK);

	public FluidTankTileEntityBase(final TileEntityType<?> tileEntityTypeIn, final int numBuckets) {

		super(tileEntityTypeIn);

		this.capacity *= numBuckets;

		TANK.setCapacity(this.capacity);
	}

	@Override
	public void read(CompoundNBT compound) {

		super.read(compound);

		TANK.readFromNBT(compound);

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {

		compound = super.write(compound);

		TANK.writeToNBT(compound);

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

	public void drainWithBucket() {


	}
	
	public static boolean canHoldBucketVolume(FluidTank tank) {

		return tank.getSpace() >= BUCKET_VOLUME;
	}
	
	public static boolean isHoldingBucketVolume(FluidTank tank) {
		
		return tank.getFluidAmount() >= BUCKET_VOLUME;
	}
	
	public abstract void fillWithBucket(PlayerEntity player);
	
	public abstract void drainWithBucket(PlayerEntity player);

}
