package com.kanomiya.mcmod.seikacreativemod.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.schematic.Schematic;

public class CommandSchematic extends CommandBase {
	protected static final int MISS_SCHEMATIC = 0;
	protected static final int MISS_LOAD = 1;
	protected static final int MISS_TEST = 2;
	protected static final int MISS_SAVE = 3;
	protected static final int MISS_OLDSAVE = 4;

	@Override public String getName() {
		return "schematic";
	}

	@Override public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/schematic <load/test/save/oldsave> <Filename> <x> <y> <z> ([x2] [y2] [z2])";
	}

	@Override public void execute(ICommandSender sender, String[] strs) {
		if (0 < strs.length) {
			if (strs[0].equals("load")) {
				if (strs.length >= 5) {
					load(sender, strs[1], new String[] {strs[2], strs[3], strs[4]} );
				} else if (strs.length == 2) {
					load(sender, strs[1], new String[] {"~0","~0","~0"} );
				} else { missCommand(sender, MISS_LOAD); return; }

			} else if (strs[0].equals("test")) {
				if (strs.length >= 5) {
					test(sender, strs[1], new String[] {strs[2], strs[3], strs[4]} );
				} else if (strs.length == 2) {
					test(sender, strs[1], new String[] {"~0","~0","~0"} );
				} else { missCommand(sender, MISS_TEST); return; }

			} else if (strs[0].equals("save")) {
				if (strs.length >= 7) {
					save(sender, strs[1], new String[] {strs[2], strs[3], strs[4]}, new String[] {strs[5], strs[6], strs[7]}, false);
				} else { missCommand(sender, MISS_SAVE); return; }
			} else if (strs[0].equals("oldsave")) {
				if (strs.length >= 7) {
					save(sender, strs[1], new String[] {strs[2], strs[3], strs[4]}, new String[] {strs[5], strs[6], strs[7]}, true);
				} else { missCommand(sender, MISS_OLDSAVE); return; }

			} else {
				missCommand(sender, MISS_SCHEMATIC); return;
			}
		} else {
			missCommand(sender, MISS_SCHEMATIC); return;
		}

	}

	public void missCommand(ICommandSender sender, int missId) {
		switch (missId) {
		case MISS_SCHEMATIC:
			sender.addChatMessage(new ChatComponentText("[HELP] Mode: load/test"));
			sender.addChatMessage(new ChatComponentText("[HELP] /schematic <Mode> <Filename> [x] [y] [z]"));
			sender.addChatMessage(new ChatComponentText("[HELP] Mode: save"));
			sender.addChatMessage(new ChatComponentText("[HELP] /schematic <Mode> <Filename> <x1> <y1> <z1> <x2> <y2> <z2>"));
			break;

		case MISS_LOAD:
			sender.addChatMessage(new ChatComponentText("[HELP] /schematic load <Filename> [x] [y] [z]"));
			break;

		case MISS_TEST:
			sender.addChatMessage(new ChatComponentText("[HELP] /schematic test <Filename> [x] [y] [z]"));
			break;

		case MISS_SAVE:
			sender.addChatMessage(new ChatComponentText("[HELP] /schematic save <Filename> <x1> <y1> <z1> <x2> <y2> <z2>"));
			break;
		}

	}

	private void load(ICommandSender sender, String filename, String[] xyzplus) {

		try {
			File f = new File(SeikaCreativeMod.SCHEMATICSPATH + "/" + filename + ".schematic");

			if (f.exists()) {
				NBTTagCompound tag = CompressedStreamTools.readCompressed(new FileInputStream(f));
				Schematic sch = Schematic.createFromNBT(tag);

				// Positions
				if (sender.getCommandSenderEntity() == null) { return ; }
				BlockPos pos = sender.getCommandSenderEntity().getPosition();
				int x, y, z;
				x = y = z = 0;

				try {
					x = MathHelper.floor_double(func_175761_b(pos.getX(), xyzplus[0], true));
					y = MathHelper.floor_double(func_175761_b(pos.getY(), xyzplus[1], true));
					z = MathHelper.floor_double(func_175761_b(pos.getZ(), xyzplus[2], true));;
				} catch (NumberInvalidException e) {
					e.printStackTrace(); return ;
				}

				sender.addChatMessage(new ChatComponentText("Building is Starting w: " + sch.width + " h: " + sch.height + " d: " + sch.depth));
				sch.build(sender.getEntityWorld(), new BlockPos(x, y, z));

				sender.addChatMessage(new ChatComponentText("Building has done"));
			}
		} catch (IOException e) { e.printStackTrace(); }

	}

	private void test(ICommandSender sender, String filename, String[] xyzplus) {

		try {
			File f = new File(SeikaCreativeMod.SCHEMATICSPATH + "/" + filename + ".schematic");

			if (f.exists()) {
				NBTTagCompound tag = CompressedStreamTools.readCompressed(new FileInputStream(f));
				Schematic sch = Schematic.createFromNBT(tag);

				// Positions
				if (sender.getCommandSenderEntity() == null) { return ; }
				BlockPos pos = sender.getCommandSenderEntity().getPosition();
				int x, y, z;
				x = y = z = 0;

				try {
					x = MathHelper.floor_double(func_175761_b(pos.getX(), xyzplus[0], true));
					y = MathHelper.floor_double(func_175761_b(pos.getY(), xyzplus[1], true));
					z = MathHelper.floor_double(func_175761_b(pos.getZ(), xyzplus[2], true));;
				} catch (NumberInvalidException e) {
					e.printStackTrace(); return ;
				}

				sender.addChatMessage(new ChatComponentText("Test is Starting w: " + sch.width + " h: " + sch.height + " d: " + sch.depth));

				sch.test(sender.getEntityWorld(), new BlockPos(x, y, z));

				sender.addChatMessage(new ChatComponentText("Test has done"));

			}
		} catch (IOException e) { e.printStackTrace(); }

	}


	private void save(ICommandSender sender, String filename, String[] xyz1, String[] xyz2, boolean old) {

		try {
			File f = new File(SeikaCreativeMod.SCHEMATICSPATH + "/" + filename + ".schematic");

			// Positions
			if (sender.getCommandSenderEntity() == null) { return ; }
			BlockPos pos = sender.getCommandSenderEntity().getPosition();
			int x1, y1, z1;
			x1 = y1 = z1 = 0;
			int x2, y2, z2;
			x2 = y2 = z2 = 0;

			try {
				x1 = MathHelper.floor_double(func_175761_b(pos.getX(), xyz1[0], true));
				y1 = MathHelper.floor_double(func_175761_b(pos.getY(), xyz1[1], true));
				z1 = MathHelper.floor_double(func_175761_b(pos.getZ(), xyz1[2], true));;

				x2 = MathHelper.floor_double(func_175761_b(pos.getX(), xyz2[0], true));
				y2 = MathHelper.floor_double(func_175761_b(pos.getY(), xyz2[1], true));
				z2 = MathHelper.floor_double(func_175761_b(pos.getZ(), xyz2[2], true));;
			} catch (NumberInvalidException e) {
				e.printStackTrace(); return ;
			}

			NBTTagCompound schematic = Schematic.getNBT(sender.getEntityWorld(), x1,y1,z1, x2,y2,z2, old);
			CompressedStreamTools.writeCompressed(schematic, new FileOutputStream(f));

			sender.addChatMessage(new ChatComponentText("Saved schematic: " + f.getName()));

		} catch (IOException e) { e.printStackTrace(); }
	}


}

