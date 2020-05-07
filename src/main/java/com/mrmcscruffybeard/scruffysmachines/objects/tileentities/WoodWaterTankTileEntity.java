package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.WoodWaterTankBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.ILeakable;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterTankTileEntityBase;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class WoodWaterTankTileEntity extends WaterTankTileEntityBase implements ITickableTileEntity, ILeakable{

	public static final String ID = WoodWaterTankBlock.ID;
	
	public static final int BUCKETS = 5;
	public static final int LEAK_AMOUNT = 10; //in mB
	
	//20 ticks per second
	private final int NUM_COOLDOWN_TICKS = 200;
	private int leakCooldown = -1;
	
	private WoodWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {
		super(tileEntityTypeIn, tankIn);
		
		this.setInTankTileEntity(this);
		
	}
	
	public WoodWaterTankTileEntity() {
		
		this(ModTileEntityTypes.WOOD_WATER_TANK.get(), new WaterTank(BUCKETS * BUCKET_VOLUME));
	}

	@Override
	public void tick() {
		
		if(!world.isRemote) {
			
			--this.leakCooldown;
			
			if(!isOnLeakCooldown() && !isEmpty()) {
				
				this.setLeakCooldown(this.NUM_COOLDOWN_TICKS);
				
				leak();
				
			}
		}
	}

	@Override
	public boolean isOnLeakCooldown() { return this.leakCooldown > 0;}

	@Override
	public void setLeakCooldown(int ticks) { this.leakCooldown = ticks;	}
	
	@Override
	public void leak() {
		
		if(!isEmpty() && !world.isRemote) {
			
			tank.drain(LEAK_AMOUNT, FluidAction.EXECUTE);
		}
	}

}
