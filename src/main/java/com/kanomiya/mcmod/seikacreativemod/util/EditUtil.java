package com.kanomiya.mcmod.seikacreativemod.util;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPosWand;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class EditUtil {
	protected static final IBlockState airState = Blocks.air.getDefaultState();
	protected static final int airStateId = Block.getStateId(airState);

	public static int getYFacingTheSky(World world, int x, int y, int z) {
		BlockPos blockpos = new BlockPos(x, y, z);

		if (! world.isAirBlock(blockpos)) {
			while (y++<255 && ! world.isAirBlock(new BlockPos(x, y, z)));
		} else {
			while (y-->0 && ! world.isAirBlock(new BlockPos(x, y, z)));
		}

		return y;
	}


	public static NBTTagCompound getItemStackTag(ItemStack item) {
		NBTTagCompound tag = item.getTagCompound();

		if(tag == null) {
			tag = new NBTTagCompound();
			item.setTagCompound(tag);
		}

		return tag;
	}



	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// ItemStack Check
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public static boolean hasBlockState(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemBlock) { return true; }
			if (FluidContainerRegistry.isFilledContainer(stack)) { return true; }
			if (item instanceof ItemPipette)  { return ! getItemStackTag(stack).getCompoundTag("dataPool").hasNoTags(); }


		}

		return true;
	}

	public static IBlockState getBlockState(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemBlock) { return Block.getBlockFromItem(item).getStateFromMeta(stack.getMetadata()); }

			if (FluidContainerRegistry.isFilledContainer(stack)) {
				FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(stack);
				return fs.getFluid().getBlock().getStateFromMeta(stack.getMetadata());
			}

			if (item instanceof ItemPipette) {
				NBTTagCompound nbt = getItemStackTag(stack).getCompoundTag("dataPool");

				if (! nbt.hasNoTags()) { return Block.getStateById(nbt.getInteger("blockputid")); }
			}

		}

		return Blocks.air.getDefaultState();
	}

	public static boolean hasTileEntity(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemPipette)  { return ! getItemStackTag(stack).getCompoundTag("dataPool").hasKey("tileentity"); }

		}

		return false;
	}

	public static TileEntity getTileEntity(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();
			if (item instanceof ItemPipette)  {
				NBTTagCompound nbt = getItemStackTag(stack).getCompoundTag("dataPool").getCompoundTag("tileentity");

				return TileEntity.createAndLoadEntity(nbt);
			}

		}

		return null;
	}


	public static boolean hasPositon(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();

			if (item instanceof ItemPosWand) {
				return (getItemStackTag(stack).hasKey("position"));
			}
		}

		return false;
	}

	public static BlockPos getPositon(ItemStack stack) {
		if (stack != null) {
			Item item = stack.getItem();

			if (item instanceof ItemPosWand) {
				int[] pos = getItemStackTag(stack).getIntArray("position");

				if (pos.length == 3) { return new BlockPos(pos[0], pos[1], pos[2]); }
			}
		}

		return null;
	}





	private static boolean setBlock(World world, BlockPos pos, IBlockState put, TileEntity putTileEntity, boolean isDemo) {

		if (! isDemo) {
			world.setBlockState(pos, put);
			if (putTileEntity != null) { world.setTileEntity(pos, putTileEntity); }
		}

		return true;
	}


	private static boolean setAirToBlock(World world, BlockPos pos, IBlockState put, TileEntity putTileEntity, boolean isDemo) {
		if (world.isAirBlock(pos)) { return setBlock(world, pos, put, putTileEntity, isDemo); }

		return false;
	}

	private static boolean setMatchesToBlock(World world, BlockPos pos, IBlockState target, IBlockState put, TileEntity putTileEntity, boolean isDemo) {
		int targetId = Block.getStateId(target);

		if (Block.getStateId(world.getBlockState(pos)) == targetId) {
			return setBlock(world, pos, put, putTileEntity, isDemo);

		} else if (targetId == Block.getStateId(put) && targetId == airStateId) {
			return setBlock(world, pos, airState, null, isDemo);
		}

		return false;
	}


	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// Fill
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public static int fill(World world, BlockPos pos, IBlockState put, TileEntity putTileEntity, int minY, boolean isDemo) {
		if (pos == null) { return -1; }
		if (Block.getStateId(put) == airStateId) { return -1; }

		Integer count = 0;

		try {
			for (int yy=1; (minY <= pos.getY() -yy && yy <= pos.getY()); yy++) {
				if (! doFill(world, pos.down(yy), put, putTileEntity, count, isDemo)) break;
			}
		} catch (StackOverflowError e) {
			if (! isDemo) {SeikaCreativeMod.logger.error("[SCM] StackOverflowError: Failed to fill."); }
			count = -1;
		}

		return count;
	}

	public static int fill(World world, BlockPos pos, IBlockState put, TileEntity putTileEntity, boolean isDemo) {
		return fill(world, pos, put, putTileEntity, 0, isDemo);
	}


	private static boolean doFill(World world, BlockPos pos, IBlockState put, TileEntity putTileEntity, Integer count, boolean isDemo) {

		if (world.isAirBlock(pos)) {
			if (setAirToBlock(world, pos, put, putTileEntity, isDemo)) { count ++; }

			if (world.isAirBlock(pos.add(0, 0, -1))) {
				doFill(world, pos.add(0, 0, -1), put, putTileEntity, count, isDemo);
			}
			if (world.isAirBlock(pos.add(0, 0, +1))) {
				doFill(world, pos.add(0, 0, +1), put, putTileEntity, count, isDemo);
			}
			if (world.isAirBlock(pos.add(-1, 0, 0))) {
				doFill(world, pos.add(-1, 0, 0), put, putTileEntity, count, isDemo);
			}
			if (world.isAirBlock(pos.add(+1, 0, 0))) {
				doFill(world, pos.add(+1, 0, 0), put, putTileEntity, count, isDemo);
			}

			return true;
		}

		return false;
	}





	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// Replace
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public static int replace(World world, BlockPos from, BlockPos to, IBlockState target, IBlockState put, TileEntity putTileEntity, boolean isDemo) {
		if (from == null || to == null) { return -1; }
		if (Block.getStateId(target) == Block.getStateId(put) && Block.getStateId(target) != airStateId) { return -1; }

		int minX = Math.min(from.getX(), to.getX());
		int maxX = Math.max(from.getX(), to.getX());
		int minY = Math.min(from.getY(), to.getY());
		int maxY = Math.max(from.getY(), to.getY());
		int minZ = Math.min(from.getZ(), to.getZ());
		int maxZ = Math.max(from.getZ(), to.getZ());

		int count = 0;

		for (int y=minY; y<=maxY; y++) {
		for (int x=minX; x<=maxX; x++) {
		for (int z=minZ; z<=maxZ; z++) {
			if (setMatchesToBlock(world, new BlockPos(x, y, z), target, put, putTileEntity, isDemo)) { count ++; }
		}
		}
		}

		return count;
	}


	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// Box
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public static int box(World world, BlockPos from, BlockPos to, IBlockState put, TileEntity putTileEntity, boolean isDemo) {
		if (from == null || to == null) { return -1; }
		if (Block.getStateId(put) == Block.getStateId(Blocks.air.getDefaultState())) { return -1; }


		int minX = Math.min(from.getX(), to.getX());
		int maxX = Math.max(from.getX(), to.getX());
		int minY = Math.min(from.getY(), to.getY());
		int maxY = Math.max(from.getY(), to.getY());
		int minZ = Math.min(from.getZ(), to.getZ());
		int maxZ = Math.max(from.getZ(), to.getZ());

		int count = 0;

		for (int y=minY; y<=maxY; y++) {
		for (int x=minX; x<=maxX; x++) {
			if (setAirToBlock(world, new BlockPos(x, y, minZ), put, putTileEntity, isDemo)) { count ++; }
			if (setAirToBlock(world, new BlockPos(x, y, maxZ), put, putTileEntity, isDemo)) { count ++; }

		}
		}

		for (int y=minY; y<=maxY; y++) {
		for (int z=minZ; z<=maxZ; z++) {
			if (setAirToBlock(world, new BlockPos(minX, y, z), put, putTileEntity, isDemo)) { count ++; }
			if (setAirToBlock(world, new BlockPos(maxX, y, z), put, putTileEntity, isDemo)) { count ++; }
		}
		}

		for (int x=minX; x<=maxX; x++) {
		for (int z=minZ; z<=maxZ; z++) {
			if (setAirToBlock(world, new BlockPos(x, minY, z), put, putTileEntity, isDemo)) { count ++; }
			if (setAirToBlock(world, new BlockPos(x, maxY, z), put, putTileEntity, isDemo)) { count ++; }
		}
		}

		return count;
	}










}
