package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.WaterTankBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.StoneWaterTankTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class StoneWaterTankBlock extends WaterTankBlockBase {

	public static String ID = "stone_water" + ID_TANK;

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
	public TileEntity createTileEntity(BlockState state, IBlockReader world) { 

		return ModTileEntityTypes.STONE_WATER_TANK.get().create();
	}



	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

		if (state.getBlock() != newState.getBlock()) {

			TileEntity tile = worldIn.getTileEntity(pos);

			if(tile instanceof StoneWaterTankTileEntity && ((StoneWaterTankTileEntity) tile).canSpill()) {

				((StoneWaterTankTileEntity) tile).spill(pos, worldIn, state);

			} //instance of
		}
	}

}//class
