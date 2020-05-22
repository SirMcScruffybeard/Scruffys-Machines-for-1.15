package com.mrmcscruffybeard.scruffysmachines.fluidworks;

import net.minecraft.tileentity.ITickableTileEntity;

public interface ILeakableTileEntity extends ILeakable, ITickableTileEntity {

	final static int TICKS_PER_SECOND = 20;
	
	void tick();
	
	boolean isOnLeakCooldown();
	
	void setLeakCooldown(int ticks);
	
	void leak();
}
