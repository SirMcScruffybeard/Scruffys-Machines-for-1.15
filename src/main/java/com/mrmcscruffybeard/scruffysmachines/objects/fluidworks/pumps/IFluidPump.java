package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.pumps;

import com.mrmcscruffybeard.scruffysmachines.objects.fluids.vapors.Vapor;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.IFluidWorker;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidPump extends IFluidWorker{

	default boolean canPumpFluid(Fluid fluid) {
		
		return canConductFluid(fluid);
	}
	
	default boolean canPumpVapor(Vapor vapor) {
		
		return canConductVapor(vapor);
	}
	
	default boolean canPumpFluids() {
		
		return canConductFluids();
	}
	
	default boolean canPumpVapors() {
		
		return canConductVapors();
	}
	
	FluidStack drawFromDirection(int amount, Direction dir);
	
	int pumpToDirection(Direction dir);
	
	
	
}
