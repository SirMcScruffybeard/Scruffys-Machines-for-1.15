package com.mrmcscruffybeard.scruffysmachines.events;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.FluidWorkerTile;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.tanks.tileetnties.WaterTankTile;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.workerspecifiers.IWaterWorker;
import com.mrmcscruffybeard.scruffysmachines.objects.items.tools.WoodDipstickItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ScruffysMachines.MOD_ID, bus = Bus.FORGE)
public class TankMesureEvent {

	@SubscribeEvent
	public static void waterTankMeasureEvent(RightClickBlock event) {

		PlayerEntity player = event.getPlayer();
		World world = event.getWorld();
		BlockPos pos = event.getPos();

		if(!world.isRemote) {

			if(player.getHeldItemMainhand().getItem() instanceof WoodDipstickItem) {

				if(IWaterWorker.isWaterTankAtPos(pos, world)) {

					WaterTankTile tankTile = IWaterWorker.getWaterTankAtPos(pos, world);

					player.sendMessage(getMesurment(tankTile));
					
					//ScruffysMachines.LOGGER.info(getMesurment(tankTile).toString());

				}
			}
		}

	}

	private static StringTextComponent getMesurment(FluidWorkerTile worker) {

		int amount = worker.getFluidAmount();

		if (amount == 0) {

			return new StringTextComponent("Empty");
		}



		return new StringTextComponent(String.valueOf(amount) + "mB of " + worker.getFluid().getDisplayName().getFormattedText());
	}

}//class
