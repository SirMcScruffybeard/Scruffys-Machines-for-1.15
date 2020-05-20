package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.FluidPumpBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidPumpTileEntityBase;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PumpHelper {

	public static boolean isFluidPumpBlock(Block block) {

		return block instanceof FluidPumpBlockBase;
	}

	public static boolean isFluidPumpTileEntity(TileEntity te) {

		return te instanceof FluidPumpTileEntityBase;
	}

	public static boolean isFluidPumpAtPos(BlockPos pos, World world) {

		return isFluidPumpBlock(world.getBlockState(pos).getBlock()) && isFluidPumpTileEntity(world.getTileEntity(pos));
	}

	public static FluidPumpTileEntityBase getFluidPumpTileEntityBase(BlockPos pos, World world) {
		
		return (FluidPumpTileEntityBase) world.getTileEntity(pos);
	}
	
	public static BlockPos getSourcePump(TileEntity tile) {

		World world = tile.getWorld();

		BlockPos checkPos = tile.getPos().add(Direction.DOWN.getDirectionVec());

		if (!world.isRemote) {

			if (PumpHelper.isFluidPumpAtPos(checkPos, world)) {

				return checkPos;
			}

			else {

				while (TankHelper.isFluidTankAtPos(checkPos, world)) {

					checkPos.add(Direction.DOWN.getDirectionVec());
				}
			}
		}

		return checkPos;
	}

}
