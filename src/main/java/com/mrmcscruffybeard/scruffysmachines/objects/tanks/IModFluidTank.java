package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public interface IModFluidTank extends IFluidTank{

	void empty();
	
	boolean isFull();
	
	void setTankTileEntity(TileEntity tileIn);
	
	boolean canHoldFluidType(FluidStack fluidStack);
	
	default boolean canHoldGas() { return false; };
}
