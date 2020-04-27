package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.BucketSwapHelper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class StoneWaterTankTileEntity extends TileEntity {

	public static final String ID = StoneWaterTankBlock.ID;

	public static final int BUCKETS = 5;

	public static final int CAPACITY = BUCKETS * WaterTank.BUCKET_VOLUME;

	private WaterTank tank = new WaterTank(CAPACITY);

	private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

	public StoneWaterTankTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);

	}

	public StoneWaterTankTileEntity() {

		this(ModTileEntityTypes.STONE_WATER_TANK.get());

	}

	@Override
	public void read(CompoundNBT compound) {

		super.read(compound);

		tank.readFromNBT(compound);

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {

		compound = super.write(compound);

		tank.writeToNBT(compound);

		return compound;


	}


	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction facing) {

		if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {

			return holder.cast();
		}

		return super.getCapability(cap, facing);
	}

	public void fillWithBucket(PlayerEntity player) {
		
		if(tank.getSpace() >= WaterTank.BUCKET_VOLUME) {

			tank.fill(new FluidStack(Fluids.WATER, FluidAttributes.BUCKET_VOLUME), FluidAction.EXECUTE);

			BucketSwapHelper.emptyBucket(player);
		}

	}

	public void drainWithBucket(PlayerEntity player) {
		
		if(tank.getFluidAmount() >= WaterTank.BUCKET_VOLUME) {

			tank.drain(FluidAttributes.BUCKET_VOLUME, FluidAction.EXECUTE);

			BucketSwapHelper.fillWaterBucket(player);
		}

	}

}
