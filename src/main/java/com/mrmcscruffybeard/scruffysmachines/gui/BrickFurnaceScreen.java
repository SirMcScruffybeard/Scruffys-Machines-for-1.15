package com.mrmcscruffybeard.scruffysmachines.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mrmcscruffybeard.scruffysmachines.ScruffysMachines;
import com.mrmcscruffybeard.scruffysmachines.objects.containers.BrickFurnaceContainer;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.client.gui.recipebook.IRecipeShownListener;
import net.minecraft.client.gui.recipebook.RecipeBookGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BrickFurnaceScreen extends ContainerScreen<BrickFurnaceContainer> implements IRecipeShownListener{

	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(ScruffysMachines.MOD_ID, "textures/gui/brick_furnace_screen.png");

	private static final ResourceLocation RECIPE_BUTTON = new ResourceLocation("textures/gui/recipe_button.png");

	private final AbstractRecipeBookGui RECIPE_BOOK_GUI = new FurnaceRecipeGui();

	private boolean doesFit;

	@OnlyIn(Dist.CLIENT)
	public BrickFurnaceScreen(BrickFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);

		this.guiLeft = 0;
		this.guiTop = 0;
		this.xSize = 176;
		this.ySize = 184;


	}

	public void init() {

		super.init();

		this.doesFit = this.width < 379;

		this.RECIPE_BOOK_GUI.init(this.width, this.height, this.minecraft, this.doesFit, this.container);

		this.guiLeft = this.RECIPE_BOOK_GUI.updateScreenPosition(this.doesFit, this.width, this.xSize);

		this.addButton((new ImageButton(this.guiLeft +20, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON, (RECIPE_BUTTON) -> {

			this.RECIPE_BOOK_GUI.initSearchBar(this.doesFit);

			this.RECIPE_BOOK_GUI.toggleVisibility();

			this.guiLeft = this.RECIPE_BOOK_GUI.updateScreenPosition(this.doesFit, this.width, this.xSize);

			((ImageButton)RECIPE_BUTTON).setPosition(this.guiLeft + 20, this.height / 2 - 49);
		})));

	}

	public void tick() {

		super.tick();

		this.RECIPE_BOOK_GUI.tick();
	}

	public void render(final int mouseX, final int mouseY, final float partialTicks) {

		this.renderBackground();

		if (this.RECIPE_BOOK_GUI.isVisible() && this.doesFit) {

			this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

			this.RECIPE_BOOK_GUI.render(mouseX, mouseY, partialTicks);

		}// if recipe book is visible and does fit

		else {

			this.RECIPE_BOOK_GUI.render(mouseX, mouseY, partialTicks);

			super.render(mouseX, mouseY, partialTicks);

			this.RECIPE_BOOK_GUI.renderGhostRecipe(this.guiLeft, this.guiTop, true, partialTicks);
		}

		this.renderHoveredToolTip(mouseX, mouseY);

		this.RECIPE_BOOK_GUI.renderTooltip(this.guiLeft, this.guiTop, mouseX, mouseY);

	}

	/************************************************************************************
	 * 
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 ************************************************************************************/
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

		String text = this.title.getFormattedText();

		this.font.drawString(text, (float)(this.xSize / 2 - this.font.getStringWidth(text) / 2), 6.0f, 4210752);

		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, (float)(this.ySize - 96 + 2), 4210752);
	}

	/********************************************************************
	 * Draws the background layer of this container (behind the items).
	 ********************************************************************/
	protected void drawGuiContainerBackgroundLayer(float partialticks, int mouseX, int mouseY) {

		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);

		this.minecraft.getTextureManager().bindTexture(this.BACKGROUND_TEXTURE);

		int l = this.guiLeft;

		int t = this.guiTop;

		this.blit(l, t, 0, 0, this.xSize, this.ySize);

		if(((BrickFurnaceContainer)this.container).isBurning()) {

			int b = ((BrickFurnaceContainer) this.container).getBurnLeftScaled();

			this.blit(t + 56, t + 36 + 12 - b, 176, 12 - b, 14, b + 1);
		}

		int c = ((BrickFurnaceContainer)this.container).getBurnLeftScaled();
		this.blit(l + 79, t + 34, 176, 14, c + 1, 16);
	}

	public boolean mouseClicked(double mouseClickd1, double mouseClicked3, int mouseClicked5) {

		if(this.RECIPE_BOOK_GUI.mouseClicked(mouseClickd1, mouseClicked3, mouseClicked5)) {

			return true;
		}

		else  {

			return this.doesFit && this.RECIPE_BOOK_GUI.isVisible() ? true : super.mouseClicked(mouseClickd1, mouseClicked3, mouseClicked5);
		}
	}

	/*******************************************************************
	 * Called when the mouse is clicked over a slot or outside the gui.
	 *******************************************************************/
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {

		super.handleMouseClick(slotIn, slotId, mouseButton, type);

		this.RECIPE_BOOK_GUI.slotClicked(slotIn);
	}

	public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
		return this.RECIPE_BOOK_GUI.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_) ? false : super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
	}
	
	protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeftIn, int guiTopIn, int mouseButton) {
		
		boolean flag = mouseX < (double)guiLeftIn || mouseY < (double)guiTopIn || mouseX >= (double)(guiLeftIn + this.xSize) || mouseY >= (double)(guiTopIn + this.ySize);
		
		return this.RECIPE_BOOK_GUI.func_195604_a(mouseX, mouseY, guiLeftIn, guiTopIn, guiLeftIn, guiTopIn, mouseButton) && flag;
		
	}
	
	public boolean charTyped(char charTyped1, int charTyped2) {
		
		return this.RECIPE_BOOK_GUI.charTyped(charTyped1, charTyped2) ? true : super.charTyped(charTyped1, charTyped2);
	}
	
	public void recipesUpdated() {
		
		this.RECIPE_BOOK_GUI.recipesUpdated();
	}
	
	public RecipeBookGui getRecipeGui() {
		
		return this.RECIPE_BOOK_GUI;
	}
	
	public void removed() {
		
		this.RECIPE_BOOK_GUI.removed();
		super.removed();
	}
	

}
