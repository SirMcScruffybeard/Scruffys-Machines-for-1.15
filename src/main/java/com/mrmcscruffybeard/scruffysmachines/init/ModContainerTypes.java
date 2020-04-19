package com.mrmcscruffybeard.scruffysmachines.init;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.containers.BrickFurnaceContainer;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {

	public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, ScruffysMachines.MOD_ID);

	public static final RegistryObject<ContainerType<BrickFurnaceContainer>> BRICK_FURNACE = CONTAINER_TYPES
    		.register(BrickFurnaceContainer.ID, () -> IForgeContainerType.create(BrickFurnaceContainer::new));

}//ModContainerTypes
