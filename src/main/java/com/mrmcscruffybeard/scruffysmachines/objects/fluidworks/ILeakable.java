package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

public interface ILeakable {
	
	final static int TICKS_PER_SECOND = 20;
	
	void tick();
	
	boolean isOnLeakCooldown();
	
	void setLeakCooldown(int ticks);
	
	void leak();
}
