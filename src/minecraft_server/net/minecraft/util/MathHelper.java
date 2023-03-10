package net.minecraft.util;

import java.util.Random;
import java.util.UUID;

public class MathHelper {
   public static final float SQRT_2 = sqrt_float(2.0F);
   private static final float[] SIN_TABLE = new float[65536];
   private static final int[] multiplyDeBruijnBitPosition;
   private static final double field_181163_d;
   private static final double[] field_181164_e;
   private static final double[] field_181165_f;

   public static float sin(float p_76126_0_) {
      return SIN_TABLE[(int)(p_76126_0_ * 10430.378F) & 65535];
   }

   public static float cos(float value) {
      return SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
   }

   public static float sqrt_float(float value) {
      return (float)Math.sqrt((double)value);
   }

   public static float sqrt_double(double value) {
      return (float)Math.sqrt(value);
   }

   public static int floor_float(float value) {
      int i = (int)value;
      return value < (float)i?i - 1:i;
   }

   public static int floor_double(double value) {
      int i = (int)value;
      return value < (double)i?i - 1:i;
   }

   public static long floor_double_long(double value) {
      long i = (long)value;
      return value < (double)i?i - 1L:i;
   }

   public static float abs(float value) {
      return value >= 0.0F?value:-value;
   }

   public static int abs_int(int value) {
      return value >= 0?value:-value;
   }

   public static int ceiling_float_int(float value) {
      int i = (int)value;
      return value > (float)i?i + 1:i;
   }

   public static int ceiling_double_int(double value) {
      int i = (int)value;
      return value > (double)i?i + 1:i;
   }

   public static int clamp_int(int num, int min, int max) {
      return num < min?min:(num > max?max:num);
   }

   public static float clamp_float(float num, float min, float max) {
      return num < min?min:(num > max?max:num);
   }

   public static double clamp_double(double num, double min, double max) {
      return num < min?min:(num > max?max:num);
   }

   public static double denormalizeClamp(double p_151238_0_, double p_151238_2_, double p_151238_4_) {
      return p_151238_4_ < 0.0D?p_151238_0_:(p_151238_4_ > 1.0D?p_151238_2_:p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_);
   }

   public static double abs_max(double p_76132_0_, double p_76132_2_) {
      if(p_76132_0_ < 0.0D) {
         p_76132_0_ = -p_76132_0_;
      }

      if(p_76132_2_ < 0.0D) {
         p_76132_2_ = -p_76132_2_;
      }

      return p_76132_0_ > p_76132_2_?p_76132_0_:p_76132_2_;
   }

   public static int getRandomIntegerInRange(Random p_76136_0_, int p_76136_1_, int p_76136_2_) {
      return p_76136_1_ >= p_76136_2_?p_76136_1_:p_76136_0_.nextInt(p_76136_2_ - p_76136_1_ + 1) + p_76136_1_;
   }

   public static float randomFloatClamp(Random p_151240_0_, float p_151240_1_, float p_151240_2_) {
      return p_151240_1_ >= p_151240_2_?p_151240_1_:p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_;
   }

   public static double getRandomDoubleInRange(Random p_82716_0_, double p_82716_1_, double p_82716_3_) {
      return p_82716_1_ >= p_82716_3_?p_82716_1_:p_82716_0_.nextDouble() * (p_82716_3_ - p_82716_1_) + p_82716_1_;
   }

   public static double average(long[] values) {
      long i = 0L;

      for(long j : values) {
         i += j;
      }

      return (double)i / (double)values.length;
   }

   public static float wrapAngleTo180_float(float value) {
      value = value % 360.0F;
      if(value >= 180.0F) {
         value -= 360.0F;
      }

      if(value < -180.0F) {
         value += 360.0F;
      }

      return value;
   }

   public static double wrapAngleTo180_double(double value) {
      value = value % 360.0D;
      if(value >= 180.0D) {
         value -= 360.0D;
      }

      if(value < -180.0D) {
         value += 360.0D;
      }

      return value;
   }

   public static int parseIntWithDefault(String p_82715_0_, int p_82715_1_) {
      try {
         return Integer.parseInt(p_82715_0_);
      } catch (Throwable var3) {
         return p_82715_1_;
      }
   }

   public static int parseIntWithDefaultAndMax(String p_82714_0_, int p_82714_1_, int p_82714_2_) {
      return Math.max(p_82714_2_, parseIntWithDefault(p_82714_0_, p_82714_1_));
   }

   public static double parseDoubleWithDefault(String p_82712_0_, double p_82712_1_) {
      try {
         return Double.parseDouble(p_82712_0_);
      } catch (Throwable var4) {
         return p_82712_1_;
      }
   }

   public static double parseDoubleWithDefaultAndMax(String p_82713_0_, double p_82713_1_, double p_82713_3_) {
      return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_));
   }

   public static int roundUpToPowerOfTwo(int value) {
      int i = value - 1;
      i = i | i >> 1;
      i = i | i >> 2;
      i = i | i >> 4;
      i = i | i >> 8;
      i = i | i >> 16;
      return i + 1;
   }

   private static boolean isPowerOfTwo(int value) {
      return value != 0 && (value & value - 1) == 0;
   }

   private static int calculateLogBaseTwoDeBruijn(int value) {
      value = isPowerOfTwo(value)?value:roundUpToPowerOfTwo(value);
      return multiplyDeBruijnBitPosition[(int)((long)value * 125613361L >> 27) & 31];
   }

   public static int calculateLogBaseTwo(int value) {
      return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value)?0:1);
   }

   public static int func_154354_b(int p_154354_0_, int p_154354_1_) {
      if(p_154354_1_ == 0) {
         return 0;
      } else if(p_154354_0_ == 0) {
         return p_154354_1_;
      } else {
         if(p_154354_0_ < 0) {
            p_154354_1_ *= -1;
         }

         int i = p_154354_0_ % p_154354_1_;
         return i == 0?p_154354_0_:p_154354_0_ + p_154354_1_ - i;
      }
   }

   public static UUID getRandomUuid(Random rand) {
      long i = rand.nextLong() & -61441L | 16384L;
      long j = rand.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(i, j);
   }

   public static double func_181160_c(double p_181160_0_, double p_181160_2_, double p_181160_4_) {
      return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
   }

   public static double func_181159_b(double p_181159_0_, double p_181159_2_) {
      double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;
      if(Double.isNaN(d0)) {
         return Double.NaN;
      } else {
         boolean flag = p_181159_0_ < 0.0D;
         if(flag) {
            p_181159_0_ = -p_181159_0_;
         }

         boolean flag1 = p_181159_2_ < 0.0D;
         if(flag1) {
            p_181159_2_ = -p_181159_2_;
         }

         boolean flag2 = p_181159_0_ > p_181159_2_;
         if(flag2) {
            double d1 = p_181159_2_;
            p_181159_2_ = p_181159_0_;
            p_181159_0_ = d1;
         }

         double d9 = func_181161_i(d0);
         p_181159_2_ = p_181159_2_ * d9;
         p_181159_0_ = p_181159_0_ * d9;
         double d2 = field_181163_d + p_181159_0_;
         int i = (int)Double.doubleToRawLongBits(d2);
         double d3 = field_181164_e[i];
         double d4 = field_181165_f[i];
         double d5 = d2 - field_181163_d;
         double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
         double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
         double d8 = d3 + d7;
         if(flag2) {
            d8 = (Math.PI / 2D) - d8;
         }

         if(flag1) {
            d8 = Math.PI - d8;
         }

         if(flag) {
            d8 = -d8;
         }

         return d8;
      }
   }

   public static double func_181161_i(double p_181161_0_) {
      double d0 = 0.5D * p_181161_0_;
      long i = Double.doubleToRawLongBits(p_181161_0_);
      i = 6910469410427058090L - (i >> 1);
      p_181161_0_ = Double.longBitsToDouble(i);
      p_181161_0_ = p_181161_0_ * (1.5D - d0 * p_181161_0_ * p_181161_0_);
      return p_181161_0_;
   }

   static {
      for(int i = 0; i < 65536; ++i) {
         SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
      }

      multiplyDeBruijnBitPosition = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
      field_181163_d = Double.longBitsToDouble(4805340802404319232L);
      field_181164_e = new double[257];
      field_181165_f = new double[257];

      for(int j = 0; j < 257; ++j) {
         double d0 = (double)j / 256.0D;
         double d1 = Math.asin(d0);
         field_181165_f[j] = Math.cos(d1);
         field_181164_e[j] = d1;
      }
   }
}
