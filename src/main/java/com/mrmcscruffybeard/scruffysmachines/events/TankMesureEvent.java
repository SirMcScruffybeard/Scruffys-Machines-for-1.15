package com.mrmcscruffybeard.scruffysmachines.events;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.bases.WaterTankBlockBase;
import com.mrmcscruffybeard.scruffysmachines.objects.items.tools.WoodDipstickItem;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.FluidTankTileEntityBase;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.bases.WaterTankTileEntityBase;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = ScruffysMachines.MOD_ID, bus = Bus.FORGE)
public class TankMesureEvent {

	@SubscribeEvent
	public static void waterTankMeasureEvent(RightClickBlock event) {

		PlayerEntity player = event.getPlayer();
		
		if(!player.world.isRemote) {

			if(player.getHeldItemMainhand().getItem() instanceof WoodDipstickItem) {

				if(event.getWorld().getBlockState(event.getPos()).getBlock() instanceof WaterTankBlockBase) {

					WaterTankTileEntityBase tankTile = (WaterTankTileEntityBase) event.getWorld().getTileEntity(event.getPos());


					player.sendMessage(getMesurment(tankTile));
				}
			}
		}

	}

	private static StringTextComponent getMesurment(FluidTankTileEntityBase tank) {
		
		int amount = tank.getFluidAmount();
		
		if (amount == 0) {
			
			return new StringTextComponent("Empty");
		}
		
		
		
		return new StringTextComponent(String.valueOf(amount) + "mB of " + tank.getFluid().getDisplayName().getFormattedText());
	}

}//class
