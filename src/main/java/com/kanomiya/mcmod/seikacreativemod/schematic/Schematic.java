package com.kanomiya.mcmod.seikacreativemod.schematic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class Schematic {
	public short width, depth, height;
	public SchematicBlock[] blocks;
	public NBTTagList tileEntities;
	public NBTTagList entities;

	public void build(World worldIn, BlockPos rlPos) {
		ArrayList<SchematicBlock> airBlocks = new ArrayList<SchematicBlock>();

		for (SchematicBlock schBlock: blocks) {
			BlockPos pos = rlPos.add(schBlock.getBlockPos());

			if (schBlock.block.canPlaceBlockAt(worldIn, pos)) {
				worldIn.setBlockState(pos, schBlock.getBlockState(), 2);
			} else {
				airBlocks.add(schBlock);
			}
		}

		for (SchematicBlock schBlock: airBlocks) {
			BlockPos pos = rlPos.add(schBlock.getBlockPos());
			worldIn.setBlockState(pos, schBlock.block.getStateFromMeta(schBlock.meta), 2);
		}

		// TileEntities
		for (int ii=0; ii<tileEntities.tagCount(); ii++) {
			NBTTagCompound eachtag = tileEntities.getCompoundTagAt(ii);
			BlockPos pos = new BlockPos(
					eachtag.getInteger("x") +rlPos.getX(),
					eachtag.getInteger("y") +rlPos.getY(),
					eachtag.getInteger("z") +rlPos.getZ());

			worldIn.setTileEntity(pos, TileEntity.createAndLoadEntity(eachtag));
		}

		// Entities
		for (int ii=0; ii<entities.tagCount(); ii++) {
			NBTTagCompound eachtag = entities.getCompoundTagAt(ii);
			Entity ent = EntityList.createEntityFromNBT(eachtag, worldIn);
			ent.serverPosX += rlPos.getX();
			ent.serverPosY += rlPos.getY();
			ent.serverPosZ += rlPos.getZ();

			worldIn.spawnEntityInWorld(ent);
		}


	}

	public void test(World world, BlockPos rlPos) {
		world.setBlockState(rlPos, Blocks.beacon.getDefaultState(), 2);
		world.setBlockState(rlPos.add(width, 0, 0), Blocks.beacon.getDefaultState(), 2);
		world.setBlockState(rlPos.add(0, 0, depth), Blocks.beacon.getDefaultState(), 2);
		world.setBlockState(rlPos.add(width, 0, depth), Blocks.beacon.getDefaultState(), 2);
	}






	public static Schematic createFromNBT(NBTTagCompound tag) {
		Schematic sch = new Schematic();
		sch.height = tag.getShort("Height");
		sch.depth = tag.getShort("Length");
		sch.width = tag.getShort("Width");

		int[] blocks;
		int[] data;

		if (tag.hasKey("Blocks", NBT.TAG_INT_ARRAY) && tag.hasKey("Data", NBT.TAG_INT_ARRAY)) {
			blocks = tag.getIntArray("Blocks");
			data = tag.getIntArray("Data");
		} else {
			byte[] byteBlocks = tag.getByteArray("Blocks");
			byte[] byteData = tag.getByteArray("Data");

			blocks = new int[byteBlocks.length];
			data = new int[byteData.length];
			for (int i=0; i<byteBlocks.length; i++) { blocks[i] = byteBlocks[i] & 0xFF; }
			for (int i=0; i<byteData.length; i++) { data[i] = byteData[i] & 0xFF; }
		}

		// Blocks
		ArrayList<SchematicBlock> blockList = new ArrayList<SchematicBlock>();
		int index = 0;

		for (int j=0; j<sch.height; j++) {
			for (int k=0; k<sch.depth; k++) {
				for (int i=0; i<sch.width; i++) {
					Block block = Block.getBlockById(blocks[index]);
					blockList.add(new SchematicBlock(new BlockPos(i, j, k), block, data[index]));

					index ++;
				}
			}
		}

		sch.blocks = blockList.toArray(new SchematicBlock[blockList.size()]);

		// TileEntities
		sch.tileEntities = tag.getTagList("TileEntities", NBT.TAG_COMPOUND);

		// Entities
		sch.entities = tag.getTagList("Entities", NBT.TAG_COMPOUND);

		return sch;
	}

	public static NBTTagCompound getNBT(Schematic sch) {
		NBTTagCompound tag = new NBTTagCompound();

		tag.setShort("Height", sch.height);
		tag.setShort("Length", sch.depth);
		tag.setShort("Width", sch.width);


		int[] blocks = new int[sch.height *sch.depth *sch.width];
		int[] data = new int[sch.height *sch.depth *sch.width];
		// byte[] bytes = tag.getByteArray("Blocks");
		// byte[] data = tag.getByteArray("Data");

		// Blocks
		int index = 0;

		for (int j=0; j<sch.height; j++) {
			for (int k=0; k<sch.depth; k++) {
				for (int i=0; i<sch.width; i++) {
					SchematicBlock schBlock = sch.blocks[index];

					blocks[index] = Block.getIdFromBlock(schBlock.block);
					data[index] = schBlock.meta;

					index ++;
				}
			}
		}

		// XXX: 注意-ByteArray>>IntArray
		tag.setIntArray("Blocks", blocks);
		tag.setIntArray("Data", data);

		// TileEntities
		tag.setTag("TileEntities", sch.tileEntities);

		// Entities
		tag.setTag("Entities", sch.entities);



		return tag;
	}

	public static NBTTagCompound getNBT(World world, int x1, int y1, int z1, int x2, int y2, int z2, boolean old) {
		NBTTagCompound tag = new NBTTagCompound();

		// Positions
		int minX = Math.min(x1, x2);
		int maxX = Math.max(x1, x2);
		int minY = Math.min(y1, y2);
		int maxY = Math.max(y1, y2);
		int minZ = Math.min(z1, z2);
		int maxZ = Math.max(z1, z2);

		int height = maxY -minY +1;
		int depth = maxZ -minZ +1;
		int width = maxX -minX +1;

		tag.setInteger("Height", height);
		tag.setInteger("Length", depth);
		tag.setInteger("Width", width);

		// Blocks
		// TileEntities
		int[] blocks = new int[height *depth *width];
		int[] data = new int[height *depth *width];
		NBTTagList tileEntityList = new NBTTagList();
		int index = 0;

		for (int y=minY; y<=maxY; y++) {
			for (int z=minZ; z<=maxZ; z++) {
				for (int x=minX; x<=maxX; x++) {
					BlockPos pos = new BlockPos(x, y, z);
					IBlockState state = world.getBlockState(pos);

					blocks[index] = Block.getIdFromBlock(state.getBlock());
					data[index] = state.getBlock().getMetaFromState(state);

					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null) {
						NBTTagCompound tileEntityTag = new NBTTagCompound();
						tileEntity.writeToNBT(tileEntityTag);
						tileEntityList.appendTag(tileEntityTag);
					}

					index ++;
				}
			}
		}

		// XXX: 注意-ByteArray>>IntArray
		if (old) {
			byte[] bBlocks = new byte[blocks.length];
			byte[] bData = new byte[blocks.length];

			for (int i=0; i<blocks.length; i++) {
				bBlocks[i] = (byte) ((255 < blocks[i]) ? -128 : blocks[i] -128);
				bData[i] = (byte) ((255 < data[i]) ? -128 : data[i] -128);
			}

			tag.setByteArray("Blocks", bBlocks);
			tag.setByteArray("Data", bData);

		} else {
			tag.setIntArray("Blocks", blocks);
			tag.setIntArray("Data", data);
		}

		tag.setTag("TileEntities", tileEntityList);

		// Entities
		NBTTagList entityList = new NBTTagList();
		List<Entity> list = world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.fromBounds(minX, minY, minZ, maxX, maxY, maxZ));

		for (Entity entity: list) {
			if (! (entity instanceof EntityPlayer)) {
				NBTTagCompound entityTag = new NBTTagCompound();
				entity.writeToNBT(entityTag);
				entityList.appendTag(entityTag);
			}
		}
		tag.setTag("Entities", entityList);

		return tag;
	}


}
