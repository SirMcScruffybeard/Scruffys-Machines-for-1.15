package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.blocks;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class WoodWaterTankBlock extends WaterTankBlock {

	public static final String ID = "wood" + ID_WATER_TANK;
	
	public static final Material MATERIAL = Material.WOOD;
	
	public WoodWaterTankBlock(Properties properties) {
		super(properties);
 		
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		
		return ModTileEntityTypes.WOOD_WATER_TANK.get().create();
	}

}
