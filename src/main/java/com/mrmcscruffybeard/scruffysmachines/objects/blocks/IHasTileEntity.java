package com.mrmcscruffybeard.scruffysmachines.objects.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.extensions.IForgeBlock;

public interface IHasTileEntity extends IForgeBlock{

	default boolean hasTileEntity(BlockState state) {return true; }
	
	TileEntity createTileEntity(BlockState state, IBlockReader world);
}
