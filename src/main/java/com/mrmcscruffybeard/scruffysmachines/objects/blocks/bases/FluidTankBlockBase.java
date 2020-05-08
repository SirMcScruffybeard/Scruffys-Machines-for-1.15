package com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketHelper;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.TankHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
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

public abstract class FluidTankBlockBase extends Block {

	protected static final String ID_TANK = "_tank";

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	public FluidTankBlockBase(Properties properties) {
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public boolean hasTileEntity(BlockState state) {

		return true;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING);}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

		if (state.getBlock() != newState.getBlock()) {

			TileEntity tile = worldIn.getTileEntity(pos);

			if(TankHelper.isFluidTankTileEntity(tile)) {
			
				if(((FluidTankTileEntityBase) tile).canSpill()) { 
					
					((FluidTankTileEntityBase) tile).spill(pos, worldIn);
				}

			} //instance of
		}
	}

	@Override
	public abstract ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit);
}
