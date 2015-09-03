package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityPreparation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPreparation extends BlockContainer {

	public BlockPreparation() {
		super(Material.ground);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockPreparation");

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

		TileEntityPreparation te = (TileEntityPreparation) world.getTileEntity(pos);

		if (te == null) { return false; }

		ItemStack is = player.getHeldItem();

		if (is != null) {
			if (is.getItem() instanceof ItemBlock) {
				Block block = Block.getBlockFromItem(is.getItem());

				if (te.setmode) {
					te.setTgtState(block.getStateFromMeta(is.getMetadata()));

					player.addChatMessage(new ChatComponentText(
							"Set the target as "
									+ block.getLocalizedName()));
				} else {
					te.setPutState(block.getStateFromMeta(is.getMetadata()));

					player.addChatMessage(new ChatComponentText(
							"Set new block as "
									+ te.getPutState().getBlock().getLocalizedName()));
				}

				te.setmode = ! te.setmode;

			} else if (is.getItem() == Items.gunpowder) {
				if (te.sidelength < 96) {

					EntityPlayerMP emp = (EntityPlayerMP) player;
					if (!emp.theItemInWorldManager.getGameType()
							.isCreative()) {
						is = is.splitStack(is.stackSize - 1);
					}

					te.sidelength += 4;
					player.addChatMessage(new ChatComponentText(
							"Set the power as " + te.sidelength));
				} else {
					player.addChatMessage(new ChatComponentText(
							"The power is maximum(96)."));
				}

			} else if (is.getItem() == Items.water_bucket) {
				Block block = Blocks.water;

				if (te.setmode) {
					te.setTgtState(block.getDefaultState());

					player.addChatMessage(new ChatComponentText(
							"Set the target as "
									+ block.getLocalizedName()));
				} else {
					te.setPutState(block.getDefaultState());

					player.addChatMessage(new ChatComponentText(
							"Set new block as "
									+ block.getLocalizedName()));
				}

				te.setmode = ! te.setmode;

			} else if (is.getItem() == Items.lava_bucket) {
				Block block = Blocks.lava;

				if (te.setmode) {
					te.setTgtState(block.getDefaultState());

					player.addChatMessage(new ChatComponentText(
							"Set the target as "
									+ block.getLocalizedName()));
				} else {
					te.setPutState(block.getDefaultState());

					player.addChatMessage(new ChatComponentText(
							"Set new block as "
									+ block.getLocalizedName()));
				}

			} else {
				return false;
			}


		} else {
			if (te.setmode) {
				te.setTgtState(Blocks.air.getDefaultState());
				player.addChatMessage(new ChatComponentText(
						"Set the target as All"));
			} else {
				te.setPutState(Blocks.air.getDefaultState());
				player.addChatMessage(new ChatComponentText(
						"Set new block as Air"));
			}

			te.setmode = ! te.setmode;

		}

		return true;
	}



	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		onBlockAdded(world, pos, state);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (world.isRemote) { return ; }

		TileEntityPreparation te = (TileEntityPreparation) world.getTileEntity(pos);
		if (world.isBlockPowered(pos)) {

			if (te == null) { return ; }
			int r = te.sidelength;
			IBlockState putBlock = te.getPutState();
			IBlockState delBlock = te.getTgtState();

			if ((delBlock.getBlock() == Blocks.air && putBlock.getBlock() != Blocks.air) || (r == 0)) {
				world.playAuxSFX(1001, pos, 0);
				return ;
			}

			for (int i= -r; i<=r; i++) {
				for (int ii= 0; ii<255 -pos.getY(); ii++) {
					for (int iii= -r; iii<=r; iii++) {
						BlockPos newPos = pos.add(i,ii,iii);

						if (! world.isAirBlock(newPos)) {
							if (delBlock.getBlock() == Blocks.air
									|| world.getBlockState(newPos) == delBlock) {

								world.setBlockState(newPos, putBlock);
							}
						}

					}
				}
			}

			world.setBlockToAir(pos);

			te.setPrevRSState(true);
		} else {
			te.setPrevRSState(false);
		}

	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPreparation();
	}

	@Override
	public int getRenderType() {
		return 3;
	}
}
