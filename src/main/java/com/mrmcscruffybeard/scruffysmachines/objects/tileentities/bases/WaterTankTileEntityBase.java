package com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases;

import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketSwapHelper;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public abstract class WaterTankTileEntityBase extends FluidTankTileEntityBase{

	public WaterTankTileEntityBase(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);

	}

	@Override
	public void fillWithBucket(PlayerEntity player) {

		if(canHoldBucketVolume(tank)) { 

			tank.fill(new FluidStack(Fluids.WATER, BUCKET_VOLUME), FluidAction.EXECUTE);

			BucketSwapHelper.emptyBucket(player);
		}

	}

	@Override
	public void drainWithBucket(PlayerEntity player) {

		if(isHoldingBucketVolume(tank)) {

			tank.drain(BUCKET_VOLUME, FluidAction.EXECUTE);

			BucketSwapHelper.fillWaterBucket(player);
		}

	}

	public void spill(BlockPos pos, World worldIn, BlockState state) {

		if (!tank.isEmpty() && tank.getFluidAmount() >= BUCKET_VOLUME) {

			tank.empty();

			worldIn.setBlockState(pos, Blocks.WATER.getDefaultState());
		}

	}

	public boolean hasWaterTank() {

		return true;
	}

}
