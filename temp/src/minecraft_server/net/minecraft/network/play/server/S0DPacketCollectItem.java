package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S0DPacketCollectItem implements Packet<INetHandlerPlayClient> {
   private int field_149357_a;
   private int field_149356_b;

   public S0DPacketCollectItem() {
   }

   public S0DPacketCollectItem(int p_i45232_1_, int p_i45232_2_) {
      this.field_149357_a = p_i45232_1_;
      this.field_149356_b = p_i45232_2_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149357_a = p_148837_1_.func_150792_a();
      this.field_149356_b = p_148837_1_.func_150792_a();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149357_a);
      p_148840_1_.func_150787_b(this.field_149356_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147246_a(this);
   }
}
