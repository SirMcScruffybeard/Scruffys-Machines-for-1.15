package com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases;

import com.mrmcscruffybeard.scruffysmachines.objects.blocks.IHasFacing;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.IHasTileEntity;

import net.minecraft.block.Block;

public abstract class FluidPumpBlockBase extends Block implements IHasFacing, IHasTileEntity{

	public FluidPumpBlockBase(Properties properties) {
		super(properties);

	}

}
