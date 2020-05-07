package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import com.mrmcscruffybeard.scruffysmachines.util.PosSpokes;
import com.mrmcscruffybeard.scruffysmachines.util.PosSpokes.CenterSpokes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction8;
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

		return fluidStack.isFluidEqual(new FluidStack(fluid, 1));
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


		BlockPos[] puddles = new  BlockPos[CenterSpokes.values().length];

		fluid = placeFluidInWorld(fluid, pos, world);

		int n = 0;

		//sets starting positions 
		for(CenterSpokes spoke : CenterSpokes.values()) {

			puddles[n] = new BlockPos(spoke.getX(), spoke.getY(), spoke.getZ());

			n++;
		}
		
		boolean[] flags = new boolean[puddles.length];
		
		for(boolean flag : flags) {flag = true;} //Set all flags to true
		
		int flagCount = 0; //Holds number of !flags
		
		int i = 0;
		
		while(i < buckets ) {

			n = 0;
			
			for(CenterSpokes spoke : CenterSpokes.values()) {

				if(flags[n]) {

					fluid = placeFluidInWorld(fluidIn, puddles[n], world);

					puddles[n] = adjustPos(puddles[i], spoke.getX(), spoke.getY(), spoke.getZ());
					
					flags[n] = isValidBlockAtPos(puddles[n], world, fluidIn); //Set flag for next block in same direction
					
					n++;
					
					if( !flags[n]) { flagCount ++; } //if next block is not valid add 1 to flagCount
				}
				
			}

			if(flagCount == puddles.length) { i = buckets; }
			else { i++;}
		}

	}

	private static boolean isValidBlockAtPos(BlockPos pos, World world, FluidStack fluidIn) {

		return PosHelper.isAirAtPos(pos, world) || PosHelper.isValidBlockAtPos(pos, world, getBlockFromFluidStack(fluidIn));
	}

	private static BlockPos adjustPos(BlockPos pos, final int X, final int Y, final int Z) {

		int outX = X + pos.getX();
		int outY = Y + pos.getY();
		int outZ = Z + pos.getY();

		return new BlockPos(outX, pos.getY() + Y, pos.getZ() + Z);

	}

	private static FluidStack placeFluidInWorld(FluidStack fluid, BlockPos pos, final World world) {

		if(hasBucketWorth(fluid)) {

			if(!fluid.isEmpty()) {

				world.setBlockState(pos, getBlockStateFromFluidStack(fluid));

				fluid.shrink(FluidAttributes.BUCKET_VOLUME);	
			}
		}

		return fluid;
	}

}
