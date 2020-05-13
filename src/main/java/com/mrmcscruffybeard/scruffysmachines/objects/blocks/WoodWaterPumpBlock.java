package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.WoodWaterPumpTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
	

public class WoodWaterPumpBlock extends Block implements IHasFacing, IHasTileEntity{

	public static final String ID = "wood_water_pump";
	
	
	public WoodWaterPumpBlock(Properties properties) {
		super(properties);
		
		this.setDefaultState(stateContainer.getBaseState().with(FACING, Direction.NORTH));
		
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {

		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) { builder.add(FACING); }
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {

		return ModTileEntityTypes.WOOD_WATER_PUMP.get().create();
	}
	
	@Override
	public ActionResultType onBlockActivated(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player,
			final Hand handIn, final BlockRayTraceResult hit) {
		
		if(!world.isRemote) {
			
			if(world.getTileEntity(pos) instanceof WoodWaterPumpTileEntity) {
				
				WoodWaterPumpTileEntity pump = (WoodWaterPumpTileEntity) world.getTileEntity(pos);
				
				pump.pump();
			}
		}
		
		return ActionResultType.SUCCESS;
	}
	
	
	
	
	
}
