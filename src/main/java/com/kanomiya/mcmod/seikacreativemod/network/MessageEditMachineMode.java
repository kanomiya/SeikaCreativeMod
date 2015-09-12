package com.kanomiya.mcmod.seikacreativemod.network;

import com.kanomiya.mcmod.seikacreativemod.tileentity.TileEntityEditMachine;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageEditMachineMode implements IMessage {
	public int mode;
	public BlockPos pos;

	public MessageEditMachineMode() { }

	public MessageEditMachineMode(BlockPos pos, int mode) {
		this.pos = pos;
		this.mode = mode;

	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();

		pos = new BlockPos(x, y, z);
		mode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());

		buf.writeInt(mode);
	}


	public static class Server implements IMessageHandler<MessageEditMachineMode, IMessage> {

		@Override
		public IMessage onMessage(MessageEditMachineMode message, MessageContext ctx) {
			if (ctx.side.isServer()) {
				World world = ctx.getServerHandler().playerEntity.worldObj;

				TileEntity tileEntity = world.getTileEntity(message.pos);

				if (tileEntity != null && tileEntity instanceof TileEntityEditMachine) {
					TileEntityEditMachine editMachine = (TileEntityEditMachine) tileEntity;

					editMachine.setMode(message.mode);
				}
			}

			return null;
		}

	}

}
