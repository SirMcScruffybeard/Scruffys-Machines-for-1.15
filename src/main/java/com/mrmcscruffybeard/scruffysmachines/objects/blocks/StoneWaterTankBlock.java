package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.StoneWaterTankTileEntity;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketSwapHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StoneWaterTankBlock extends Block {

	public static String ID = "stone_water_tank";

	public static final Material MATERIAL = Material.ROCK;

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public StoneWaterTankBlock(Properties properties) {
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING);}

	@Override
	public boolean hasTileEntity(BlockState state) {

		return true; 
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) { 

	return ModTileEntityTypes.STONE_WATER_TANK.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {

		if (!worldIn.isRemote) { 

			TileEntity tile = worldIn.getTileEntity(pos);

			Item heldItem = player.getHeldItemMainhand().getItem();

			if (tile instanceof StoneWaterTankTileEntity) {


				if(heldItem == Items.WATER_BUCKET) { 

					((StoneWaterTankTileEntity) tile).fillWithBucket(player);

				}

				if(heldItem == Items.BUCKET) {

					((StoneWaterTankTileEntity) tile).drainWithBucket(player);

				}


			}
		}
		return ActionResultType.SUCCESS;
	}
}
