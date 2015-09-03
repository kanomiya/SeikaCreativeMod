package com.kanomiya.mcmod.seikacreativemod.schematic;

import net.minecraft.block.Block;

public class SchematicBlock {
	public int x, y, z, meta;
	public Block block;


	public SchematicBlock(int x, int y, int z, Block block, int meta) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.block = block;
		this.meta = meta;
	}


}