package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities;

import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.IWaterTankHandler;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
/******************************************************************************************
 * WaterTankTileEntity
 * 
 * @author SirMcScruffyBeard
 *
 *	A TileEntity that handles a single water tank
 *******************************************************************************************/
public abstract class WaterTankTileEntity extends LiquidTankTileEntity implements IWaterTankHandler{

	public WaterTankTileEntity(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);
		
	}

	public int fill(FluidStack resource, FluidAction action, Direction dir) {

		if(FluidHelper.isWater(resource) && resource != null) {

			return super.fill(resource, action, dir, (WaterTank) worker);
		}

		else { return 0; }
	}

	@Override
	public int fill(FluidStack resource, FluidAction action, Direction dir, WaterTank tank) {

		if(FluidHelper.isWater(resource) && resource != null) {

			return super.fill(resource, action, dir, tank);
		}

		else { return 0; }
	}

	

}
