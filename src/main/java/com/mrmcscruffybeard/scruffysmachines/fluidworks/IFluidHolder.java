package com.mrmcscruffybeard.scruffysmachines.fluidworks;

import net.minecraft.fluid.Fluid;
import net.minecraft.tileentity.TileEntity;

public interface IFluidHolder {

	boolean isFull();

	boolean canHoldBuckWorth();

	boolean hasBucketWorth();

	Fluid getFluidType();

	TileEntity getTileEntity();

	void setTileEntity(TileEntity tileIn);

}
