package com.sirscruffybeard.scruffysmachines.init;

import com.sirscruffybeard.scruffysmachines.ScruffysMachines;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFluidTankBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.CrumblerBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, ScruffysMachines.MOD_ID);
	
	public static final RegistryObject<Block> OVEN_BRICKS = BLOCKS.register("oven_bricks_block", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f, 3.5f).sound(SoundType.STONE).harvestTool(ToolType.PICKAXE)));

	public static final RegistryObject<Block> BRICK_FURNACE = BLOCKS.register(BrickFurnaceBlock.ID, () -> new BrickFurnaceBlock(Block.Properties.create(BrickFurnaceBlock.MATERIAL).
			hardnessAndResistance(BrickFurnaceBlock.HARDNESS, BrickFurnaceBlock.RESISTANCE).sound(BrickFurnaceBlock.SOUND).harvestTool(BrickFurnaceBlock.TOOL)));
	
	public static final RegistryObject<Block> BRICK_TANK = BLOCKS.register(BrickFluidTankBlock.ID, () -> new BrickFurnaceBlock(Block.Properties.create(BrickFurnaceBlock.MATERIAL).
			hardnessAndResistance(BrickFurnaceBlock.HARDNESS, BrickFurnaceBlock.RESISTANCE).
			sound(BrickFurnaceBlock.SOUND).harvestTool(BrickFurnaceBlock.TOOL)));
	
	public static final RegistryObject<Block> LEATHER_CHEST = BLOCKS.register(LeatherChestBlock.ID, () -> new LeatherChestBlock(Block.Properties.create(LeatherChestBlock.MATERIAL).
			hardnessAndResistance(LeatherChestBlock.HARDNESS, LeatherChestBlock.RESISTANCE).sound(LeatherChestBlock.SOUND)));
	
	public static final RegistryObject<Block> CRUMBLER = BLOCKS.register(CrumblerBlock.ID, () -> new CrumblerBlock(Block.Properties.create(CrumblerBlock.MATERIAL).
			hardnessAndResistance(CrumblerBlock.HARDNESS, CrumblerBlock.RESISTANCE).sound(CrumblerBlock.SOUND).harvestTool(CrumblerBlock.TOOL)));
}