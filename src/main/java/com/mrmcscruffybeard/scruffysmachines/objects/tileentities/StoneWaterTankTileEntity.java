package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketSwapHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class StoneWaterTankTileEntity extends FluidTankTileEntityBase {

	public static final String ID = StoneWaterTankBlock.ID;

	private static final int BUCKETS = 5;

	public StoneWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, BUCKETS);

	}

	public StoneWaterTankTileEntity() {

		this(ModTileEntityTypes.STONE_WATER_TANK.get());

	}

	@Override
	public void fillWithBucket(PlayerEntity player) {
		
		if(canHoldBucketVolume(TANK)) { 

			TANK.fill(new FluidStack(Fluids.WATER, BUCKET_VOLUME), FluidAction.EXECUTE);

			BucketSwapHelper.emptyBucket(player);
		}

	}

	@Override
	public void drainWithBucket(PlayerEntity player) {
		
		if(isHoldingBucketVolume(TANK)) {

			TANK.drain(BUCKET_VOLUME, FluidAction.EXECUTE);

			BucketSwapHelper.fillWaterBucket(player);
		}

	}

	public void spill(BlockPos pos, World worldIn, BlockState state) {
		
		if (!TANK.isEmpty() && TANK.getFluidAmount() >= BUCKET_VOLUME) {
			
			int amount = MathHelper.roundUp(TANK.getFluidAmount(), BUCKET_VOLUME);
			
			int coverage = amount / BUCKET_VOLUME;
			
			Block.replaceBlock(state, Blocks.WATER.getDefaultState(), worldIn, pos, 2);
			
		}
		
	}
	
	public boolean canSpill() {
		
		return true;
	}
	
	/**********************************************************
	 * Checks if passed item is a empty bucket or water bucket 
	 ***********************************************************/
	public static boolean isValidBucket(Item item) {
		
		return item == Items.BUCKET || item == Items.WATER_BUCKET;
	}
	
	public boolean hasFluidTank() {
		
		return true;
	}
	
	public boolean hasWaterTank() {
		
		return true;
	}
	
	/********************************
	 * fills from tank sitting on top
	 *******************************/
	public void fillFromUpper() {
		
		BlockPos checkUpPos = new BlockPos(this.pos.getX(), this.pos.getY() + 1, this.pos.getZ());
		
		Block checkUpBlock = this.world.getBlockState(checkUpPos).getBlock();
		
	}
}
