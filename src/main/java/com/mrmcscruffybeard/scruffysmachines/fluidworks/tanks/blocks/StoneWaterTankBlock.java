package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.blocks;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class StoneWaterTankBlock extends WaterTankBlock {

	public static String ID = "stone" + ID_WATER_TANK;

	public static final Material MATERIAL = Material.ROCK;

	public StoneWaterTankBlock(Properties properties) {
		super(properties);

	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) { 

		return ModTileEntityTypes.STONE_WATER_TANK.get().create();
	}





}//class
