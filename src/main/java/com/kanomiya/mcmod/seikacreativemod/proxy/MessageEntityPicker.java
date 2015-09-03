package com.kanomiya.mcmod.seikacreativemod.proxy;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageEntityPicker implements IMessage {
	public int slot;
	public ItemStack stack;

	public MessageEntityPicker() { }

	public MessageEntityPicker(int slot, ItemStack stack) {
		this.slot = slot;
		this.stack = stack;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slot = buf.readInt();
		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(slot);
		ByteBufUtils.writeItemStack(buf, stack);
	}


	public static class Server implements IMessageHandler<MessageEntityPicker, IMessage> {

		@Override
		public IMessage onMessage(MessageEntityPicker message, MessageContext ctx) {
			if (ctx.side.isServer()) {
				EntityPlayerMP player = ctx.getServerHandler().playerEntity;

				player.inventory.setInventorySlotContents(message.slot, message.stack);
			}

			return null;
		}

	}

}
