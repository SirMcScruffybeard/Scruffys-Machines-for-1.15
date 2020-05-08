package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.LeatherChestTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LeatherChestBlock extends Block{

	public static final Material MATERIAL = Material.WOOD;
	public static final float HARDNESS = 5.0F;
	public static final float RESISTANCE = 3.5F;
	public static final SoundType SOUND = SoundType.CLOTH;
	public static final String ID = "leather_chest";
	
	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	/***********************************************
	 * Constructor
	 * 
	 * @param Properties properties
	 ***********************************************/
	public LeatherChestBlock(Properties properties) {
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
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING);}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		
		return ModTileEntityTypes.LEATHER_CHEST.get().create();
		
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {
		
		if(!worldIn.isRemote) {
			
			TileEntity tile = worldIn.getTileEntity(pos);
			
			if(tile instanceof LeatherChestTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity)player, (LeatherChestTileEntity)tile, pos);
				return ActionResultType.SUCCESS;
			}
		}
		
		return ActionResultType.FAIL;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		
		if(state.getBlock() != newState.getBlock()) {
			
			TileEntity te = worldIn.getTileEntity(pos);
			
			if(te instanceof LeatherChestTileEntity) {
				InventoryHelper.dropItems(worldIn, pos, ((LeatherChestTileEntity)te).getItems());
			}
		}
	}//onReplaced
}
