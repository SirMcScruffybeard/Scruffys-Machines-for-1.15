package com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.objects.tanks.ModFluidTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.PosHelper;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.TankHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class FluidTankTileEntityBase extends TileEntity{

	public static final int BUCKET_VOLUME = FluidAttributes.BUCKET_VOLUME;

	protected ModFluidTank tank;

	protected final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

	public FluidTankTileEntityBase(final TileEntityType<?> tileEntityTypeIn, final ModFluidTank tankIn) {

		super(tileEntityTypeIn);

		this.tank = tankIn;

	}

	public void setInTankTileEntity(FluidTankTileEntityBase tileIn) {

		tank.setTankTileEntity(tileIn);
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

	public static boolean canHoldBucketVolume(ModFluidTank tank) {

		return tank.getSpace() >= BUCKET_VOLUME;
	}

	public static boolean isHoldingBucketVolume(ModFluidTank tankIn) {

		return tankIn.getFluidAmount() >= BUCKET_VOLUME;
	}

	public abstract void fillWithBucket();

	public void drainWithBucket() {
		
		if(isHoldingBucketVolume(tank)) {

			tank.drain(BUCKET_VOLUME, FluidAction.EXECUTE);

		}
		
	}

	public boolean canSpill() { return tank.hasBucketWorth(); }
	
	public boolean canDrainWithBucket() { return tank.hasBucketWorth(); }
	
	public boolean canDrain() { return tank.getFluidAmount() > 0; }
	
	public boolean canFillWithBucket() { return tank.canHoldBucketWorth(); }
	
	public boolean canFill() { return tank.getSpace() > 0; }

	public boolean hasFluidTank() { return true; }

	public int getFluidAmount() {

		return tank.getFluidAmount();
	}

	/*****************************
	 * getFluid()
	 * 
	 * @return FluidStack
	 *****************************/
	public FluidStack getFluid() {

		return tank.getFluid();
	}

	public boolean isFull() {

		return tank.isFull();
	}

	public boolean isEmpty() {

		return tank.isEmpty();
	}

	public int getSpace() {

		return tank.getSpace();
	}

	public ModFluidTank getTank() {

		return tank;
	}

	
	public void spill(BlockPos pos, World worldIn)  {
		
		if (FluidHelper.isBucketWorth(tank.getFluidAmount())) {
			
			FluidHelper.flood(pos, tank.getFluid(), worldIn);
			
			tank.empty();
		}
	}
	
}
