package com.sirscruffybeard.scruffysmachines.objects.blocks;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines.ScruffysMachinesItemGroup;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BrickFurnaceBlock extends AbstractFurnaceBlock{

	public static final Material MATERIAL = Material.ROCK;
	public static final ToolType TOOL = ToolType.PICKAXE;
	public static final SoundType SOUND = SoundType.STONE;
	public static final ItemGroup GROUP = ScruffysMachinesItemGroup.instance;
	public static final float HARDNESS = 3.5f;
	public static final float RESISTANCE = 5.0f;
	public static final String ID = "brick_furnace";

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;


	public BrickFurnaceBlock(Block.Properties builder) {

		super(builder);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
		
		

	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) { return state.with(FACING, rot.rotate(state.get(FACING))); }

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) { return state.rotate(mirrorIn.toRotation(state.get(FACING))); }

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING, LIT); }

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
		// TODO Auto-generated method stub
		
	}


}
