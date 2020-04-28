package com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class FluidTankBlockBase extends Block{

	protected static final String ID_TANK = "_tank";
	
	public FluidTankBlockBase(Properties properties) {
		super(properties);
		
		
	}
	
	@Override
	public abstract BlockState getStateForPlacement(BlockItemUseContext context);

	@Override
	public abstract void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving);
	
	@Override
	public abstract ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit);
	
	@Override
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		
		return true;
	}
	
	@Override
	protected abstract void fillStateContainer(Builder<Block, BlockState> builder);
}
