package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.FluidTank;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.ILiquidTankHandler;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.LiquidTank;

import net.minecraft.tileentity.TileEntityType;

public abstract class LiquidTankTileEntity extends FluidTankTileEntity implements ILiquidTankHandler{

	public LiquidTankTileEntity(TileEntityType<?> tileEntityTypeIn, FluidTank tankIn) {
		super(tileEntityTypeIn, tankIn);
		
	}

	@Override
	public boolean canFillWithBucket(LiquidTank tank) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fillWithBucket() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
