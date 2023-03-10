package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S28PacketEffect implements Packet<INetHandlerPlayClient> {
   private int field_149251_a;
   private BlockPos field_179747_b;
   private int field_149249_b;
   private boolean field_149246_f;

   public S28PacketEffect() {
   }

   public S28PacketEffect(int p_i45978_1_, BlockPos p_i45978_2_, int p_i45978_3_, boolean p_i45978_4_) {
      this.field_149251_a = p_i45978_1_;
      this.field_179747_b = p_i45978_2_;
      this.field_149249_b = p_i45978_3_;
      this.field_149246_f = p_i45978_4_;
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_149251_a = p_148837_1_.readInt();
      this.field_179747_b = p_148837_1_.func_179259_c();
      this.field_149249_b = p_148837_1_.readInt();
      this.field_149246_f = p_148837_1_.readBoolean();
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.field_149251_a);
      p_148840_1_.func_179255_a(this.field_179747_b);
      p_148840_1_.writeInt(this.field_149249_b);
      p_148840_1_.writeBoolean(this.field_149246_f);
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_147277_a(this);
   }
}
