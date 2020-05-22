package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.tileetnties;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class WaterTankTile extends FluidTankTile implements IWaterWorker {

	public WaterTankTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {

		super(tileEntityTypeIn, capacityIn);

	}

	@Override
	public int fillFromSide(FluidStack resource, FluidAction action, Direction dir) {

		if(this.canWorkFluid(resource)) { 

			int filled = holder.fill(resource, action);

			if (filled > 0) {

				onFilledFromSide(dir);
			}

			return filled;
		}

		else { return 0; }

	}

	@Override
	public void fillWithBucket() {

		if(holder.canHoldBuckWorth()) {

			holder.fill(FluidHelper.createBucketWorth(Fluids.WATER), FluidAction.EXECUTE);

		}
	}

}
