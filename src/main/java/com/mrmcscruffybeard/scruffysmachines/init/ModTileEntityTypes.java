package com.mrmcscruffybeard.scruffysmachines.init;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.BrickFurnaceTileEntity;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.LeatherChestTileEntity;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.StoneWaterTankTileEntity;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.WoodWaterPumpTileEntity;
import com.mrmcscruffybeard.scruffysmachines.objects.tileentities.WoodWaterTankTileEntity;

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

	
	//Fluid Tank Tile Entities
	public static final RegistryObject<TileEntityType<StoneWaterTankTileEntity>> STONE_WATER_TANK = TILE_ENTITY_TYPES
			.register(StoneWaterTankTileEntity.ID, () -> TileEntityType.Builder.create(StoneWaterTankTileEntity::new,  BlockInit.STONE_WATER_TANK.get()).build(null));

	public static final RegistryObject<TileEntityType<WoodWaterTankTileEntity>> WOOD_WATER_TANK = TILE_ENTITY_TYPES
			.register(WoodWaterTankTileEntity.ID, () -> TileEntityType.Builder.create(WoodWaterTankTileEntity::new, BlockInit.WOOD_WATER_TANK.get()).build(null));
	
	
	public static final RegistryObject<TileEntityType<WoodWaterPumpTileEntity>> WOOD_WATER_PUMP = TILE_ENTITY_TYPES
			.register(WoodWaterPumpTileEntity.ID, () -> TileEntityType.Builder.create(WoodWaterPumpTileEntity:: new , BlockInit.WOOD_WATER_PUMP.get()).build(null));


}//ModTileEntityTypes
