package com.mrmcscruffybeard.scruffysmachines.fluidworks.pumps.tileentities;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WoodWaterPumpTile extends WaterPumpTile implements IWaterWorker {

	public WoodWaterPumpTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {
		super(tileEntityTypeIn, capacityIn);

	}

	public WoodWaterPumpTile() {
		
		
	}
	
	@Override
	public int drawFromSource(BlockPos pos, World world, Direction dir) {

		return 0;
	}

}
