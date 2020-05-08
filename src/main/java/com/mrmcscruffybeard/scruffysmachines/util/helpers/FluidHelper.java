package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidHelper {

	public static boolean hasBucketWorth(FluidStack fluid) {

		return isBucketWorth(fluid.getAmount());
	}

	public static boolean isBucketWorth(int amount) {

		return amount >= FluidAttributes.BUCKET_VOLUME;
	}

	public static boolean isNumBucketsWorth(int amount, int numBuckets) {

		return amount >= (numBuckets * FluidAttributes.BUCKET_VOLUME);
	}

	public static boolean isFluidType(final FluidStack fluidStack, final Fluid fluid) {

		return fluidStack.getFluid().getFluid() == fluid;
	}

	public static boolean isWater(FluidStack fluidStack) {

		return isFluidType(fluidStack, Fluids.WATER);
	}

	public static boolean isLava(FluidStack fluidStack) {

		return isFluidType(fluidStack, Fluids.LAVA);
	}

	/**************************************************************************************************
	 * getBlockStateFromFluidStack
	 * 
	 * @param FluidStack fluidIn
	 * 
	 * @return FluidStack BlockState 
	 * 
	 * Returns BlockState equivalent of "fluidIn".
	 **************************************************************************************************/
	public static BlockState getBlockStateFromFluidStack(FluidStack fluidIn) {

		return getBlockFromFluidStack(fluidIn).getDefaultState();
	}

	public static Block getBlockFromFluidStack(FluidStack fluidIn) {

		Block outBlock  =  Blocks.AIR;

		for(Block block : ForgeRegistries.BLOCKS) {

			if(block instanceof FlowingFluidBlock) { 

				//ScruffysMachines.LOGGER.info(block.getNameTextComponent().getFormattedText());

				Fluid fluid = (Fluid) ((FlowingFluidBlock)block).getFluidState(block.getDefaultState()).getFluid();

				if(fluidIn.isFluidEqual(new FluidStack(fluid, 1))) {

					outBlock = block;
				}
			}
		}

		return outBlock;
	}

	public static void flood(final BlockPos pos, final FluidStack fluidIn, final World world) {

		int buckets = fluidIn.getAmount() / FluidAttributes.BUCKET_VOLUME;

		FluidStack fluid = fluidIn;

		placeFluidInWorld(fluid, pos, world);

		fluid.shrink(FluidAttributes.BUCKET_VOLUME);

	}

	private static boolean isValidBlockAtPos(BlockPos pos, World world, FluidStack fluidIn) {

		return PosHelper.isAirAtPos(pos, world) || PosHelper.isValidBlockAtPos(pos, world, getBlockFromFluidStack(fluidIn));
	}
	
	private static BlockPos addPos(BlockPos pos1, BlockPos pos2) {
		
		int x = pos1.getX() + pos2.getX();
		int y = pos1.getY() + pos2.getY();
		int z = pos1.getZ() + pos2.getZ();
		
		return new BlockPos(x, y, z);
	}

	private static BlockPos adjustPos(BlockPos pos, final int X, final int Y, final int Z) {

		int outX = X + pos.getX();
		int outY = Y + pos.getY();
		int outZ = Z + pos.getY();

		return new BlockPos(outX, outY, outZ);

	}

	private static void placeFluidInWorld(FluidStack fluid, BlockPos pos, final World world) {

		if(!world.isRemote) {

			if(hasBucketWorth(fluid)) {

				world.setBlockState(pos, getBlockStateFromFluidStack(fluid));

			}
		}
	}

}
