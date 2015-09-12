package com.kanomiya.mcmod.seikacreativemod.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("SeikaCreativeMod");

	public static void init() {
		INSTANCE.registerMessage(MessageEditMachineMode.Server.class, MessageEditMachineMode.class, 0, Side.SERVER);
		INSTANCE.registerMessage(MessageEntityPicker.Server.class, MessageEntityPicker.class, 1, Side.SERVER);
	}

}
