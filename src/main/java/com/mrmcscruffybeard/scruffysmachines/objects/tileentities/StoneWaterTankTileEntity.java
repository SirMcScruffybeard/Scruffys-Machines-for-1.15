package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterTankTileEntityBase;

import net.minecraft.tileentity.TileEntityType;

public class StoneWaterTankTileEntity extends WaterTankTileEntityBase {

	public static final String ID = StoneWaterTankBlock.ID;

	private static final int BUCKETS = 5;

	private StoneWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);
		
		tankIn.setTankTileEntity(this);

	}

	public StoneWaterTankTileEntity() {
		
		this(ModTileEntityTypes.STONE_WATER_TANK.get(), new WaterTank(BUCKETS * BUCKET_VOLUME));

	}

}
