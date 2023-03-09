package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S13PacketDestroyEntities implements Packet<INetHandlerPlayClient> {
   private int[] field_149100_a;

   public S13PacketDestroyEntities() {
   }

   public S13PacketDestroyEntities(int... p_i45211_1_) {
      this.field_149100_a = p_i45211_1_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149100_a = new int[p_148837_1_.func_150792_a()];

      for(int i = 0; i < this.field_149100_a.length; ++i) {
         this.field_149100_a[i] = p_148837_1_.func_150792_a();
      }

   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149100_a.length);

      for(int i = 0; i < this.field_149100_a.length; ++i) {
         p_148840_1_.func_150787_b(this.field_149100_a[i]);
      }

   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147238_a(this);
   }
}
