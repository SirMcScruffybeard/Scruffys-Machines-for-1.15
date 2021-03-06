package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.blocks;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.tileetnties.WaterTankTile;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.DirectionProperty;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public abstract class WaterTankBlock extends FluidTankBlock{

	public static final String ID_WATER_TANK = "_water" + ID_TANK;

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public WaterTankBlock(Properties properties) {
		super(properties);

	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {

		if(!world.isRemote && world.getTileEntity(pos) instanceof WaterTankTile) {

			WaterTankTile tank = (WaterTankTile) world.getTileEntity(pos);

			Item heldItem = player.getHeldItemMainhand().getItem();

			if (BucketHelper.isWaterBucket(heldItem) && tank.canFillWithBucket()) {

				tank.fillWithBucket();

				if(!player.abilities.isCreativeMode) {

					BucketHelper.emptyBucket(player);
				}
			}

			if(BucketHelper.isEmptyBucket(heldItem) && tank.canDrainWithBucket()) {

				tank.drainWithBucket();

				if(!player.abilities.isCreativeMode) {

					BucketHelper.fillWaterBucket(player);
				}
			}
		}

		return ActionResultType.SUCCESS;
	}

}
