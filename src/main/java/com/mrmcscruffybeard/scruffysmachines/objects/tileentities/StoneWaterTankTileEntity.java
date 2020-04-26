package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class StoneWaterTankTileEntity extends TileEntity {

	public static final String ID = StoneWaterTankBlock.ID;
	
	public StoneWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}
	
	public static StoneWaterTankTileEntity( ) {
		
		this(ModTileEntityTypes.STONE_WATER_TANK.get());
	}

}
