package com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.tileentities;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.WoodWaterTankBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.ILeakable;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.FluidTank;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.IWaterTankHandler;
import com.mrmcscruffybeard.scruffysmachines.objects.fluidworks.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

public class WoodWaterTankTileEntity extends FluidTankTileEntity implements IWaterTankHandler, ITickableTileEntity, ILeakable{

	public static final String ID = WoodWaterTankBlock.ID;

	public static final int BUCKETS = 5;
	public static final int LEAK_AMOUNT = 5; //in mB

	//20 ticks per second
	private final int NUM_COOLDOWN_TICKS = 200;
	private int leakCooldown = -1;

	private WoodWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn, WaterTank tankIn) {

		super(tileEntityTypeIn, tankIn);
		tankIn.setTileEntity(this);

	}

	public WoodWaterTankTileEntity() {

		this(ModTileEntityTypes.WOOD_WATER_TANK.get(), new WaterTank(BUCKETS * FluidAttributes.BUCKET_VOLUME));
	}

	public int fill(FluidStack resource, FluidAction action, Direction dir) {

		if(FluidHelper.isWater(resource) && resource != null) {

			return super.fill(resource, action, dir, (WaterTank) worker);
		}

		else { return 0; }
	}

	@Override
	public int fill(FluidStack resource, FluidAction action, Direction dir, FluidTank tank) {

		if(FluidHelper.isWater(resource) && resource != null) {

			return super.fill(resource, action, dir, tank);
		}

		else { return 0; }
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

			drain(LEAK_AMOUNT, FluidAction.EXECUTE, null, (WaterTank) worker);
		}
	}


}
