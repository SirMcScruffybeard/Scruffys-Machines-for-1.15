package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks;

public interface ILeakable {
	
	void tick();
	
	boolean isOnLeakCooldown();
	
	void setLeakCooldown(int ticks);
	
	void leak();
}
