package com.mrmcscruffybeard.scruffysmachines.init;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines.ScruffysMachinesItemGroup;
import com.mrmcscruffybeard.scruffysmachines.objects.items.IronRivitItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ScruffysMachines.MOD_ID);

	public static RegistryObject<Item> STONE_DUST = ITEMS.register("stone_dust_item", () -> new Item(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	
	public static RegistryObject<Item> OVEN_BRICK = ITEMS.register("oven_brick_item", () -> new Item(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	
	public static RegistryObject<Item> TRIMMED_LEATHER = ITEMS.register("trimmed_leather_item", () -> new Item(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	public static RegistryObject<Item> LEATHER_SCRAP = ITEMS.register("leather_scrap_item", () -> new Item(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	
	
	public static RegistryObject<Item> IRON_RIVIT = ITEMS.register(IronRivitItem.ID, () -> new IronRivitItem(new Item.Properties().group(ScruffysMachinesItemGroup.instance)));
	
}//ItemInit
