package com.sirscruffybeard.scruffysmachines.init;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines;
import com.sirscruffybeard.scruffysmachines.container.BrickFurnaceContainer;
import com.sirscruffybeard.scruffysmachines.container.LeatherChestContainer;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

	
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, ScruffysMachines.MOD_ID);
	
	public static final RegistryObject<ContainerType<LeatherChestContainer>> LEATHER_CHEST = CONTAINER_TYPES
			.register(LeatherChestBlock.ID, () -> IForgeContainerType.create(LeatherChestContainer::new));
	
	public static final RegistryObject<ContainerType<BrickFurnaceContainer>> BRICK_FURNACE = CONTAINER_TYPES
			.register(BrickFurnaceBlock.ID, () -> IForgeContainerType.create(BrickFurnaceContainer::new));

	
}
