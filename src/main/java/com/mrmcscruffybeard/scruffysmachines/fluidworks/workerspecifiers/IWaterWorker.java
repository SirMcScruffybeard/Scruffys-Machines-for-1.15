package com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.tanktiles.WaterTankTile;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public interface IWaterWorker extends IWorkerSpecifier{

	@Override
	default boolean canWorkFluid(FluidStack resource) {
		
		return isWater(resource);
	}
	
	@Override
	default boolean canWorkLiquids() {
		
		return true;
	}
	
	@Override
	default boolean canWorkVapors() {
		
		return false;
	}
	
	static boolean isWaterTank(TileEntity tileIn) {
		
		return tileIn instanceof WaterTankTile;
	}
	
	static boolean isWaterTankAtPos(BlockPos pos, World world) {
		
		return isWaterTank(world.getTileEntity(pos));
	}
	
	static WaterTankTile getWaterTankAtPos(BlockPos pos, World world) {
		
		return (WaterTankTile) world.getTileEntity(pos);
	}
	
	public static boolean isWater(FluidStack rescoruce) {

		return IWorkerSpecifier.isFluidType(rescoruce, Fluids.WATER);
	}
	
	public static boolean isWater(Fluid fluid) {

		return fluid == Fluids.WATER;
	}
}
