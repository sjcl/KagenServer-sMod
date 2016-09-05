package com.bebehp.mc.kagen.items;

import com.bebehp.mc.kagen.KagenMod;

public class Ticket extends MetaItems {

	public Ticket (final int type) {
		super (type);
		this.type = type;
		setCreativeTab(KagenMod.tabKagen);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
}