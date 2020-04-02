package com.sirscruffybeard.scruffysmachines.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraftforge.common.ToolType;

public class CrumblerBlock extends Block{

	public static String ID = "crumbler";
	public static Material MATERIAL = Material.ROCK;
	public static SoundType SOUND = SoundType.STONE;
	public static float HARDNESS = 5.0f;
	public static float RESISTANCE = 5.0f;
	public static ToolType TOOL = ToolType.PICKAXE;
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	public CrumblerBlock(Properties properties) {
		super(properties);
		
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
				
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {return state.with(FACING, rot.rotate(state.get(FACING))); }
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) { return state.rotate(mirrorIn.toRotation(state.get(FACING))); }
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING); }
	
}
