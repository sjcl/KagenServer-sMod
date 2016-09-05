package com.bebehp.mc.kagen;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kamesuta.mc.carrotmod.CarrotBubu;
import com.kamesuta.mc.carrotmod.CarrotCommand;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class KagenMod {
	public static final String owner = "Kagen";
	public static final Logger logger = LogManager.getLogger(owner);

	@Instance(Reference.MODID)
	public static KagenMod instance;

	public final CarrotBubu bubu = new CarrotBubu();
	public final CarrotCommand command = new CarrotCommand(this.bubu);

	public static CreativeTabs tabKagen = new KagenTab("Kagentab");

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		Register.registerItem();
		Register.registerBlock();
		//		NetworkRegistry.INSTANCE.registerGuiHandler(this.instance, new GuiHandler());
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this.bubu);
		logger.info("Kagen Mod is setting up.");
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event)
	{
		event.registerServerCommand(this.command);
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(final Map<String, String> mods, final Side side)
	{
		return false;
	}
}
