package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.WaterTankBlockBase;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class WoodWaterTankBlock extends WaterTankBlockBase {

	public static final String ID = "wood" + ID_WATER_TANK;
	
	public static final Material MATERIAL = Material.WOOD;
	
	public WoodWaterTankBlock(Properties properties) {
		super(properties);
 		
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		// TODO Auto-generated method stub

	}

}
