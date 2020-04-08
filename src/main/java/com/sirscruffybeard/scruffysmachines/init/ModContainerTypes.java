package com.sirscruffybeard.scruffysmachines.init;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines;
import com.sirscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;
import com.sirscruffybeard.scruffysmachines.objects.container.LeatherChestContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

	
	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, ScruffysMachines.MOD_ID);
	
	public static final RegistryObject<ContainerType<LeatherChestContainer>> LEATHER_CHEST = CONTAINER_TYPES
			.register(LeatherChestBlock.ID, () -> IForgeContainerType.create(LeatherChestContainer::new));
	

}
