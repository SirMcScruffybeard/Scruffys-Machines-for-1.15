package com.sirscruffybeard.scruffysmachines.objects.items;

import java.util.List;

import com.sirscruffybeard.scruffysmachines.objects.items.bases.SteamPodBase;
import com.sirscruffybeard.scruffysmachines.util.helpers.KeyboardHelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class LeatherSteamPodItem extends SteamPodBase{
	
	public static String ID = "leather"+ STEAM_POD;
	
	private int psi = 15;
	
	public LeatherSteamPodItem(Properties properties) {
		super(properties);
		
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		
		if(KeyboardHelper.isHoldingShift()) {
			
			tooltip.add(new StringTextComponent("Holds " + psi + "psi"));
		}
		
		else {
			
			tooltip.add(new StringTextComponent("Stores Steam"));
			tooltip.add(new StringTextComponent("Hold Shift for more information"));
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
