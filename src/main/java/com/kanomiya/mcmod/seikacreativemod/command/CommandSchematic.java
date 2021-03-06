package com.kanomiya.mcmod.seikacreativemod.command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kanomiya.mcmod.seikacreativemod.SeikaCreativeMod;
import com.kanomiya.mcmod.seikacreativemod.schematic.Schematic;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class CommandSchematic extends CommandBase {
	protected static final int MISS_SCHEMATIC = 0;
	protected static final int MISS_LOAD = 1;
	protected static final int MISS_TEST = 2;
	protected static final int MISS_SAVE = 3;
	protected static final int MISS_OLDSAVE = 4;

	@Override public String getCommandName() {
		return "schematic";
	}

	@Override public String getCommandUsage(ICommandSender p_71518_1_) {
		return "/schematic <load/test/save/oldsave> <Filename> <x> <y> <z> ([x2] [y2] [z2])";
	}

	@Override public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{
		if (0 < args.length) {
			if (args[0].equals("load")) {
				if (args.length >= 5) {
					load(sender, args[1], new String[] {args[2], args[3], args[4]} );
				} else if (args.length == 2) {
					load(sender, args[1], new String[] {"~0","~0","~0"} );
				} else { missCommand(sender, MISS_LOAD); return; }

			} else if (args[0].equals("test")) {
				if (args.length >= 5) {
					test(sender, args[1], new String[] {args[2], args[3], args[4]} );
				} else if (args.length == 2) {
					test(sender, args[1], new String[] {"~0","~0","~0"} );
				} else { missCommand(sender, MISS_TEST); return; }

			} else if (args[0].equals("save")) {
				if (args.length >= 7) {
					save(sender, args[1], new String[] {args[2], args[3], args[4]}, new String[] {args[5], args[6], args[7]}, false);
				} else { missCommand(sender, MISS_SAVE); return; }
			} else if (args[0].equals("oldsave")) {
				if (args.length >= 7) {
					save(sender, args[1], new String[] {args[2], args[3], args[4]}, new String[] {args[5], args[6], args[7]}, true);
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
			sender.addChatMessage(new TextComponentString("[HELP] Mode: load/test"));
			sender.addChatMessage(new TextComponentString("[HELP] /schematic <Mode> <Filename> [x] [y] [z]"));
			sender.addChatMessage(new TextComponentString("[HELP] Mode: save/oldsave"));
			sender.addChatMessage(new TextComponentString("[HELP] /schematic <Mode> <Filename> <x1> <y1> <z1> <x2> <y2> <z2>"));
			break;

		case MISS_LOAD:
			sender.addChatMessage(new TextComponentString("[HELP] /schematic load <Filename> [x] [y] [z]"));
			break;

		case MISS_TEST:
			sender.addChatMessage(new TextComponentString("[HELP] /schematic test <Filename> [x] [y] [z]"));
			break;

		case MISS_SAVE:
			sender.addChatMessage(new TextComponentString("[HELP] /schematic save <Filename> <x1> <y1> <z1> <x2> <y2> <z2>"));
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
				double x, y, z;
				x = y = z = 0;

				try {
					x = parseDouble(pos.getX(), xyzplus[0], true);
					y = parseDouble(pos.getY(), xyzplus[1], false);
					z = parseDouble(pos.getZ(), xyzplus[2], true);
				} catch (NumberInvalidException e) {
					e.printStackTrace(); return ;
				}

				sender.addChatMessage(new TextComponentString("Building is Starting w: " + sch.width + " h: " + sch.height + " d: " + sch.depth));
				sch.build(sender.getEntityWorld(), new BlockPos(x, y, z));

				sender.addChatMessage(new TextComponentString("Building has done"));
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
				double x, y, z;
				x = y = z = 0;

				try {
					x = parseDouble(pos.getX(), xyzplus[0], true);
					y = parseDouble(pos.getY(), xyzplus[1], false);
					z = parseDouble(pos.getZ(), xyzplus[2], true);
				} catch (NumberInvalidException e) {
					e.printStackTrace(); return ;
				}

				sender.addChatMessage(new TextComponentString("Test is Starting w: " + sch.width + " h: " + sch.height + " d: " + sch.depth));

				sch.test(sender.getEntityWorld(), new BlockPos(x, y, z));

				sender.addChatMessage(new TextComponentString("Test has done"));

			}
		} catch (IOException e) { e.printStackTrace(); }

	}


	private void save(ICommandSender sender, String filename, String[] xyz1, String[] xyz2, boolean old) {

		try {
			File f = new File(SeikaCreativeMod.SCHEMATICSPATH + "/" + filename + ".schematic");

			// Positions
			if (sender.getCommandSenderEntity() == null) { return ; }
			BlockPos pos = sender.getCommandSenderEntity().getPosition();
			double x1, y1, z1;
			x1 = y1 = z1 = 0;

			double x2, y2, z2;
			x2 = y2 = z2 = 0;

			try {
				x1 = parseDouble(pos.getX(), xyz1[0], true);
				y1 = parseDouble(pos.getY(), xyz1[1], false);
				z1 = parseDouble(pos.getZ(), xyz1[2], true);

				x2 = parseDouble(pos.getX(), xyz2[0], true);
				y2 = parseDouble(pos.getY(), xyz2[1], false);
				z2 = parseDouble(pos.getZ(), xyz2[2], true);

			} catch (NumberInvalidException e) {
				e.printStackTrace(); return ;
			}

			NBTTagCompound schematic = Schematic.getNBT(sender.getEntityWorld(), new AxisAlignedBB(x1,y1,z1, x2,y2,z2), old);
			CompressedStreamTools.writeCompressed(schematic, new FileOutputStream(f));

			sender.addChatMessage(new TextComponentString("Saved schematic: " + f.getName()));

		} catch (IOException e) { e.printStackTrace(); }
	}


}

