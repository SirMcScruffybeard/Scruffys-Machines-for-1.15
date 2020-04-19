package com.mrmcscruffybeard.scruffysmachines.init;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ScruffysMachines.MOD_ID);

	public static final  RegistryObject<TileEntityType<BrickFurnaceTileEntity>> BRICK_FURNACE = TILE_ENTITY_TYPES
			.register(BrickFurnaceTileEntity.ID, () -> TileEntityType.Builder.create(BrickFurnaceTileEntity::new,  BlockInit.BRICK_FURNACE.get()).build(null));	
}//ModTileEntityTypes
