package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

public class S41PacketServerDifficulty implements Packet<INetHandlerPlayClient> {
   private EnumDifficulty field_179833_a;
   private boolean field_179832_b;

   public S41PacketServerDifficulty() {
   }

   public S41PacketServerDifficulty(EnumDifficulty p_i45987_1_, boolean p_i45987_2_) {
      this.field_179833_a = p_i45987_1_;
      this.field_179832_b = p_i45987_2_;
   }

   public void func_148833_a(INetHandlerPlayClient p_148833_1_) {
      p_148833_1_.func_175101_a(this);
   }

   public void func_148837_a(PacketBuffer p_148837_1_) throws IOException {
      this.field_179833_a = EnumDifficulty.func_151523_a(p_148837_1_.readUnsignedByte());
   }

   public void func_148840_b(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeByte(this.field_179833_a.func_151525_a());
   }
}
