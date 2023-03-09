package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C19PacketResourcePackStatus implements Packet<INetHandlerPlayServer> {
   private String hash;
   private C19PacketResourcePackStatus.Action status;

   public void readPacketData(PacketBuffer buf) throws IOException {
      this.hash = buf.readStringFromBuffer(40);
      this.status = (C19PacketResourcePackStatus.Action)buf.readEnumValue(C19PacketResourcePackStatus.Action.class);
   }

   public void writePacketData(PacketBuffer buf) throws IOException {
      buf.writeString(this.hash);
      buf.writeEnumValue(this.status);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.handleResourcePackStatus(this);
   }

   public static enum Action {
      SUCCESSFULLY_LOADED,
      DECLINED,
      FAILED_DOWNLOAD,
      ACCEPTED;
   }
}
