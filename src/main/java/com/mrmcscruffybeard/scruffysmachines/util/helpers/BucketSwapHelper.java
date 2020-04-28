package com.mrmcscruffybeard.scruffysmachines.util.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BucketSwapHelper {

	/************************************************************************
	 * swapBuckets()
	 * 
	 * @param bucketIn
	 * 
	 * @param player
	 * 
	 * Changes the item the player is holding to bucketIn
	 ************************************************************************/
	public static void swapBuckets(ItemStack bucketIn, PlayerEntity player) {

		player.setItemStackToSlot(EquipmentSlotType.MAINHAND, bucketIn);
	}

	/*****************************************
	 * getEmptyBucket()
	 * 
	 * @return Items.BUCKET
	 ****************************************/
	public static ItemStack getEmptyBucket() {

		return new ItemStack(Items.BUCKET);
	}

	/****************************************************
	 * emptyBucket()
	 * 
	 * @param player
	 * 
	 * Changes the held item to an empty bucket
	 ***************************************************/
	public static void emptyBucket(PlayerEntity player) {

		swapBuckets(getEmptyBucket(), player);
	}

	/*****************************************
	 * getWaterBucket()
	 * 
	 * @return Items.WATER_BUCKET
	 ****************************************/
	public static ItemStack getWaterBucket() {

		return new ItemStack(Items.WATER_BUCKET);
	}

	/****************************************************
	 * fillWaterBucket()
	 * 
	 * @param player
	 * 
	 * Changes the held item to a water bucket
	 ***************************************************/
	public static void fillWaterBucket(PlayerEntity player) {

		swapBuckets(getWaterBucket(), player);
	}

	/*****************************************
	 * getLavaBucket()
	 * 
	 * @return Items.LAVA_BUCKET
	 ****************************************/
	public static ItemStack getLavaBucket() {

		return new ItemStack(Items.LAVA_BUCKET);
	}

	/****************************************************
	 * fillLavaBucket
	 * 
	 * @param player
	 * 
	 * Changes the held item to a lava bucket
	 ***************************************************/
	public static void fillLavaBucket(PlayerEntity player) {

		swapBuckets(getLavaBucket(), player);
	}
	
}
