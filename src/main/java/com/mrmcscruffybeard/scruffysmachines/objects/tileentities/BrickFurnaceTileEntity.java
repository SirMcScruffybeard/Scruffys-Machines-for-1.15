package com.mrmcscruffybeard.scruffysmachines.objects.tileentities;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;
import com.mrmcscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.mrmcscruffybeard.scruffysmachines.objects.containers.BrickFurnaceContainer;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BrickFurnaceTileEntity extends LockableTileEntity implements INamedContainerProvider, ITickableTileEntity, ISidedInventory{

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};

	public static final String ID = BrickFurnaceBlock.ID;

	public static final int INVENTORY_SIZE = 3;

	private NonNullList<ItemStack> contents = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

	//Index's for contents
	private int rawMatIndex = 0;
	private int fuelIndex =  1;
	private int outputIndex = 2;

	private int burnTime;
	private int recipesUsed;
	private int cookTime;
	private int cookTimeTotal;

	protected final IRecipeType<? extends AbstractCookingRecipe> recipeType;

	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

	net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
			net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

	private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();

	public BrickFurnaceTileEntity(TileEntityType<?> tileEntityTypeIn, IRecipeType<? extends AbstractCookingRecipe> typeIn) {
		super(tileEntityTypeIn);

		this.recipeType = typeIn;

	}

	public BrickFurnaceTileEntity() {
		this(ModTileEntityTypes.BRICK_FURNACE.get(), IRecipeType.SMELTING);
	}


	public int getBurnTime() { return this.burnTime;}

	public void setBurnTime(int time) {this.burnTime = time;}

	public int getRecipesUsed() {return this.recipesUsed;}

	public void setRecipesUsed(int recipies) {this.recipesUsed = recipies;}

	public int getCookTimeAmount() {return this.cookTime;}

	public void setCookTimeAmount(int time) {this.cookTime = time;}

	public int getCookTimeTotal() {return this.cookTimeTotal;}

	public void setCookTimeTotal(int time) {this.cookTimeTotal = time;}


	public void tick() {

		World world = this.getWorld();

		boolean flag = this.isBurning();

		boolean flag1 = false;

		if(this.isBurning()) { //Check if fuel is burning

			-- this.burnTime;
		}//if burning	

		if(this.world.isRemote) { //if not client

			ItemStack fuelStack = this.contents.get(fuelIndex); //get second item stack in contents

			if(this.isBurning() || !fuelStack.isEmpty() && !this.contents.get(rawMatIndex).isEmpty()) {

				IRecipe<?> recipe = world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, world).orElse(null);

				if(!this.isBurning() && this.canSmelt(recipe)) { // if isn't burning and can smelt

					this.burnTime = this.getBurnTime(fuelStack);

					this.recipesUsed = this.burnTime;

					if (this.isBurning()) {

						flag1 = true;

						if(fuelStack.hasContainerItem()) { this.contents.set(fuelIndex, fuelStack.getContainerItem());} 

						else if(!fuelStack.isEmpty()) {

							Item fuelItem = fuelStack.getItem();

							fuelStack.shrink(1);

							if(fuelStack.isEmpty()) { 

								this.contents.set(fuelIndex, fuelStack.getContainerItem()); 

							}//if fuelStack is empty
						}//if fuelStack is not empty

					}//if burning

				}//if isn't burning and can smelt

				if(this.isBurning() && this.canSmelt(recipe)) {

					++this.cookTime;

					if(this.cookTime == this.cookTimeTotal) {

						this.cookTime = 0;

						this.cookTimeTotal = this.getCookTime();

						this.processMaterials(recipe);

						flag1 = true;
					}
				}//if is burning and can smelt

				else {cookTime = 0;}

			}//if burning or stack and first stack in contents is not empty

			else if (!this.isBurning() && this.cookTime > 0) {

				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);

			}//else if not burning and cook time is greater than 0

			if(flag != this.isBurning()) {

				flag1 = true;

				this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BrickFurnaceBlock.LIT, this.isBurning()), 3);

			}//if flag doesn't match isBurning

		}//if world is not remote

		if(flag1) { this.markDirty(); }  

	}//tick


	public boolean isBurning() { return this.burnTime > 0; }	

	public boolean canSmelt(@Nullable IRecipe<?> recipeIn) {

		if(!this.contents.get(rawMatIndex).isEmpty() && recipeIn != null) { //Make sure there is a raw material and recipe

			ItemStack result = recipeIn.getRecipeOutput();

			if(result.isEmpty()) { return false; }

			else { //Result not empty

				ItemStack finishedStack = this.contents.get(outputIndex); //Item stack in output slot

				if(finishedStack.isEmpty()) { return true; } //If output is empty

				else if (!finishedStack.isItemEqual(result)) { return false; } //If output is not equal to result

				//else if (finishedStack.getCount() + result.getCount() <= this.getInventoryStackLimit() && finishedStack.getCount() + result.getCount() <= finishedStack.getMaxStackSize()) { 
				else if(stacksFitLimit(finishedStack, result, this.getInventoryStackLimit()) && stacksFitLimit(finishedStack, result, finishedStack.getMaxStackSize())){
					return true; 

				}//If finished stack + result is less than or equal to inventory stack limit and finished + result is less than or equal to finished max stack size

				else { return stacksFitLimit(finishedStack, result, result.getMaxStackSize());} 
			}

		}//if contents and recipe

		else { return false; } 

	}//canSmelt

	/*******************************************************************************
	 * stacksFitLimit()
	 * @param ItemStack stack1
	 * @param ItemStack stack2
	 * @param int limit
	 * @return boolean result
	 *******************************************************************************/
	private boolean stacksFitLimit(ItemStack stack1, ItemStack stack2, int limit)  {

		return stack1.getCount() + stack2.getCount() <= limit;
	}

	private int getBurnTime(ItemStack fuelStack) {

		if (fuelStack.isEmpty()) {return 0;}

		else {

			return net.minecraftforge.common.ForgeHooks.getBurnTime(fuelStack);
		}
	}

	private int getCookTime() {

		return this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>)this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);

	}

	private void processMaterials(@Nullable IRecipe<?> recipeIn){

		if(recipeIn != null && this.canSmelt(recipeIn)) { //if recipe and can smelt

			ItemStack matStack = this.contents.get(rawMatIndex);
			ItemStack output = recipeIn.getRecipeOutput();
			ItemStack result = this.contents.get(outputIndex);

			if(result.isEmpty()) { this.contents.set(outputIndex, output.copy()); }

			else if(result.getItem() == output.getItem()) { result.grow(output.getCount()); }

			if (!this.world.isRemote) { this.setRecipeUsed(recipeIn); }

			if (matStack.getItem() == Blocks.WET_SPONGE.asItem() && !this.contents.get(fuelIndex).isEmpty() && this.contents.get(fuelIndex).getItem() == Items.BUCKET) {

				this.contents.set(fuelIndex, new ItemStack(Items.WATER_BUCKET));
			}

			matStack.shrink(rawMatIndex);

		}//recipe and can smelt
	}

	public static boolean isFuel(ItemStack stack) { return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0; }


	/***************************************************************************************************
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 ***************************************************************************************************/
	@Override
	public ItemStack decrStackSize(int index, int count) {

		return ItemStackHelper.getAndSplit(this.contents, index, count);
	}


	/******************************************************
	 * removeStackFromSlot()
	 * 
	 *@param int index
	 *
	 *@return ItemStack
	 * 
	 * Removes a stack from the given slot and returns it.
	 *******************************************************/
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.contents, index);
	}

	/*******************************************
	 * getStackInSlot()
	 * 
	 * @param int index
	 * 
	 * @return ItemStack
	 * 
	 * Returns the stack in the given slot.
	 *******************************************/
	@Override
	public ItemStack getStackInSlot(int index) {
		return this.contents.get(index);
	}

	/*******************************************************************************************************
	 * setInventorySlotContents()
	 * 
	 * @param int index
	 * 
	 * @param ItemStack stack
	 * 
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 *******************************************************************************************************/
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		ItemStack stackSpot = this.contents.get(index);

		boolean flag = !stackSpot.isEmpty() && stack.isItemEqual(stackSpot) && ItemStack.areItemStackTagsEqual(stackSpot, stack);

		this.contents.set(index, stack);

		if(stack.getCount() > this.getInventoryStackLimit()) {

			stack.setCount(this.getInventoryStackLimit());
		}

		if(index == 0 && !flag) {

			this.cookTimeTotal = this.getCookTime();

			this.cookTime = 0;

			this.markDirty();
		}

	}

	/******************************************************************************
	 * isUsableByPlayer()
	 * 
	 * @param PlayerEntity
	 * 
	 * @return boolean
	 * 
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 ******************************************************************************/
	public boolean isUsableByPlayer(PlayerEntity player) {

		if (this.world.getTileEntity(this.pos) != this) {

			return false;
		} 

		else {

			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}


	/*********************************************
	 * clear()
	 *********************************************/
	@Override
	public void clear() { contents.clear();	}

	/*********************************************************
	 * fillStackedContents()
	 * 
	 * @param RecipeItemHelper helper
	 *********************************************************/
	public void fillStackedContents(RecipeItemHelper helper) {
		for(ItemStack itemstack : this.contents) {
			helper.accountStack(itemstack);
		}

	}

	/******************************************
	 * setRecipeUsed()
	 * 
	 * @param IRecipe recipe
	 ******************************************/
	public void setRecipeUsed(IRecipe recipe) {
		if (this.recipeUseCounts.containsKey(recipe.getId())) {
			this.recipeUseCounts.put(recipe.getId(), this.recipeUseCounts.get(recipe.getId()) + 1);
		} else {
			this.recipeUseCounts.put(recipe.getId(), 1);
		}

	}

	@Nullable
	public IRecipe<?> getRecipeUsed() {	return null; }

	@Override
	protected ITextComponent getDefaultName() {

		return new TranslationTextComponent("container." + ID);
	}

	@Override
	protected Container createMenu(int windowId, PlayerInventory playerInventoryIn) {

		return new BrickFurnaceContainer(windowId, playerInventoryIn, this);
	}

	public NonNullList<ItemStack> getItemList() { return this.contents; }

	/*******************************
	 * remove()
	 * 
	 * invalidates a tile entity
	 *******************************/
	@Override
	public void remove() {

		super.remove();

		if(itemHandler != null) {

			itemHandler.invalidate();
		}
	}

	/***********************************************
	 * createHandler()
	 * 
	 * @return IItemHandlerModifiable
	 ***********************************************/
	private IItemHandlerModifiable createHandler() {

		return new InvWrapper(this);
	}

	public void read(CompoundNBT compound) {

		super.read(compound);

		this.contents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

		ItemStackHelper.loadAllItems(compound, this.contents);

		this.burnTime = compound.getInt("BurnTime");
		this.cookTime = compound.getInt("CookTime");
		this.cookTimeTotal = compound.getInt("CookTimeTotal");
		this.recipesUsed = this.getBurnTime(this.contents.get(this.fuelIndex));

		int i = compound.getShort("RecipesUsedSize");

		for (int n = 0; n < i; ++n) {

			ResourceLocation resourceLocation = new ResourceLocation(compound.getString("RecipeLocation" + n));

			this.recipeUseCounts.put(resourceLocation, compound.getInt("RecipeAmount" + n));
		}
	}//read


	public CompoundNBT write(CompoundNBT compound) {

		super.write(compound);

		compound.putInt("BurnTime", this.burnTime);
		compound.putInt("CookTime", this.cookTime);
		compound.putInt("CookTimeTotal", this.cookTimeTotal);

		ItemStackHelper.saveAllItems(compound, this.contents);

		compound.putShort("RecipesUsedSize", (short)this.recipeUseCounts.size());

		int i = 0;

		for(Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {

			compound.putString("RecipeLocation" + i, entry.getKey().toString());

			compound.putInt("RecipeAmount" + i, entry.getValue());

			++i;
		}

		return compound;
	}


	public void processRecipe(PlayerEntity player) {

		List<IRecipe<?>> list = Lists.newArrayList();

		for(Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {

			player.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((p_213993_3_) -> {

				list.add(p_213993_3_);

				giveExperince(player, entry.getValue(), ((AbstractCookingRecipe)p_213993_3_).getExperience());
			});
		}

		player.unlockRecipes(list);
		this.recipeUseCounts.clear();
	}


	private static void giveExperince(PlayerEntity player, int recipeValue, float recipeExp) {

		if(recipeExp == 0.0f) {

			recipeValue = 0;

		}//if exp = 0;

		else if(recipeExp < 1.0f) {

			int i = MathHelper.floor((float)recipeValue * recipeExp);

			if(i < MathHelper.ceil((float)recipeValue * recipeExp) && Math.random() < (double)(float)recipeValue * recipeExp - (float)i) {

				++i;
			}

			recipeValue = i;
		}

		while(recipeValue > 0) {

			int j = ExperienceOrbEntity.getXPSplit(recipeValue);

			recipeValue -= j;

			player.world.addEntity(new ExperienceOrbEntity(player.world, player.getPosX(), player.getPosY() + .05D, player.getPosZ() + 0.5D, j));
		}


	}

	/*************************************************************
	 * getItemHandler()
	 * 
	 * @return LazyOptional<IItemHandlerModifable>
	 *************************************************************/
	public LazyOptional<IItemHandlerModifiable> getItemHandler() {

		return this.itemHandler;
	}

	/*****************************************************************************************************************
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 *****************************************************************************************************************/
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		if(index == this.outputIndex) {

			return false;
		}

		else if(index == fuelIndex) {

			return true;
		}

		else {

			ItemStack itemStack = this.contents.get(fuelIndex);
			return isFuel(stack) || stack.getItem() == Items.BUCKET && itemStack.getItem() != Items.BUCKET;
		}
	}


	/**********************************************************************************************
	 * getCapability()
	 * 
	 **********************************************************************************************/
	@Override
	public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing == Direction.UP)
				return handlers[0].cast();
			else if (facing == Direction.DOWN)
				return handlers[1].cast();
			else
				return handlers[2].cast();
		}
		return super.getCapability(capability, facing);
	}

	public void onCrafting(PlayerEntity player) {	}

	/************************************************************
	 * getSizeInventory()
	 * 
	 * @return The number of slots in the inventory.
	 ************************************************************/
	public int getSizeInventory() { return this.contents.size(); }


	public boolean isEmpty() {

		for(ItemStack stack : this.contents) {

			if(!stack.isEmpty()) {

				return false;
			}
		}

		return true;
	}

	public IItemHandlerModifiable getItems() {

		return this.items;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {

		if(side == Direction.DOWN) {
			return SLOTS_DOWN;
		}
		else {
			return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
		}
	}

	/*********************************************************************************************
	 * Returns true if automation can insert the given item in the given slot from the given side.
	 *********************************************************************************************/
	@Override
	public boolean canInsertItem(int index, ItemStack stackIn, Direction direction) {

		return this.isItemValidForSlot(index, stackIn);
	}

	/**********************************************************************************************
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 **********************************************************************************************/
	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {

		if(direction == Direction.DOWN && index == 1) {

			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {

				return false;
			}
		}

		return true;
	}

}//BrickFurnaceTileEntity
