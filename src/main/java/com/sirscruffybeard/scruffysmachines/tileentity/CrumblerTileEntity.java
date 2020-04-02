package com.sirscruffybeard.scruffysmachines.tileentity;

import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class CrumblerTileEntity extends TileEntity{
	
	public CrumblerTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		
	}
	
	public CrumblerTileEntity() {
		
		this(ModTileEntityTypes.CRUMBLER.get());
	}

}
