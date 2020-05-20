package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

import com.mrmcscruffybeard.scruffysmachines.objects.fluids.vapors.Vapor;

import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;

/***************************************************************************************
 * 
 * @author SirMcScruffyBeard
 * 
 * Lvl: 0
 *
 * 	This is the start of fluid handling. It is inspired by the FluidTank and
 * fluid handler templates provided by Forge. Found at {@link} net.minecraftforge.fluids 	
 ****************************************************************************************/
public interface IFluidWorker {

	int getCapacity();

	void setCapacity(int capIn);

	Fluid getFluidType();

	FluidStack getFluid();

	void setFluid(FluidStack resource);

	boolean isEmpty();

	int getAmount();

	int getId();

	void setId(int idIn);
	
	int getSpace();

	default public boolean canConductVapors() {

		return true;
	}

	default boolean canConductFluids() {

		return true;
	}

	default boolean canConductFluid(Fluid fluid) {

		return canConductFluids();
	}
	
	default boolean canConductFluid(FluidStack resource) {
		
		return this.canConductFluid(resource.getFluid());
	}
	
	default boolean canConductVapor(Vapor vapor) {
		
		return canConductVapors();
	}

	boolean isFluidValid(FluidStack resoruce);

}
