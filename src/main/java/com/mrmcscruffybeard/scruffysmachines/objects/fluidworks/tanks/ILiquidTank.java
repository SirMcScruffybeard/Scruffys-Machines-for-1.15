package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks;

/**********************************************************************
 * ILiquidTank
 * 
 * @author SirMcScruffyBeard
 *
 * Level: 2T
 * 
 * 	Narrows to tanks that can only hold liquids
 ***********************************************************************/
public interface ILiquidTank extends IFluidTank {

	@Override
	default boolean canConductVapors() {

		return false;
	}

}
