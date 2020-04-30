package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.FluidTankBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.ModFluidTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class TankHelper {

	public static boolean isFluidTankBlock(Block block) {

		return block instanceof FluidTankBlockBase;
	}

	public static boolean isFluidTankTileEntity(TileEntity te) {

		return te instanceof FluidTankTileEntityBase;
	}


	/*****************************************************
	 * Fills from tank above
	 *****************************************************/
	public static void fillFromUpper(final FluidTankTileEntityBase tankTile) {

		moveFluid(tankTile.getPos(), PosHelper.getAbovePos(tankTile), tankTile.getWorld());
	}

	public static void drainToLower(final FluidTankTileEntityBase tankTile) {
		
		moveFluid(PosHelper.getBelowPos(tankTile), tankTile.getPos(), tankTile.getWorld());
	}
	
	public static void moveFluid(BlockPos inPos, BlockPos outPos, World world) {

		if(!world.isRemote) {

			Block inBlock = world.getBlockState(inPos).getBlock();
			Block outBlock = world.getBlockState(outPos).getBlock();

			if (isFluidTankBlock(inBlock) && isFluidTankBlock(outBlock)) {
				
				if(isFluidTankTileEntity(world.getTileEntity(inPos))&& isFluidTankTileEntity(world.getTileEntity(outPos))) {
					
					ModFluidTank inTank = ((FluidTankTileEntityBase) world.getTileEntity(inPos)).getTank();
					ModFluidTank outTank = ((FluidTankTileEntityBase) world.getTileEntity(outPos)).getTank();
					
					if(inTank.getFluid().isFluidEqual(outTank.getFluid()) || inTank.isEmpty()) {
						
						while (!inTank.isFull() && !outTank.isEmpty()) {
							
							//int amount = Math.min(inTank.getSpace(), outTank.getFluidAmount());
							
							int amount = inTank.getSpace() < outTank.getFluidAmount() ? inTank.getSpace() : outTank.getFluidAmount();
							
							FluidStack flow = outTank.drain(amount, FluidAction.EXECUTE);
							
							inTank.fill(flow, FluidAction.EXECUTE);
						}
					}
				}
			}
		}
	}//Move fluid

	
}
