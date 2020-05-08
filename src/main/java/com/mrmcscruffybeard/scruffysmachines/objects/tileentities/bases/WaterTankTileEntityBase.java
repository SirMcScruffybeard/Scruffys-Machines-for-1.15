package com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases;

import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class WaterTankTileEntityBase extends FluidTankTileEntityBase{

	public WaterTankTileEntityBase(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);

	}

	@Override
	public void fillWithBucket() {

		if(canHoldBucketVolume(tank)) { 

			tank.fill(new FluidStack(Fluids.WATER, BUCKET_VOLUME), FluidAction.EXECUTE);

		}

	}

	public boolean hasWaterTank() {

		return true;
	}

}
