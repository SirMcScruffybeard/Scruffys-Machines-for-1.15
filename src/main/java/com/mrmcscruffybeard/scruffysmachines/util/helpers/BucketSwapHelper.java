package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BucketSwapHelper {

	public static void swapBuckets(ItemStack bucketIn, PlayerEntity player) {
		
		player.setItemStackToSlot(EquipmentSlotType.MAINHAND, bucketIn);
	}
	
	public static ItemStack getEmptyBucket() {
		
		return new ItemStack(Items.BUCKET);
	}
	
	public static void emptyBucket(PlayerEntity player) {
		
		swapBuckets(getEmptyBucket(), player);
	}
	
	public static ItemStack getWaterBucket() {
		
		return new ItemStack(Items.WATER_BUCKET);
	}
	
	public static void fillWaterBucket(PlayerEntity player) {
		
		swapBuckets(getWaterBucket(), player);
	}
	
	public static ItemStack getLavaBucket() {
		
		return new ItemStack(Items.LAVA_BUCKET);
	}
	
	public static void fillLavaBucket(PlayerEntity player) {
		
		swapBuckets(getLavaBucket(), player);
	}
}
