package com.kanomiya.mcmod.seikacreativemod.block;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityFill;
import com.kanomiya.mcmod.seikacreativemod.util.EditUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class BlockFill extends BlockContainer {
	public static int EDIT_MINY = 0;

	public BlockFill() {
		super(Material.GROUND);
		setCreativeTab(SeikaCreativeMod.tabSeika);
		setUnlocalizedName("blockFill");

		setHardness(0.5f);
		setResistance(1.0f);
		setSoundType(SoundType.STONE);

	}

	// RS切断
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) return true;
		if (hand == EnumHand.OFF_HAND) return false;

		TileEntityFill te = (TileEntityFill) worldIn.getTileEntity(pos);
		if (te == null) return false;

		if (heldItem != null) {
			if (heldItem.getItem() instanceof ItemBlock) {
				Block block = Block.getBlockFromItem(heldItem.getItem());

                te.setPutState(block.getStateFromMeta(heldItem.getMetadata()));

				playerIn.addChatMessage(new TextComponentString(
						"Set new block as "
								+ block.getLocalizedName()));

			} else if (heldItem.getItem() == Items.GUNPOWDER) {
				te.sidelength ++;

				playerIn.addChatMessage(new TextComponentString(
						"Set Fill Depth as "
								+ te.sidelength));
			} else {

			    FluidStack fluidStack = FluidUtil.getFluidContained(heldItem);

			    if (fluidStack != null)
			    {
			        te.setPutState(fluidStack.getFluid().getBlock().getStateFromMeta(heldItem.getMetadata()));

			        playerIn.addChatMessage(new TextComponentString(
		                        "Set new block as "
		                                + te.getPutState().getBlock().getLocalizedName()));

		             return true;
			    }


				return false;
			}

		}
		else {
			te.setPutState(Blocks.AIR.getDefaultState());
			playerIn.addChatMessage(new TextComponentString(
					"Set new block as Air"));
		}

		return true;

	}



	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		onBlockAdded(worldIn, pos, state);
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
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
}
