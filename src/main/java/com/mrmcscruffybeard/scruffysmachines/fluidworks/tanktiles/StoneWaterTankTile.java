package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanktiles;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;
import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;

import net.minecraft.tileentity.TileEntityType;

public class StoneWaterTankTile extends WaterTankTile implements IWaterWorker{

	public static final String ID = StoneWaterTankBlock.ID;
	
	public static final int CAPACITY = 5000;
	
	public StoneWaterTankTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {
		super(tileEntityTypeIn, capacityIn);
		
		this.setTileInTank(this);
		
	}
	
	public StoneWaterTankTile() {
		
		this(ModTileEntityTypes.STONE_WATER_TANK.get(), CAPACITY);
	}

}
