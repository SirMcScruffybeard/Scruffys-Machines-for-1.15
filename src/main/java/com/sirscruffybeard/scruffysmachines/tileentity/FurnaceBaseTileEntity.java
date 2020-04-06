package com.sirscruffybeard.scruffysmachines.tileentity;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sirscruffybeard.scruffysmachines.container.LeatherChestContainer;
import com.sirscruffybeard.scruffysmachines.objects.blocks.BrickFurnaceBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.FurnaceBaseBlock;
import com.sirscruffybeard.scruffysmachines.objects.blocks.LeatherChestBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class FurnaceBaseTileEntity extends TileEntity implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator, INamedContainerProvider, IInventory{

	public final int INVENTORY_SIZE = 3;
	private NonNullList<ItemStack> contents = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);

	private static final int[] SLOTS_UP = new int[]{0};
	private static final int[] SLOTS_DOWN = new int[]{2, 1};
	private static final int[] SLOTS_HORIZONTAL = new int[]{1};

	private int timer; 
	private int totalBurnTime; //How long the furnace will burn in ticks
	private int currentFuelBurnTime;//How long a new copy of the fuel item will keep the furnace burning
	private int cookTime;
	private int totalCookTime;
	
	private int modifier = 1;
	
	protected int numPlayerUsing;

	private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();

	protected IRecipeType<? extends AbstractCookingRecipe> recipeType;

	private IItemHandlerModifiable items = createHandler();
	private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

	public FurnaceBaseTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);

		this.recipeType = IRecipeType.SMELTING;

	}

	/************************************************************
	 * getCookTime(int)
	 * 
	 * @param int modifier: amount to adjust cook time 
	 * 
	 * @return Cook time
	 * 
	 * 
	 ************************************************************/
	protected int getCookTime(int modifier) {
		ItemStack stack = getStackInSlot(3);
		if (!stack.isEmpty()) {
			int newTime = getDefaultCookTime()/modifier;
			return newTime;
		}
		return getDefaultCookTime();
	}

	/************************************************************
	 * getDefaultCookTime
	 * 
	 * @return Default Minecraft furnace cook time
	 ************************************************************/
	public int getDefaultCookTime() {
		return 200;
	}

	public void setModifier(int modify) {
		
		this.modifier = modify;
	}
	
	public int getModifier() {

		return modifier;
	}


	public Map<ResourceLocation, Integer> getRecipeUseCounts() {
		return this.recipeUseCounts;
	}

	public NonNullList<ItemStack> getItems() { return this.contents; }
	
	public boolean isEmpty() { return this.contents.isEmpty(); }

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		
		return null;
	}


	public final IIntArray fields = new IIntArray() {
		public int get(int index) {
			switch (index) {
			case 0:
				return FurnaceBaseTileEntity.this.totalBurnTime;
			case 1:
				return FurnaceBaseTileEntity.this.currentFuelBurnTime;
			case 2:
				return FurnaceBaseTileEntity.this.cookTime;
			case 3:
				return FurnaceBaseTileEntity.this.totalCookTime;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch (index) {
			case 0:
				FurnaceBaseTileEntity.this.totalBurnTime = value;
				break;
			case 1:
				FurnaceBaseTileEntity.this.currentFuelBurnTime = value;
				break;
			case 2:
				FurnaceBaseTileEntity.this.cookTime = value;
				break;
			case 3:
				FurnaceBaseTileEntity.this.totalCookTime = value;
			}

		}

		public int size() {
			return 4;
		}
	};

	@Override
	public void tick() {
		boolean flag1 = false;
		if (this.isBurning()) {
			--this.totalBurnTime;
		}

		if (!this.world.isRemote) { //if client
			timer++;
			if (this.totalCookTime != this.getCookTime(this.getModifier())) { this.totalCookTime = this.getCookTime(this.getModifier()); }

			if (!this.getStackInSlot(3).isEmpty()) {
				if (this.recipeType != IRecipeType.BLASTING) { this.recipeType = IRecipeType.BLASTING; }
				else if (this.recipeType != IRecipeType.SMOKING) { this.recipeType = IRecipeType.SMOKING; }
				else if (this.recipeType != IRecipeType.SMELTING) { this.recipeType = IRecipeType.SMELTING; }
			}

			ItemStack itemstack = this.contents.get(1);
			if (this.isBurning() || !itemstack.isEmpty() && !this.contents.get(0).isEmpty()) {
				IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.world).orElse(null);

				if (!this.isBurning() && this.canSmelt(irecipe)) {
					if (itemstack.hasTag()) {
						int x = itemstack.getTag().getInt("X");
						int y = itemstack.getTag().getInt("Y");
						int z = itemstack.getTag().getInt("Z");
						TileEntity te = world.getTileEntity(new BlockPos(x, y, z));	
						this.totalBurnTime = getBurnTime(itemstack) * this.getCookTime(this.getModifier()) / 200;
						this.currentFuelBurnTime = this.totalBurnTime;
					}
					if (this.isBurning()) {
						flag1 = true;
						if (itemstack.hasContainerItem()) {
							this.contents.set(1, itemstack.getContainerItem());
						} else if (!itemstack.isEmpty()) {
							Item item = itemstack.getItem();
							itemstack.shrink(1);
							if (itemstack.isEmpty()) {
								Item item1 = item.getContainerItem();
								this.contents.set(1, item1 == null ? ItemStack.EMPTY : new ItemStack(item1));
							}
						}
					}
				}


				if (this.isBurning() && this.canSmelt(irecipe)) {
					++this.cookTime;
					if (this.cookTime >= this.totalCookTime) {
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime(getModifier());
						this.smeltItem(irecipe);
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			} else if (!this.isBurning() && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
			}
			if (timer % 24 == 0) {
				BlockState state = world.getBlockState(pos);
				if (state.get(BlockStateProperties.LIT) != this.totalBurnTime > 0) {
					world.setBlockState(pos, state.with(BlockStateProperties.LIT, this.totalBurnTime > 0), 3);
				}
			}
		}

		if (flag1) {
			this.markDirty();
		}
	}

	public boolean isBurning() {
		return this.totalBurnTime > 0;
	}
	
    private void smeltItem(@Nullable IRecipe recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.contents.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.contents.get(2);
            if (itemstack2.isEmpty()) {
                this.contents.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                this.canUseRecipe(this.world, (ServerPlayerEntity) null, recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.contents.get(1).isEmpty() && this.contents.get(1).getItem() == Items.BUCKET) {
                this.contents.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }

	private boolean canSmelt(@Nullable IRecipe recipe) {
		if (!this.contents.get(0).isEmpty() && recipe != null) {
			ItemStack itemstack = recipe.getRecipeOutput();
			if (itemstack.isEmpty()) {
				return false;
			} else {
				ItemStack itemstack1 = this.contents.get(2);
				if (itemstack1.isEmpty()) {
					return true;
				} else if (!itemstack1.isItemEqual(itemstack)) {
					return false;
				} else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() < itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}

	protected static int getBurnTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			int ret = stack.getBurnTime();
			return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack, ret == -1 ? ForgeHooks.getBurnTime(stack) : ret);
		}
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getBurnTime(stack) > 0;
	}




	@Override
	public void read(CompoundNBT tag) {
		ItemStackHelper.loadAllItems(tag, this.contents);
		this.totalBurnTime = tag.getInt("BurnTime");
		this.cookTime = tag.getInt("CookTime");
		this.totalCookTime = tag.getInt("CookTimeTotal");
		this.timer = 0;
		this.currentFuelBurnTime = getBurnTime(this.contents.get(1));
		int i = tag.getShort("RecipesUsedSize");
		for (int j = 0; j < i; ++j) {
			ResourceLocation resourcelocation = new ResourceLocation(tag.getString("RecipeLocation" + j));
			int k = tag.getInt("RecipeAmount" + j);
			this.recipeUseCounts.put(resourcelocation, k);
		}

		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		ItemStackHelper.saveAllItems(tag, this.contents);
		tag.putInt("BurnTime", this.totalBurnTime);
		tag.putInt("CookTime", this.cookTime);
		tag.putInt("CookTimeTotal", this.totalCookTime);
		tag.putShort("RecipesUsedSize", (short) this.recipeUseCounts.size());
		int i = 0;

		for (Map.Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {
			tag.putString("RecipeLocation" + i, entry.getKey().toString());
			tag.putInt("RecipeAmount" + i, entry.getValue());
			++i;
		}

		return super.write(tag);
	}

	private IItemHandlerModifiable createHandler() {

		return new InvWrapper(this);
	}

	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {

		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandler.cast();
		}

		return super.getCapability(cap, side);
	}

	@Override
	public void onCrafting(PlayerEntity player) { }

	@Override
	public void fillStackedContents(RecipeItemHelper helper) {
		for (ItemStack itemstack : this.contents) {
			helper.accountStack(itemstack);
		}
	}

	@Override
	public void setRecipeUsed(IRecipe recipe) {
		if (this.recipeUseCounts.containsKey(recipe.getId())) {
			this.recipeUseCounts.put(recipe.getId(), this.recipeUseCounts.get(recipe.getId()) + 1);
		} else {
			this.recipeUseCounts.put(recipe.getId(), 1);
		}

	}
	
	@Nullable
    public IRecipe getRecipeUsed() { return null; }
	
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        return true;
    }
    
   
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }
    
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.contents.get(1);
            return FurnaceTileEntity.isFuel(stack) || FurnaceFuelSlot.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }
    
    public void func_213995_d(PlayerEntity player) {
        List<IRecipe<?>> list = Lists.newArrayList();

        for (Map.Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {
            player.world.getRecipeManager().getRecipe(entry.getKey()).ifPresent((p_213993_3_) -> {
                list.add(p_213993_3_);
                func_214003_a(player, entry.getValue(), ((AbstractCookingRecipe) p_213993_3_).getExperience());
            });
        }

        player.unlockRecipes(list);
        this.recipeUseCounts.clear();
    }

    private static void func_214003_a(PlayerEntity player, int p_214003_1_, float p_214003_2_) {
        if (p_214003_2_ == 0.0F) {
            p_214003_1_ = 0;
        } else if (p_214003_2_ < 1.0F) {
            int i = MathHelper.floor((float) p_214003_1_ * p_214003_2_);
            if (i < MathHelper.ceil((float) p_214003_1_ * p_214003_2_) && Math.random() < (double) ((float) p_214003_1_ * p_214003_2_ - (float) i)) {
                ++i;
            }

            p_214003_1_ = i;
        }

        while (p_214003_1_ > 0) {
            int j = ExperienceOrbEntity.getXPSplit(p_214003_1_);
            p_214003_1_ -= j;
            player.world.addEntity(new ExperienceOrbEntity(player.world, player.prevPosX, player.prevPosY + 0.5D, player.prevPosZ + 0.5D, j));
        }

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
	public void remove() {
		
		super.remove();
		
		if(itemHandler != null) {
			
			itemHandler.invalidate();
		}
	}
    
	public static void swapContents(LeatherChestTileEntity te, LeatherChestTileEntity otherTe) {
		
		NonNullList<ItemStack> list = te.getItems();
		te.setItems(otherTe.getItems());
		otherTe.setItems(list);
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
		
		if(block instanceof FurnaceBaseBlock) {
			
			this.world.addBlockEvent(this.pos, block, 1, this.numPlayerUsing);
			this.world.notifyNeighborsOfStateChange(this.pos, block);
		}
	}
	
	public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
		
		BlockState blockState = reader.getBlockState(pos);
		
		if(blockState.hasTileEntity()) {
			
			TileEntity tileEntity = reader.getTileEntity(pos);
			
			if(tileEntity instanceof LeatherChestTileEntity) {
				
				return ((FurnaceBaseTileEntity)tileEntity).numPlayerUsing;
				
			}//tileEntity instanceof LeatherChestTileEntity
		}//blockState.hasTileEntity()
		
		return 0;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) { return this.contents.get(index); }
	
	@Override
	public void clear() { this.contents.clear(); }
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) { contents.add(index, stack); }
	
	protected Container createMenu(int id, PlayerInventory player) { return null; }
}//FurnaceBaseTileEntity
