package com.sirscruffybeard.scruffysmachines.util;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines;
import com.sirscruffybeard.scruffysmachines.gui.LeatherChestScreen;
import com.sirscruffybeard.scruffysmachines.init.ModContainerTypes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ScruffysMachines.MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSuscriber {

	@SubscribeEvent
	public static void clientSetup(FMLClientSetupEvent event) {
		
		ScreenManager.registerFactory(ModContainerTypes.LEATHER_CHEST.get(),LeatherChestScreen::new);
		
		
	}
	
}
