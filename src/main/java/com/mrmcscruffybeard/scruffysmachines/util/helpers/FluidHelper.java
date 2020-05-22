package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWorkerSpecifier;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

public class FluidHelper {

	public static boolean isBucketWorth(FluidStack resource) {
		
		return resource.getAmount() >= FluidAttributes.BUCKET_VOLUME;
	}
	
	public static boolean isNumBucketsWorth(int amount, int numBuckets) {

		return amount >= (numBuckets * FluidAttributes.BUCKET_VOLUME);
	}
	
	public static FluidStack createBucketWorth(Fluid fluid) {
		
		return new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME);
	}

	/**************************************************************************************************
	 * getBlockStateFromFluidStack
	 * 
	 * @param FluidStack fluidIn
	 * 
	 * @return  BlockState 
	 * 
	 * Returns BlockState equivalent of "fluidIn".
	 **************************************************************************************************/
	public static BlockState getBlockStateFromFluidStack(FluidStack fluidIn) {

		return fluidIn.getFluid().getDefaultState().getBlockState();
	}

	public static Block getBlockFromFluidStack(FluidStack fluidIn) {

		return getBlockStateFromFluidStack(fluidIn).getBlock();
		
		
	}

	public static void flood(final BlockPos pos, final FluidStack fluidIn, final World world) {

		if(world.getDimension().getType() != DimensionType.THE_NETHER || !IWaterWorker.isWater(fluidIn)) {
		
		int buckets = fluidIn.getAmount() / FluidAttributes.BUCKET_VOLUME;

		FluidStack fluid = fluidIn;

		placeFluidInWorld(fluid, pos, world);

		fluid.shrink(FluidAttributes.BUCKET_VOLUME);
		
		
		}
	}

	public static void placeFluidInWorld(FluidStack fluid, BlockPos pos, final World world) {

		if(!world.isRemote) {

			if(isBucketWorth(fluid)) {

				world.setBlockState(pos, getBlockStateFromFluidStack(fluid));

			}
		}
	}

}
