package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C15PacketClientSettings implements Packet<INetHandlerPlayServer> {
   private String lang;
   private int view;
   private EntityPlayer.EnumChatVisibility chatVisibility;
   private boolean enableColors;
   private int modelPartFlags;

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.lang = buf.readStringFromBuffer(7);
      this.view = buf.readByte();
      this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(buf.readByte());
      this.enableColors = buf.readBoolean();
      this.modelPartFlags = buf.readUnsignedByte();
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeString(this.lang);
      buf.writeByte(this.view);
      buf.writeByte(this.chatVisibility.getChatVisibility());
      buf.writeBoolean(this.enableColors);
      buf.writeByte(this.modelPartFlags);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processClientSettings(this);
   }

   public String getLang() {
      return this.lang;
   }

   public EntityPlayer.EnumChatVisibility getChatVisibility() {
      return this.chatVisibility;
   }

   public boolean isColorsEnabled() {
      return this.enableColors;
   }

   public int getModelPartFlags() {
      return this.modelPartFlags;
   }
}
