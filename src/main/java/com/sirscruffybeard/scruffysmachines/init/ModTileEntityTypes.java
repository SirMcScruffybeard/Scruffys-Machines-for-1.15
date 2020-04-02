package com.sirscruffybeard.scruffysmachines.init;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines;
import com.sirscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;
import com.sirscruffybeard.scruffysmachines.tileentity.LeatherChestTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ScruffysMachines.MOD_ID);
	
	public static final RegistryObject<TileEntityType<LeatherChestTileEntity>> LEATHER_CHEST = TILE_ENTITY_TYPES
			.register(LeatherChestBlock.ID, () -> TileEntityType.Builder.create(LeatherChestTileEntity::new,  BlockInit.LEATHER_CHEST.get()).build(null));
	
}