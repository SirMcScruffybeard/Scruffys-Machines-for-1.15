package com.mrmcscruffybeard.scruffysmachines.objects.tanks;

public interface ILeakable {
	
	void tick();
	
	boolean isOnLeakCooldown();
	
	void setLeakCooldown(int ticks);
	
	void leak();
}
