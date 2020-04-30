package com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases;

import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketSwapHelper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public abstract class WaterTankBlockBase extends FluidTankBlockBase{

	public WaterTankBlockBase(Properties properties) {
		super(properties);

	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {

		if (!worldIn.isRemote) { 

			TileEntity tile = worldIn.getTileEntity(pos);

			Item heldItem = player.getHeldItemMainhand().getItem();

			if (tile instanceof WaterTankTileEntityBase) {

				if(BucketSwapHelper.isWaterBucket(heldItem)) { 

					((WaterTankTileEntityBase) tile).fillWithBucket(player);

				}

				if(BucketSwapHelper.isEmptyBucket(heldItem)) {

					((WaterTankTileEntityBase) tile).drainWithBucket(player);

				}
			}
		}

		return ActionResultType.SUCCESS;

	}//OnBlockActivated
}
