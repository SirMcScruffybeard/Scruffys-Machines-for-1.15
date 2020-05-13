package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.WoodWaterPumpBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.tanks.WaterTank;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterPumpTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.FluidHelper;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.PosHelper;
import com.mrmcscruffybeard.scruffysmachines.util.helpers.TankHelper;

import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;


public class WoodWaterPumpTileEntity extends WaterPumpTileEntityBase{

	public static final String ID = WoodWaterPumpBlock.ID;

	private final static int PUMP_CAPACITY = 100;

	private BlockPos waterPos;
	private BlockPos tankPos;

	public WoodWaterPumpTileEntity() {

		super(ModTileEntityTypes.WOOD_WATER_PUMP.get(), new WaterTank(PUMP_CAPACITY));

		tank.setTankTileEntity(this);

		this.pumpCapacity = PUMP_CAPACITY;
	}

	public void pump() {

		if (!world.isRemote) { 

			this.tankPos = pos.add(Direction.UP.getDirectionVec());

			if(FluidHelper.isWater(world, waterPos)) { 

				this.draw(world, waterPos);
			}

			if(TankHelper.isFluidTankAtPos(pos, world)) {
				
				this.pumpOut(world, tankPos);
			}
			
			else if(PosHelper.isAirAtPos(tankPos, world) && tank.isFull()) {

				FluidHelper.flood(tankPos, tank.getFluid(), world);

				tank.empty();
			}
		}//world not remote

	}//pump

	public void draw(World world, BlockPos pos) {

		if(!world.isRemote) {

			if(FluidHelper.isWater(world, pos)) {

				this.amountPumped += pumpCapacity;

				tank.fill(new FluidStack(Fluids.WATER, pumpCapacity), FluidAction.EXECUTE);
			}

			if(this.amountPumped == FluidAttributes.BUCKET_VOLUME) {

				world.setBlockState(waterPos, Blocks.AIR.getDefaultState());
			}
		}
	}

	public void pumpOut(World world, BlockPos pos) {
		
		int numTanks = 0;
		
		BlockPos checkPos = pos;
		
		
	}
	
	public void pumpOut0(World world, BlockPos pos) {

		if(!world.isRemote) {

			FluidTankTileEntityBase otherTank = (FluidTankTileEntityBase) world.getTileEntity(pos);

			if((!tank.isEmpty() && otherTank.canFill()) && (otherTank.isEmpty() || FluidHelper.isWater(otherTank.getFluid()))) {

				otherTank.fill(tank.drain(pumpCapacity, FluidAction.EXECUTE));
			}
		}
	}
}
