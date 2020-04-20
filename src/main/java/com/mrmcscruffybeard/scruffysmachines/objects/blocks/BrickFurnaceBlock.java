package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import java.util.Random;

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
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

		if (state.get(BlockStateProperties.LIT)) { //If lit
			double dX = (double) pos.getX() + 0.5D;
			double dY = (double) pos.getY();
			double dZ = (double) pos.getZ() + 0.5D;
			if (rand.nextDouble() < 0.1D) {
				world.playSound(dX, dY, dZ, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			Direction direction = state.get(BlockStateProperties.FACING);
			Direction.Axis direction$axis = direction.getAxis();
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
			double d6 = rand.nextDouble() * 6.0D / 16.0D;
			double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;
			world.addParticle(ParticleTypes.SMOKE, dX + d5, dY + d6, dZ + d7, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, dX + d5, dY + d6, dZ + d7, 0.0D, 0.0D, 0.0D);
		}
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
