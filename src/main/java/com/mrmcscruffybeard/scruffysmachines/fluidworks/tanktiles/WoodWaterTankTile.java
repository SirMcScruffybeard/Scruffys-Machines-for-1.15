package com.mrmcscruffybeard.scruffysmachines.fluidworks.tanktiles;

import com.mrmcscruffybeard.scruffysmachines.fluidworks.ILeakableTileEntity;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;
import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.WoodWaterTankBlock;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class WoodWaterTankTile extends WaterTankTile implements IWaterWorker, ILeakableTileEntity{

	public static final String ID = WoodWaterTankBlock.ID;

	public static final int CAPACITY = 4000;

	private int leakCooldown = 200;
	private final int NUM_COOLDOWN_TICKS = 200;
	public static final int LEAK_AMOUNT = 5; //in mB

	public WoodWaterTankTile(TileEntityType<?> tileEntityTypeIn, int capacityIn) {
		super(tileEntityTypeIn, capacityIn);

		this.setTileInTank(this);
	}

	public WoodWaterTankTile() {

		this(ModTileEntityTypes.WOOD_WATER_TANK.get(), CAPACITY);
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

			holder.drain(LEAK_AMOUNT, FluidAction.EXECUTE);
		}
	}

}
