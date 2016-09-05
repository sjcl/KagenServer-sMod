package com.bebehp.mc.kagen.changemachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityChangeMachine extends TileEntity implements IInventory {

	protected ItemStack[] itemStacks = new ItemStack[2];
	protected byte importingSlot;
	protected byte exportingSlot;

	public boolean tryImportItemStack(final ItemStack itemStack) {
		for (int i = 0; i < getSizeInventory(); i++) {
			this.importingSlot = getNextSlot(this.importingSlot);
			if (this.itemStacks[this.importingSlot] == null) {
				this.itemStacks[this.importingSlot] = itemStack.copy();
				return true;
			}
		}
		return false;
	}

	public ItemStack tryExportItemStack() {
		for (int i = 0; i < getSizeInventory(); i++) {
			this.exportingSlot = getNextSlot(this.exportingSlot);
			if (this.itemStacks[this.exportingSlot] != null) {
				final ItemStack itemStack = this.itemStacks[this.exportingSlot].copy();
				this.itemStacks[this.exportingSlot] = null;
				return itemStack;
			}
		}
		return null;
	}

	protected byte getNextSlot(byte slot) {
		slot++;
		if (slot >= getSizeInventory())
			slot = 0;
		return slot;
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		final NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.itemStacks.length; i++) {
			if (this.itemStacks[i] == null)
				continue;
			final NBTTagCompound nbt1 = new NBTTagCompound();
			nbt1.setByte("Slot", (byte) i);
			this.itemStacks[i].writeToNBT(nbt1);
			nbttaglist.appendTag(nbt1);
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setByte("ImportingSlot", this.importingSlot);
		nbt.setByte("ExportingSlot", this.exportingSlot);
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		final NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.itemStacks = new ItemStack[54];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			final NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			final byte b0 = nbt1.getByte("Slot");
			if (0 <= b0 && b0 < this.itemStacks.length) {
				this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		this.importingSlot = nbt.getByte("ImportingSlot");
		this.exportingSlot = nbt.getByte("ExportingSlot");
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(final int slot) {
		return this.itemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(final int slot, final int amount) {
		if (this.itemStacks[slot] == null)
			return null;
		ItemStack itemstack;
		if (this.itemStacks[slot].stackSize <= amount) {
			itemstack = this.itemStacks[slot];
			this.itemStacks[slot] = null;
			return itemstack;
		}
		itemstack = this.itemStacks[slot].splitStack(amount);
		if (this.itemStacks[slot].stackSize < 1) {
			this.itemStacks[slot] = null;
		}
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack itemStack) {
		this.itemStacks[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "container.KagenMod.ChangeMachine";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
		return true;
	}
}
