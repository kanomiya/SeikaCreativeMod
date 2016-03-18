package com.kanomiya.mcmod.seikacreativemod.schematic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class SchematicBlock {
	public BlockPos pos;
	public int meta;
	public Block block;


	public SchematicBlock(BlockPos parPos, Block parBlock, int parMeta) {
		pos = parPos;
		block = parBlock;
		meta = parMeta;
	}

	public BlockPos getBlockPos() { return pos; }
	public IBlockState getBlockState() { return block.getStateFromMeta(meta); }


	public void writeToNBT(NBTTagCompound tag) {

	}


}
