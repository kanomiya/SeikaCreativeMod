package com.kanomiya.mcmod.seikacreativemod.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.item.ItemPipette;

public class EditUtil {
	protected static final IBlockState airState = Blocks.AIR.getDefaultState();
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


	public static NBTTagCompound getItemStackTag(ItemStack stackIn) {
		if (stackIn == null) return null;

		NBTTagCompound tag = stackIn.getTagCompound();

		if(tag == null) {
			tag = new NBTTagCompound();
			stackIn.setTagCompound(tag);
		}

		return tag;
	}


	public static BlockPos intArrayToPos(int[] array) {
		if (array == null || array.length != 3) return null;
		return new BlockPos(intArrayToPosX(array), intArrayToPosY(array), intArrayToPosZ(array));
	}
	public static int intArrayToPosX(int[] array) { return array[0]; }
	public static int intArrayToPosY(int[] array) { return array[1]; }
	public static int intArrayToPosZ(int[] array) { return array[2]; }

	public static int[] posToIntArray(int posX, int posY, int posZ) {
		return new int[] { posX, posY, posZ };
	}

	public static int[] posToIntArray(BlockPos pos) {
		return posToIntArray(pos.getX(), pos.getY(), pos.getZ());
	}



	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
	// ItemStack Check
	// *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

	public static boolean hasBlockState(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			if (item instanceof ItemBlock) { return true; }
			if (item instanceof IFluidContainerItem && ((IFluidContainerItem) item).getFluid(stackIn) != null) { return true; }
			if (item instanceof ItemPipette)  { return ! getItemStackTag(stackIn).getCompoundTag("dataPool").hasNoTags(); }


		}

		return true;
	}

	public static IBlockState getBlockState(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			if (item instanceof ItemBlock) { return Block.getBlockFromItem(item).getStateFromMeta(stackIn.getMetadata()); }

			if (item instanceof IFluidContainerItem) {
				FluidStack fs = ((IFluidContainerItem) item).getFluid(stackIn);
				if (fs != null)
					return fs.getFluid().getBlock().getStateFromMeta(stackIn.getMetadata());
			}

			if (item instanceof ItemPipette) {
				NBTTagCompound nbt = getItemStackTag(stackIn).getCompoundTag("dataPool");

				if (! nbt.hasNoTags()) { return Block.getStateById(nbt.getInteger("blockputid")); }
			}

		}

		return Blocks.AIR.getDefaultState();
	}

	public static boolean hasTileEntity(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			if (item instanceof ItemPipette)  { return ! getItemStackTag(stackIn).getCompoundTag("dataPool").hasKey("tileentity"); }

		}

		return false;
	}

	public static TileEntity getTileEntity(ItemStack stackIn) {
		if (stackIn != null) {
			Item item = stackIn.getItem();
			if (item instanceof ItemPipette)  {
				NBTTagCompound nbt = getItemStackTag(stackIn).getCompoundTag("dataPool").getCompoundTag("tileentity");

				return TileEntity.create(nbt);
			}

		}

		return null;
	}


	public static void setPositon(ItemStack stackIn, BlockPos pos) {
		if (stackIn == null) return ;

		NBTTagCompound tag = getItemStackTag(stackIn);
		tag.setIntArray(TAG_POSITION, posToIntArray(pos));
	}

	public static boolean hasPositon(ItemStack stackIn) {
		if (stackIn == null) return false;
		return getItemStackTag(stackIn).hasKey(TAG_POSITION);
	}

	public static BlockPos getPositon(ItemStack stackIn) {
		if (stackIn == null) return null;
		return intArrayToPos(getPositionIntArray(stackIn));
	}

	public static int[] getPositionIntArray(ItemStack stackIn) {
		if (stackIn == null) return null;
		return getItemStackTag(stackIn).getIntArray(TAG_POSITION);
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
		if (Block.getStateId(put) == Block.getStateId(Blocks.AIR.getDefaultState())) { return -1; }


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


	public static final String TAG_POSITION = "position";










}
