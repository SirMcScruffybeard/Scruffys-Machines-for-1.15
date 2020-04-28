package com.mrmcscruffybeard.scruffysmachines.init;

import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.CrumblerBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.StoneWaterTankBlock;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ScruffysMachines.MOD_ID);
	
	public static final RegistryObject<Block> OVEN_BRICKS = BLOCKS.register("oven_bricks_block", () -> new Block(Block.Properties.create(
			Material.ROCK).hardnessAndResistance(3.5f, 3.5f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));
	
	public static final RegistryObject<Block> LEATHER_BLOCK = BLOCKS.register("leather_block", () -> new Block(Block.Properties.create(
			Material.WOOL).hardnessAndResistance(1.5f, 0.5f).sound(SoundType.CLOTH)));
	
	
	public static final RegistryObject<Block> BRICK_FURNACE = BLOCKS.register(BrickFurnaceBlock.ID, () -> 
			new BrickFurnaceBlock(Block.Properties.create(Material.ROCK)));
	
	
	
	public static final RegistryObject<Block> LEATHER_CHEST = BLOCKS.register(LeatherChestBlock.ID, () -> 
			new LeatherChestBlock(Block.Properties.create(LeatherChestBlock.MATERIAL).
			hardnessAndResistance(LeatherChestBlock.HARDNESS, LeatherChestBlock.RESISTANCE).sound(LeatherChestBlock.SOUND)));
	
	
	public static final RegistryObject<Block> CRUMBLER = BLOCKS.register(CrumblerBlock.ID, () -> 
			new CrumblerBlock(Block.Properties.create(Material.ROCK)));
	
	
	public static final RegistryObject<Block> STONE_WATER_TANK = BLOCKS.register(StoneWaterTankBlock.ID, () -> 
			new StoneWaterTankBlock(Block.Properties.create(StoneWaterTankBlock.MATERIAL).from(Blocks.STONE)));


}//BlockInit
