package com.sirscruffybeard.scruffysmachines.tileentity;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sirscruffybeard.scruffysmachines.container.BrickFurnaceContainer;
import com.sirscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BrickFurnaceTileEntity extends AbstractFurnaceTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity{

	protected int numPlayerUsing;

	private IItemHandlerModifiable items = createHandler();;

	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

	private int burnTime = 0;
	private int recipesUsed = 0;
	private int cookTime = 0;
	private int cookTimeTotal = 0;

	public static final int INVENTORY_SIZE = 3;
	private NonNullList<ItemStack> furnaceContents = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};

	protected final IIntArray furnaceData = new IIntArray() {
		public int get(int index) {
			switch(index) {
			case 0:
				return BrickFurnaceTileEntity.this.burnTime;
			case 1:
				return BrickFurnaceTileEntity.this.recipesUsed;
			case 2:
				return BrickFurnaceTileEntity.this.cookTime;
			case 3:
				return BrickFurnaceTileEntity.this.cookTimeTotal;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch(index) {
			case 0:
				BrickFurnaceTileEntity.this.burnTime = value;
				break;
			case 1:
				BrickFurnaceTileEntity.this.recipesUsed = value;
				break;
			case 2:
				BrickFurnaceTileEntity.this.cookTime = value;
				break;
			case 3:
				BrickFurnaceTileEntity.this.cookTimeTotal = value;
			}

		}

		public int size() {
			return 4;
		}
	};    

	protected final IRecipeType<? extends AbstractCookingRecipe> recipeType;

	/**********************************************************************************************************************
	 * Constructor
	 **********************************************************************************************************************/
	public BrickFurnaceTileEntity(TileEntityType<?> tileTypeIn, IRecipeType<? extends AbstractCookingRecipe> recipeTypeIn) {
		super(tileTypeIn, recipeTypeIn);
		this.recipeType = recipeTypeIn;
	}

	//Forge - get burn times by calling ForgeHooks#getBurnTime(ItemStack)

	public BrickFurnaceTileEntity() {

		this(ModTileEntityTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.brick_furnace");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new BrickFurnaceContainer(id, player, this, this.furnaceData);
	}

	public NonNullList<ItemStack> getItems() {

		return this.furnaceContents;
	}


	@Override
	public int getSizeInventory() {

		return INVENTORY_SIZE;
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {

		if(id == 1) {

			this.numPlayerUsing = type;
			return true;
		}

		else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void openInventory(PlayerEntity player) {

		if(!player.isSpectator()) {

			if(this.numPlayerUsing < 0) {

				this.numPlayerUsing = 0;
			}

			++this.numPlayerUsing;

			this.onOpenOrClosed();
		}
	}

	@Override
	public void closeInventory(PlayerEntity player) {

		if(!player.isSpectator()) {

			--this.numPlayerUsing;

			this.onOpenOrClosed();
		}
	}

	protected void onOpenOrClosed() {

		Block block = this.getBlockState().getBlock();

		if(block instanceof LeatherChestBlock) {

			this.world.addBlockEvent(this.pos, block, 1, this.numPlayerUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}

	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {

		BlockState blockState = reader.getBlockState(pos);

		if(blockState.hasTileEntity()) {

			TileEntity tileEntity = reader.getTileEntity(pos);

			if(tileEntity instanceof BrickFurnaceTileEntity) {

				return ((BrickFurnaceTileEntity)tileEntity).numPlayerUsing;

			}//tileEntity instanceof BrickFurnaceTileEntity
		}//blockState.hasTileEntity()

		return 0;
	}

	@Override
	public void updateContainingBlockInfo() {

		super.updateContainingBlockInfo();

		if(this.itemHandler != null) {
			this.itemHandler.invalidate();
			this.itemHandler = null;
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {

		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	private IItemHandlerModifiable createHandler() {

		return new InvWrapper(this);
	}

	@Override
	public void remove() {

		super.remove();

		if(itemHandler != null) {

			itemHandler.invalidate();
		}
	}

	public void clear() {
		this.furnaceContents.clear();
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	public boolean isUsableByPlayer(PlayerEntity player) {
		if (this.world.getTileEntity(this.pos) != this) {
			return false;
		} else {
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 */
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index == 2) {
			return false;
		} else if (index != 1) {
			return true;
		} else {
			ItemStack itemstack = this.furnaceContents.get(1);
			return isFuel(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
		}
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 */
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (direction == Direction.DOWN && index == 1) {
			Item item = stack.getItem();
			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = this.furnaceContents.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.furnaceContents.set(index, stack);
		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.cookTimeTotal = this.getCookTime();
			this.cookTime = 0;
			this.markDirty();
		}

	}

	/**
	 * Returns the stack in the given slot.
	 */
	public ItemStack getStackInSlot(int index) {
		return this.furnaceContents.get(index);
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.furnaceContents, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.furnaceContents, index);
	}

	public void onCrafting(PlayerEntity player) {
	}

	@Nullable
	public IRecipe<?> getRecipeUsed() {
		return null;
	}

	public boolean isEmpty() {
		for(ItemStack itemstack : this.furnaceContents) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side.
	 */
	public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return SLOTS_DOWN;
		} else {
			return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	public static boolean isFuel(ItemStack fuel) {
		return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel) > 0;
	}

	@SuppressWarnings("unchecked")
	protected int getCookTime() {
		return this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>)this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
	}

	protected int getBurnTime(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return 0;
		} else {
			Item item = itemStack.getItem();
			return net.minecraftforge.common.ForgeHooks.getBurnTime(itemStack);
		}
	}

	protected boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
		if (!this.furnaceContents.get(0).isEmpty() && recipeIn != null) {
			ItemStack itemstack = recipeIn.getRecipeOutput();
			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.furnaceContents.get(2);
				if (itemstack1.isEmpty()) {
					return true;
				} else if (!itemstack1.isItemEqual(itemstack)) {
					return false;
				} else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	private void func_214007_c(@Nullable IRecipe<?> recipe) {
		if (recipe != null && this.canSmelt(recipe)) {
			ItemStack itemstack = this.furnaceContents.get(0);
			ItemStack itemstack1 = recipe.getRecipeOutput();
			ItemStack itemstack2 = this.furnaceContents.get(2);
			if (itemstack2.isEmpty()) {
				this.furnaceContents.set(2, itemstack1.copy());
			} else if (itemstack2.getItem() == itemstack1.getItem()) {
				itemstack2.grow(itemstack1.getCount());
			}

			if (!this.world.isRemote) {
				this.setRecipeUsed(recipe);
			}

			if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.furnaceContents.get(1).isEmpty() && this.furnaceContents.get(1).getItem() == Items.BUCKET) {
				this.furnaceContents.set(1, new ItemStack(Items.WATER_BUCKET));
			}

			itemstack.shrink(1);
		}
	}

	public void tick() {
		boolean flag = this.isBurning();
		boolean flag1 = false;
		if (this.isBurning()) {
			--this.burnTime;
		}

		if (!this.world.isRemote) {
			ItemStack itemstack = this.furnaceContents.get(1);
			if (this.isBurning() || !itemstack.isEmpty() && !this.furnaceContents.get(0).isEmpty()) {
				IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>)this.recipeType, this, this.world).orElse(null);
				if (!this.isBurning() && this.canSmelt(irecipe)) {
					this.burnTime = this.getBurnTime(itemstack);
					this.recipesUsed = this.burnTime;
					if (this.isBurning()) {
						flag1 = true;
						if (itemstack.hasContainerItem())
							this.furnaceContents.set(1, itemstack.getContainerItem());
						else
							if (!itemstack.isEmpty()) {
								Item item = itemstack.getItem();
								itemstack.shrink(1);
								if (itemstack.isEmpty()) {
									this.furnaceContents.set(1, itemstack.getContainerItem());
								}
							}
					}
				}

				if (this.isBurning() && this.canSmelt(irecipe)) {
					++this.cookTime;
					if (this.cookTime == this.cookTimeTotal) {
						this.cookTime = 0;
						this.cookTimeTotal = this.getCookTime();
						this.func_214007_c(irecipe);
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			} else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
			}

			if (flag != this.isBurning()) {
				flag1 = true;
				this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BrickFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
			}
		}

		if (flag1) {
			this.markDirty();
		}

	}

	private boolean isBurning() {
		return this.burnTime > 0;
	}

	public void fillStackedContents(RecipeItemHelper helper) {

		for(ItemStack itemstack : this.furnaceContents) { helper.accountStack(itemstack); }
	}

	private static void func_214003_a(PlayerEntity player, int p_214003_1_, float p_214003_2_) {
		if (p_214003_2_ == 0.0F) {
			p_214003_1_ = 0;
		} else if (p_214003_2_ < 1.0F) {
			int i = MathHelper.floor((float)p_214003_1_ * p_214003_2_);
			if (i < MathHelper.ceil((float)p_214003_1_ * p_214003_2_) && Math.random() < (double)((float)p_214003_1_ * p_214003_2_ - (float)i)) {
				++i;
			}

			p_214003_1_ = i;
		}

		while(p_214003_1_ > 0) {
			int j = ExperienceOrbEntity.getXPSplit(p_214003_1_);
			p_214003_1_ -= j;
			player.world.addEntity(new ExperienceOrbEntity(player.world, player.getPosX(), player.getPosY() + 0.5D, player.getPosZ() + 0.5D, j));
		}

	}
	


}