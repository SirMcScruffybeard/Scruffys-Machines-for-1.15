package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.FluidTankBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterTankTileEntityBase;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class StoneWaterTankTileEntity extends WaterTankTileEntityBase {

	public static final String ID = StoneWaterTankBlock.ID;

	private static final int BUCKETS = 5;

	private StoneWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);

	}

	public StoneWaterTankTileEntity() {
		
		this(ModTileEntityTypes.STONE_WATER_TANK.get(), new WaterTank(BUCKETS * BUCKET_VOLUME));
		
		this.setInTankTileEntity(this);

	}

}
