package com.mrmcscruffybeard.scruffysmachines.init;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.tanktiles.StoneWaterTankTile;
import com.mrmcscruffybeard.scruffysmachines.fluidworks.tanktiles.WoodWaterTankTile;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.LeatherChestTileEntity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, ScruffysMachines.MOD_ID);




	public static final  RegistryObject<TileEntityType<BrickFurnaceTileEntity>> BRICK_FURNACE = TILE_ENTITY_TYPES
			.register(BrickFurnaceTileEntity.ID, () -> TileEntityType.Builder.create(BrickFurnaceTileEntity::new,  BlockInit.BRICK_FURNACE.get()).build(null));	



	public static final RegistryObject<TileEntityType<LeatherChestTileEntity>> LEATHER_CHEST = TILE_ENTITY_TYPES
			.register(LeatherChestTileEntity.ID, () -> TileEntityType.Builder.create(LeatherChestTileEntity::new,  BlockInit.LEATHER_CHEST.get()).build(null));

	
	
	public static final RegistryObject<TileEntityType<StoneWaterTankTile>> STONE_WATER_TANK = TILE_ENTITY_TYPES
			.register(StoneWaterTankTile.ID, () -> TileEntityType.Builder.create(StoneWaterTankTile::new,  BlockInit.STONE_WATER_TANK.get()).build(null));

	public static final RegistryObject<TileEntityType<WoodWaterTankTile>> WOOD_WATER_TANK = TILE_ENTITY_TYPES
			.register(WoodWaterTankTile.ID, () -> TileEntityType.Builder.create(WoodWaterTankTile ::new, BlockInit.WOOD_WATER_TANK.get()).build(null));
	
}//ModTileEntityTypes
