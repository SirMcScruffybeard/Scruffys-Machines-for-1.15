package com.sirscruffybeard.scruffysmachines.tileentity;

import com.sirscruffybeard.scruffysmachines.tileentity.bases.FurnaceBaseTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrickFurnaceTileEntity extends FurnaceBaseTileEntity {

	public BrickFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
		// TODO Auto-generated method stub

	}

}
