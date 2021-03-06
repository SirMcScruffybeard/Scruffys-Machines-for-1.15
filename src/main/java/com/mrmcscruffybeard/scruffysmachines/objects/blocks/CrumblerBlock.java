package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;

public class CrumblerBlock extends Block{

	public static final String ID = "crumbler";

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

	public CrumblerBlock(Properties properties) {
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING); }

}
