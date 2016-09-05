package com.bebehp.mc.kagen;

import com.bebehp.mc.kagen.changemachine.BlockChangeMachine;
import com.bebehp.mc.kagen.changemachine.TileEntityChangeMachine;
import com.bebehp.mc.kagen.items.Casino;
import com.bebehp.mc.kagen.items.Ticket;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Register {

	public static Item casino;
	public static Item ticket;

	public static Block changeMachine;

	public static void registerItem() {
		casino = new Casino(7)
				.setUnlocalizedName("CASINO")
				.setTextureName(Reference.MODID + ":itemCASINO")
				.setMaxStackSize(64);
		GameRegistry.registerItem(casino, "casino");

		ticket = new Ticket(4)
				.setUnlocalizedName("Ticket")
				.setTextureName(Reference.MODID + ":itemTicket")
				.setMaxStackSize(16);
		GameRegistry.registerItem(ticket, "ticket");

	}

	public static void registerBlock() {

		changeMachine = new BlockChangeMachine()
				.setBlockName("chengeMachine")
				.setBlockTextureName(Reference.MODID + ":blockChangeMachine");

		GameRegistry.registerBlock(changeMachine, "changeMachine");
		GameRegistry.registerTileEntity(TileEntityChangeMachine.class, "TileEntityChangeMachine");

	}
}
