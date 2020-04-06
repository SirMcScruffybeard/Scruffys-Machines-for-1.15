package com.sirscruffybeard.scruffysmachines.objects.blocks;

import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.sirscruffybeard.scruffysmachines.tileentity.BrickFurnaceTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BrickFurnaceBlock extends FurnaceBaseBlock{

	public static final Material MATERIAL = Material.ROCK;
	public static final SoundType SOUND = SoundType.STONE;
	public static final float HARDNESS = 3.5f;
	public static final float RESISTANCE = 5.0f;
	public static final String ID = "brick_furnace";
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	

	public BrickFurnaceBlock(Block.Properties builder) {

		super(builder);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
		
	}
	
    @Override
    public int getHarvestLevel(BlockState state) { return 2; }

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}


	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {
		
		return ModTileEntityTypes.BRICK_FURNACE.get().create();
	}

	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		if(state.getBlock() != newState.getBlock()) {
			
			TileEntity te = worldIn.getTileEntity(pos);
			
			if(te instanceof BrickFurnaceTileEntity) {
				InventoryHelper.dropItems(worldIn, pos, ((BrickFurnaceTileEntity)te).getItems());
			}
		}
	}//onReplaced

}
