package com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases;

import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.FluidStack;

public abstract class WaterPumpTileEntityBase extends FluidPumpTileEntityBase{

	public WaterPumpTileEntityBase(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);
		
	}
	
	public boolean isFluidValid(int tank, FluidStack stack) {

		return FluidHelper.isFluidType(stack, Fluids.WATER);
	}

}
