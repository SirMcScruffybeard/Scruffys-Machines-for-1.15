package com.mrmcscruffybeard.scruffysmachines;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mrmcscruffybeard.scruffysmachines.init.BlockInit;
import com.mrmcscruffybeard.scruffysmachines.init.ItemInit;
import com.mrmcscruffybeard.scruffysmachines.init.ModContainerTypes;
import com.mrmcscruffybeard.scruffysmachines.init.ModTileEntityTypes;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;

@Mod("scruffysmachines")
@Mod.EventBusSubscriber(modid = ScruffysMachines.MOD_ID, bus = Bus.MOD)
public class ScruffysMachines
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "scruffysmachines";
    
    public static ScruffysMachines instance;
    
    public ScruffysMachines() {
    	
    	
    	
    	final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
       modEventBus.addListener(this::setup);
      // modEventBus.addListener(this::enqueueIMC);
      // modEventBus.addListener(this::processIMC);
        modEventBus.addListener(this::doClientStuff);

        ItemInit.ITEMS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
		ModContainerTypes.CONTAINER_TYPES.register(modEventBus);
        
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		BlockInit.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
			final Item.Properties properties = new Item.Properties().group(ScruffysMachinesItemGroup.instance);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});

		LOGGER.debug("Registered BlockItems!");
	}

    private void setup(final FMLCommonSetupEvent event) {
          
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
       
        
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }
    
    public static class ScruffysMachinesItemGroup extends ItemGroup {
    	
    	public static final ScruffysMachinesItemGroup instance = new ScruffysMachinesItemGroup(ItemGroup.GROUPS.length, "scruffysmachinestab");
    	
		private ScruffysMachinesItemGroup(int index, String label) {
		
			super(index, label);
		
		}
    	
    	@Override
    	public ItemStack createIcon() {
    		
    		return new ItemStack(BlockInit.LEATHER_BLOCK.get());
    	}
    }

}//public class ScruffysMachines
