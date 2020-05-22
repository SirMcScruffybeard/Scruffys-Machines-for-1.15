package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.tileetnties;

import javax.annotation.Nonnull;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.FluidWorkerTile;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.IFluidTankTile;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class FluidTankTile extends FluidWorkerTile implements IFluidTankTile {

	public FluidTankTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {
		super(tileEntityTypeIn, capacityIn);

	}


	@Override
	public boolean canSpill() {

		return holder.hasBucketWorth();
	}

	@Override
	public boolean canFillWithBucket() {

		return holder.canHoldBuckWorth();
	}

	@Override
	public boolean canDrainWithBucket() {

		return holder.hasBucketWorth();
	}

	@Override
	public FluidStack drainFromSide(int maxDrain, FluidAction action, Direction dir) {

		FluidStack resOut = holder.drain(maxDrain, action);

		if(!resOut.isEmpty()) {

			this.onDrainedFromSide(dir);
		}

		return resOut;
	}

	@Override
	public FluidStack drainFromSide(@Nonnull FluidStack resource, FluidAction action, Direction dir) {

		return this.drainFromSide(resource.getAmount(), action, dir);
	}

	protected  void onFilledFromSide(Direction dir) {

	}

	protected void onDrainedFromSide(Direction dir) {


	}

	@Override
	public void drainWithBucket() {

		if (holder.hasBucketWorth()) {

			holder.drain(FluidAttributes.BUCKET_VOLUME, FluidAction.EXECUTE);
		}
	}

	public void onFilledWithBucket() {


	}

	public void onDrainedWithBucket() {


	}

	public void spill(BlockPos pos, World worldIn) {

		if (canSpill()) {

			FluidHelper.flood(pos, getFluid(), worldIn);

			empty();
		}
	}
}
