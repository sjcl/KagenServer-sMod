package com.bebehp.mc.kagen.changemachine;

import java.util.Random;

import com.bebehp.mc.kagen.KagenMod;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChangeMachine extends Block implements ITileEntityProvider {

	private final Random random = new Random();

	public BlockChangeMachine() {
		super(Material.rock);
		setCreativeTab(KagenMod.tabKagen);
		setHardness(5.0F);
		setResistance(1.0F);
		setStepSound(soundTypeMetal);
		this.isBlockContainer = true;
	}

	@Override
	public TileEntity createNewTileEntity(final World world, final int meta) {
		return new TileEntityChangeMachine();
	}

	@Override
	public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float hitX, final float hitY, final float hitZ) {
		final TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || !(tileEntity instanceof TileEntityChangeMachine))
			return false;
		final TileEntityChangeMachine chest = (TileEntityChangeMachine) tileEntity;
		if (player.getHeldItem() == null) {
			player.inventory.mainInventory[player.inventory.currentItem] = chest.tryExportItemStack();
		} else {
			if (chest.tryImportItemStack(player.getHeldItem())) {
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
		}
		return true;
	}

	@Override
	public void breakBlock(final World world, final int x, final int y, final int z, final Block block, final int meta) {
		// TileEntityの内部にあるアイテムをドロップさせる。
		final TileEntityChangeMachine tileentity = (TileEntityChangeMachine) world.getTileEntity(x, y, z);
		if (tileentity != null) {
			for (int i = 0; i < tileentity.getSizeInventory(); i++) {
				final ItemStack itemStack = tileentity.getStackInSlot(i);

				if (itemStack != null) {
					final float f = this.random.nextFloat() * 0.6F + 0.1F;
					final float f1 = this.random.nextFloat() * 0.6F + 0.1F;
					final float f2 = this.random.nextFloat() * 0.6F + 0.1F;

					while (itemStack.stackSize > 0) {
						int j = this.random.nextInt(21) + 10;

						if (j > itemStack.stackSize) {
							j = itemStack.stackSize;
						}

						itemStack.stackSize -= j;
						final EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2,
								new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

						if (itemStack.hasTagCompound()) {
							entityItem.getEntityItem()
							.setTagCompound(((NBTTagCompound) itemStack.getTagCompound().copy()));
						}

						final float f3 = 0.025F;
						entityItem.motionX = (float) this.random.nextGaussian() * f3;
						entityItem.motionY = (float) this.random.nextGaussian() * f3 + 0.1F;
						entityItem.motionZ = (float) this.random.nextGaussian() * f3;
						world.spawnEntityInWorld(entityItem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
