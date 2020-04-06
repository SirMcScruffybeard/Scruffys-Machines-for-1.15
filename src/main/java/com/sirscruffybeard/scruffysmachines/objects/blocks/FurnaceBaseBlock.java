package com.sirscruffybeard.scruffysmachines.objects.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines.ScruffysMachinesItemGroup;
import com.sirscruffybeard.scruffysmachines.tileentity.FurnaceBaseTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class FurnaceBaseBlock extends Block{

	public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
	
	/*******************************
	 * FurnaceBase()
	 * 
	 * @param Properties properties
	 *******************************/
	public FurnaceBaseBlock(Properties properties) {
		super(properties);
		this.setDefaultState(this.getDefaultState().with(BlockStateProperties.LIT, false)); //Sets to not lit state default
	}

	/**************************************************************
	 * getHarvestTool()
	 * 
	 * @param BlockState state
	 * 
	 * @return ToolType.PICKAXE
	 * 
	 * Returns that a pick axe is the harvest tool
	 **************************************************************/
	@Nullable
	@Override
	public ToolType getHarvestTool(BlockState state) {
		return ToolType.PICKAXE;
	}
	
	public ItemGroup getItemGroup() {
		
		return ScruffysMachinesItemGroup.instance;
	}

	/**************************************************************
	 * getHarvestTool()
	 * 
	 * @param BlockState state
	 * 
	 * @return true
	 * 
	 * Returns that this block has a TileEntity
	 **************************************************************/
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(BlockStateProperties.LIT) ? 14 : 0;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return (BlockState)this.getDefaultState().with(BlockStateProperties.FACING, context.getPlacementHorizontalFacing().getOpposite());
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.FACING, BlockStateProperties.LIT);
	}

	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {

		if(state.getBlock() != newState.getBlock()) {

			TileEntity te = worldIn.getTileEntity(pos);

			if(te instanceof FurnaceBaseTileEntity) {
				InventoryHelper.dropItems(worldIn, pos, ((FurnaceBaseTileEntity)te).getItems());
			}
		}
	}//onReplaced

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
		if (state.get(BlockStateProperties.LIT)) {
			double x = (double) pos.getX() + 0.5D;
			double y = (double) pos.getY();
			double z = (double) pos.getZ() + 0.5D;
			if (rand.nextDouble() < 0.1D) {
				world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			Direction direction = state.get(BlockStateProperties.FACING);
			Direction.Axis direction$axis = direction.getAxis();
			double d3 = 0.52D;
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			double d5 = direction$axis == Direction.Axis.X ? (double) direction.getXOffset() * 0.52D : d4;
			double d6 = rand.nextDouble() * 6.0D / 16.0D;
			double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getZOffset() * 0.52D : d4;
			world.addParticle(ParticleTypes.SMOKE, x + d5, y + d6, z + d7, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, x + d5, y + d6, z + d7, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult result) {

		if(!worldIn.isRemote) {

			TileEntity tile = worldIn.getTileEntity(pos);

			if(tile instanceof FurnaceBaseTileEntity) {
				NetworkHooks.openGui((ServerPlayerEntity)player, (FurnaceBaseTileEntity)tile, pos);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.FAIL;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
		if (entity != null) {
			FurnaceTileEntity tile = (FurnaceTileEntity)world.getTileEntity(pos);
			if (stack.hasDisplayName()) {
				tile.setCustomName(stack.getDisplayName());
			}
		}
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		
		return null;
	}
	
    private void interactWith(World world, BlockPos pos, PlayerEntity player) {
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof INamedContainerProvider) {
            NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) entity, entity.getPos());
            player.addStat(Stats.INTERACT_WITH_FURNACE);
        }
    }
    
	@Override
	public BlockState rotate(BlockState state, Rotation rot) { return state.with(FACING, rot.rotate(state.get(FACING))); }

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) { return state.rotate(mirrorIn.toRotation(state.get(FACING))); }

}//FurnaceBaseBlock
