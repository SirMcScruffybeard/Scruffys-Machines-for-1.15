package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.FluidTankBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.WaterTankBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.ModFluidTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterTankTileEntityBase;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TankHelper {

	/****************************************************
	 * isFluidTankBlock(Block block)
	 * 
	 * @param Block block
	 * 
	 * @return if passed block represents a fluid tank in world
	 *****************************************************/
	public static boolean isFluidTankBlock(Block block) {

		return block instanceof FluidTankBlockBase;
	}

	/********************************************************************
	 * isFluidTankTileEntity(TileEntity tileEntity)	 
	 * 
	 * @param TileEntity tileEntity
	 * 
	 * @return if passed tileEntity represents a fluid tank 
	 *******************************************************************/
	public static boolean isFluidTankTileEntity(TileEntity tileEntity) {

		return tileEntity instanceof FluidTankTileEntityBase;
	}
	
	
	public static boolean isFluidTankBlock(BlockPos pos, World world) {

		return isFluidTankBlock(world.getBlockState(pos).getBlock());
	}
	
	public static boolean isFluidTankTileEntity(BlockPos pos, World world) {

		return isFluidTankTileEntity(world.getTileEntity(pos));
	}

	/******************************************************************
	 * isFluidTankAtPos(BlockPos pos, World world)
	 * 
	 * @param BlockPos pos
	 * 
	 * @param World world
	 * 
	 * @return if the Block and TileEntity at pos represent a fluidTank
	 ******************************************************************/
	public static boolean isFluidTankAtPos(BlockPos pos, World world) {

		return isFluidTankBlock(pos, world) && isFluidTankTileEntity(pos, world);
	}

	public static boolean isWaterTankBlock(Block block) {

		return block instanceof WaterTankBlockBase;
	}

	public static boolean isWaterTankTileEntity(TileEntity tileEntity) {

		return tileEntity instanceof WaterTankTileEntityBase;
	}
	
	public static boolean isWaterTankBlock(BlockPos pos, World world) {

		return isWaterTankBlock(world.getBlockState(pos).getBlock());
	}

	public static boolean isWaterTankTileEntity(BlockPos pos, World world) {

		return isWaterTankTileEntity(world.getTileEntity(pos));
	}
	
	public static boolean isWaterTankAtPos(BlockPos pos, World world) {
		
		return isWaterTankBlock(pos, world) && isWaterTankTileEntity(pos, world);
	}

	/*******************************************************************************
	 * getTransferAmount(ModFluidTank inTank, ModFluidTank outTank)
	 * 
	 * @param ModFluidTank inTank
	 * 
	 * @param ModFluidTank outTank
	 * 
	 * @return The amount of fluid to transfered to the inTank
	 * 
	 * Returns the amount of fluid to be transfered by comparing 
	 * the space left in the inTank and how much fluid outTank is holding
	 *******************************************************************************/
	public static int getTransferAmount(ModFluidTank inTank, ModFluidTank outTank) {

		return inTank.getSpace() < outTank.getFluidAmount() ? inTank.getSpace() : outTank.getFluidAmount();
	}

	/***************************************************************************************
	 * getTransferedFluid(ModFluidTank inTank, ModFluidTank outTank)
	 * 
	 * @param ModFluidTank inTank
	 * 
	 * @param ModFluidTank outTank
	 * 
	 * @return the FluidStack to be transfered 
	 * 
	 * Returns the FluidStack to be transfered by comparing the amount of space the inTank has
	 * and how much fluid outTank is holding
	 ***************************************************************************************/
	public static FluidStack getTransferedFluid(ModFluidTank inTank, ModFluidTank outTank) {

		return outTank.drain(getTransferAmount(inTank, outTank), FluidAction.EXECUTE);

	}

	/*************************************************************************
	 * fillFromUpper()
	 * 
	 * @param FluidTankTileEntityBase tankTile
	 * 
	 * Pulls fluid from tank sitting on top of tank being used
	 *************************************************************************/
	public static void fillFromUpper(final FluidTankTileEntityBase tankTile) {

		moveFluid(tankTile.getPos(), PosHelper.getAboveTileEntity(tankTile), tankTile.getWorld());
	}

	/************************************************************************
	 * drainToLower(final FluidTankTileEntityBase tankTile)
	 * 
	 * @param FluidTankTileEntity tankTile
	 * 
	 * Fills the tank sitting below the tank being used
	 ************************************************************************/
	public static void drainToLower(final FluidTankTileEntityBase tankTile) {

		moveFluid(PosHelper.getBelowTileEntity(tankTile), tankTile.getPos(), tankTile.getWorld());
	}


	/***************************************************************************
	 * moveFluid(final FluidTankTileEntityBase tankTile)
	 * 
	 * @param BlockPos inPos
	 * 
	 * @param BlockPos outPos
	 * 
	 * @param World world
	 * 
	 * Moves FluidStack from outTank to inTank
	 ***************************************************************************/
	public static void moveFluid(BlockPos inPos, BlockPos outPos, World world) {

		if(!world.isRemote) {

			if (isFluidTankAtPos(inPos, world) && isFluidTankAtPos(outPos, world)) {

				ModFluidTank inTank = ((FluidTankTileEntityBase) world.getTileEntity(inPos)).getTank();
				ModFluidTank outTank = ((FluidTankTileEntityBase) world.getTileEntity(outPos)).getTank();

				if(inTank.getFluid().isFluidEqual(outTank.getFluid()) || (inTank.isEmpty() && inTank.canHoldFluidType(outTank.getFluid()))) {

					while (!inTank.isFull() && !outTank.isEmpty()) {

						inTank.fill(getTransferedFluid(inTank, outTank), FluidAction.EXECUTE);
					}
				}
			}
		}
	}


}
