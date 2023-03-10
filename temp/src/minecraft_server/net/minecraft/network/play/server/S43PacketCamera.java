package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S43PacketCamera implements Packet<INetHandlerPlayClient> {
   public int field_179781_a;

   public S43PacketCamera() {
   }

   public S43PacketCamera(Entity p_i45960_1_) {
      this.field_179781_a = p_i45960_1_.func_145782_y();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179781_a = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_179781_a);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175094_a(this);
   }
}
