package com.sirscruffybeard.scruffysmachines.init;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines;
import com.sirscruffybeard.scruffysmachines.ScruffysMachines.ScruffysMachinesItemGroup;
import com.sirscruffybeard.scruffysmachines.objects.items.IronSteamPodItem;
import com.sirscruffybeard.scruffysmachines.objects.items.LeatherSteamPodItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ScruffysMachines.MOD_ID);
	
	public static RegistryObject<Item> IRON_RIVIT = ITEMS.register("iron_rivet_item", () -> new Item(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	
	public static RegistryObject<Item> STONE_DUST = ITEMS.register("stone_dust_item", () -> new Item(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	
	
	
	
	
	
	
	public static RegistryObject<Item> LEATHER_STEAM_POD = ITEMS.register(LeatherSteamPodItem.ID, () -> new Item(new LeatherSteamPodItem.Properties().group(ScruffysMachinesItemGroup.instance)));
	public static RegistryObject<Item> IRON_STEAM_POD = ITEMS.register(IronSteamPodItem.ID, () -> new Item(new IronSteamPodItem.Properties().group(ScruffysMachinesItemGroup.instance)));
}
