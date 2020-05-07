package com.mrmcscruffybeard.scruffysmachines.util;

public class PosSpokes {

	public enum CenterSpokes {
		
		NORTH(0, 0, -1),
		NORTH_EAST(1, 0, -1),
		EAST(1, 0, 0),
		SOUTH_EAST(1, 0, 1),
		SOUTH(0, 0, 1),
		SOUTH_WEST(-1, 0, 1),
		WEST(-1, 0, 0),
		NORTH_WEST(-1, 0, -1);
		
		private int x;
		private int y;
		private int z;
		
		CenterSpokes(int x, int y, int z) {
			
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public int getX() {return x;}
		
		public int getY() {return y;}
		
		public int getZ() {return z;}
	}
}
