package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.potion.PotionEffect;

public class S1EPacketRemoveEntityEffect implements Packet<INetHandlerPlayClient> {
   private int field_149079_a;
   private int field_149078_b;

   public S1EPacketRemoveEntityEffect() {
   }

   public S1EPacketRemoveEntityEffect(int p_i45212_1_, PotionEffect p_i45212_2_) {
      this.field_149079_a = p_i45212_1_;
      this.field_149078_b = p_i45212_2_.func_76456_a();
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149079_a = p_148837_1_.func_150792_a();
      this.field_149078_b = p_148837_1_.readUnsignedByte();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.func_150787_b(this.field_149079_a);
      p_148840_1_.writeByte(this.field_149078_b);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147262_a(this);
   }
}
