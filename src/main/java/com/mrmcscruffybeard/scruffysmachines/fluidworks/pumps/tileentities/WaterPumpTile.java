package com.mrmcscruffybeard.scruffysmachines.fluidworks.pumps.tileentities;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.pumps.IFluidPump;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public abstract class WaterPumpTile extends FluidPumpTile implements IFluidPump, IWaterWorker {
	
	public WaterPumpTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {
		super(tileEntityTypeIn, capacityIn);
		
	}



	

	

}
