package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityFill;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockFill extends BlockContainer {
	public static int EDIT_MINY = 0;

	public BlockFill() {
		super(Material.ground);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockFill");

		setHardness(0.5f);
		setResistance(1.0f);
		setStepSound(Block.soundTypeStone);

	}

	// RS切断
	@Override
	public boolean isOpaqueCube() { return false; }


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {

		if (world.isRemote) { return false; }
		TileEntityFill te = (TileEntityFill) world.getTileEntity(pos);
		if (te == null) { return false; }

		ItemStack is = player.getHeldItem();
		if (is != null) {
			if (is.getItem() instanceof ItemBlock) {
				Block block = Block.getBlockFromItem(is.getItem());

				te.setPutState(block.getStateFromMeta(is.getMetadata()));

				player.addChatMessage(new ChatComponentText(
						"Set new block as "
								+ block.getLocalizedName()));

			} else if (is.getItem() == Items.water_bucket) {
				te.setPutState(Blocks.water.getDefaultState());

				player.addChatMessage(new ChatComponentText(
						"Set new block as "
								+ te.getPutState().getBlock().getLocalizedName()));

			} else if (is.getItem() == Items.lava_bucket) {
				te.setPutState(Blocks.lava.getDefaultState());

				player.addChatMessage(new ChatComponentText(
						"Set new block as "
								+ te.getPutState().getBlock().getLocalizedName()));
			} else if (is.getItem() == Items.gunpowder) {
				te.sidelength ++;

				player.addChatMessage(new ChatComponentText(
						"Set Fill Depth as "
								+ te.sidelength));
			} else {
				return false;
			}

		}
		else {
			te.setPutState(Blocks.air.getDefaultState());
			player.addChatMessage(new ChatComponentText(
					"Set new block as Air"));
		}

		return true;

	}



	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		onBlockAdded(world, pos, state);
	}

	@Override public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) { return ; }

		TileEntityFill te = (TileEntityFill) world.getTileEntity(pos);
		if (te == null) { return ; }

		if (world.isBlockPowered(pos)) {
			if (! te.isPrevPowered()) {
				IBlockState putBlock = te.getPutState();

				if (te.sidelength > -1) {
					EditUtil.fill(world, pos, putBlock, null, pos.getY() -te.sidelength, false);
				} else {
					EditUtil.fill(world, pos, putBlock, null, EDIT_MINY, false);
				}
			}

			te.setPrevRSState(true);
		} else {
			te.setPrevRSState(false);
		}

	}


	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFill();
	}

	@Override
	public int getRenderType() {
		return 3;
	}
}
