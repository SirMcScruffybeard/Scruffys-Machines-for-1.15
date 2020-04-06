package com.sirscruffybeard.scruffysmachines.tileentity;

import com.sirscruffybeard.scruffysmachines.container.BrickFurnaceContainer;
import com.sirscruffybeard.scruffysmachines.container.LeatherChestContainer;
import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BrickFurnaceTileEntity extends FurnaceBaseTileEntity{

	
	
	public BrickFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		
		super.setModifier(2);

	}

		public BrickFurnaceTileEntity() {

		this(ModTileEntityTypes.BRICK_FURNACE.get());
	}
	
		
		@Override
		protected Container createMenu(int id, PlayerInventory player) {

			return new BrickFurnaceContainer(id, player, this);
		}

		@Override
		public int getSizeInventory() { return super.INVENTORY_SIZE; }
		
		@Override
		public NonNullList<ItemStack> getItems() {return super.getItems(); }

		@Override
		public boolean isEmpty() { return super.isEmpty(); }

		@Override
		public ItemStack decrStackSize(int index, int count) {
			ItemStack stack = this.getItems().get(index);
			
			stack.shrink(count);
			
			return stack;
		}

		@Override
		public ItemStack removeStackFromSlot(int index) {
			
			ItemStack stack = null;
			
			if(index >= 0 && !this.isEmpty()) {
				
				stack = this.getItems().get(index);
				
				this.getItems().remove(index);
			}
			
			return stack;
		}

		@Override
		public void setInventorySlotContents(int index, ItemStack stack) {super.setInventorySlotContents(index, stack); }

		@Override
		public boolean isUsableByPlayer(PlayerEntity player) { return true; }

		@Override
		public void clear() { super.clear(); }

		@Override
		public ItemStack getStackInSlot(int index) { return super.getStackInSlot(index); }

		@Override
		public ITextComponent getDisplayName() { return new TranslationTextComponent("container." + BrickFurnaceBlock.ID); }




}
