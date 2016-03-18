package com.kanomiya.mcmod.seikacreativemod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityPreparation;

public class BlockPreparation extends BlockContainer {

	public BlockPreparation() {
		super(Material.ground);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockPreparation");

		setHardness(0.5f);
		setResistance(1.0f);
		setStepSound(SoundType.STONE);
	}


	// RS切断
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) { return false; }

		TileEntityPreparation te = (TileEntityPreparation) worldIn.getTileEntity(pos);

		if (te == null) { return false; }

		if (heldItem != null) {
			if (heldItem.getItem() instanceof ItemBlock) {
				Block block = Block.getBlockFromItem(heldItem.getItem());

				if (te.setmode) {
					te.setTgtState(block.getStateFromMeta(heldItem.getMetadata()));

					playerIn.addChatMessage(new TextComponentString(
							"Set the target as "
									+ block.getLocalizedName()));
				} else {
					te.setPutState(block.getStateFromMeta(heldItem.getMetadata()));

					playerIn.addChatMessage(new TextComponentString(
							"Set new block as "
									+ te.getPutState().getBlock().getLocalizedName()));
				}

				te.setmode = ! te.setmode;

			} else if (heldItem.getItem() == Items.gunpowder) {
				if (te.sidelength < 96) {

					EntityPlayerMP emp = (EntityPlayerMP) playerIn;
					if (!emp.interactionManager.getGameType()
							.isCreative()) {
						heldItem = heldItem.splitStack(heldItem.stackSize - 1);
					}

					te.sidelength += 4;
					playerIn.addChatMessage(new TextComponentString(
							"Set the power as " + te.sidelength));
				} else {
					playerIn.addChatMessage(new TextComponentString(
							"The power is maximum(96)."));
				}

			} else if (heldItem.getItem() == Items.water_bucket) {
				Block block = Blocks.water;

				if (te.setmode) {
					te.setTgtState(block.getDefaultState());

					playerIn.addChatMessage(new TextComponentString(
							"Set the target as "
									+ block.getLocalizedName()));
				} else {
					te.setPutState(block.getDefaultState());

					playerIn.addChatMessage(new TextComponentString(
							"Set new block as "
									+ block.getLocalizedName()));
				}

				te.setmode = ! te.setmode;

			} else if (heldItem.getItem() == Items.lava_bucket) {
				Block block = Blocks.lava;

				if (te.setmode) {
					te.setTgtState(block.getDefaultState());

					playerIn.addChatMessage(new TextComponentString(
							"Set the target as "
									+ block.getLocalizedName()));
				} else {
					te.setPutState(block.getDefaultState());

					playerIn.addChatMessage(new TextComponentString(
							"Set new block as "
									+ block.getLocalizedName()));
				}

			} else {
				return false;
			}


		} else {
			if (te.setmode) {
				te.setTgtState(Blocks.air.getDefaultState());
				playerIn.addChatMessage(new TextComponentString(
						"Set the target as All"));
			} else {
				te.setPutState(Blocks.air.getDefaultState());
				playerIn.addChatMessage(new TextComponentString(
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
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
}
