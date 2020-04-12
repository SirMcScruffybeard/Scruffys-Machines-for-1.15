package com.sirscruffybeard.scruffysmachines.gui;

import com.sirscruffybeard.scruffysmachines.gui.bases.FurnaceBaseScreen;
import com.sirscruffybeard.scruffysmachines.objects.container.BrickFurnaceContainer;
import net.minecraft.client.gui.recipebook.FurnaceRecipeGui;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BrickFurnaceScreen extends FurnaceBaseScreen<BrickFurnaceContainer>{

	   private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation("textures/gui/container/furnace.png");

	   public BrickFurnaceScreen(BrickFurnaceContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
	      super(screenContainer, new FurnaceRecipeGui(), inv, titleIn, FURNACE_GUI_TEXTURES);
	   }
}
