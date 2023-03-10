package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static boolean isNullOrEmpty(String string) {
      return org.apache.commons.lang3.StringUtils.isEmpty(string);
   }
}
