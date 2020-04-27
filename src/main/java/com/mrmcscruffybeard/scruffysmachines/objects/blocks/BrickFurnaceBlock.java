package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;


public class BrickFurnaceBlock extends Block {

	public static final String ID = "brick_furnace";

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;


	public BrickFurnaceBlock(Properties properties) {
		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, false));

	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

		if(state.getBlock() != newState.getBlock()) {

			TileEntity te = worldIn.getTileEntity(pos);

			if(te instanceof BrickFurnaceTileEntity) {
				InventoryHelper.dropItems(worldIn, pos, ((BrickFurnaceTileEntity)te).getItemList());
			}
		}
	}//onReplaced

	@Override
	public BrickFurnaceTileEntity createTileEntity(BlockState state, IBlockReader world) {

		return ModTileEntityTypes.BRICK_FURNACE.get().create();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {

		if(!worldIn.isRemote) {

			TileEntity tile = worldIn.getTileEntity(pos);

			if(tile instanceof BrickFurnaceTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity)player, (BrickFurnaceTileEntity)tile, pos);
				player.addStat(Stats.INTERACT_WITH_FURNACE);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.FAIL;
	}


	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (entity != null) {
			BrickFurnaceTileEntity te = (BrickFurnaceTileEntity)world.getTileEntity(pos);
			if (stack.hasDisplayName()) {
				te.setCustomName(stack.getDisplayName());
			}
		}
	}

}
