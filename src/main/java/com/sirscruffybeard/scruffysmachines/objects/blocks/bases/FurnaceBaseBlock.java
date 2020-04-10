package com.sirscruffybeard.scruffysmachines.objects.blocks.bases;

import com.sirscruffybeard.scruffysmachines.tileentity.LeatherChestTileEntity;
import com.sirscruffybeard.scruffysmachines.tileentity.bases.FurnaceBaseTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
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

public abstract class FurnaceBaseBlock extends Block{

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

	public FurnaceBaseBlock(Block.Properties properties) {

		super(properties);

		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, Boolean.valueOf(false)));
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	public abstract TileEntity createTileEntity(BlockState state, IBlockReader world);

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {

		if(!worldIn.isRemote) {

			TileEntity tile = worldIn.getTileEntity(pos);

			if(tile instanceof FurnaceBaseTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity)player, (FurnaceBaseTileEntity)tile, pos);
				this.interactWith(worldIn, pos, player);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.FAIL;
	}

	protected abstract void interactWith(World worldIn, BlockPos pos, PlayerEntity player);

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT);
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

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(pos);
			if (tileentity instanceof FurnaceBaseTileEntity) {
				((FurnaceBaseTileEntity)tileentity).setCustomName(stack.getDisplayName());
			}
		}

	}

	/**
	 * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
	 * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
	 * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
	 */
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
	 * fine.
	 */
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
	 */
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(FACING)));
	}

	/**
	 * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
	 * is fine.
	 */
	public boolean hasComparatorInputOverride(BlockState state) {
		return true;
	}

	/**
	 * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
	 * Implementing/overriding is fine.
	 */
	public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.calcRedstone(worldIn.getTileEntity(pos));
	}

	/**
	 * Amount of light emitted
	 * @deprecated prefer calling {@link IBlockState#getLightValue()}
	 */
	public int getLightValue(BlockState state) {
		return state.get(LIT) ? super.getLightValue(state) : 0;
	}
}//FurnaceBaseBlock
