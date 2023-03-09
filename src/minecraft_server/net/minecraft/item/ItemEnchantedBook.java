package net.minecraft.item;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemEnchantedBook extends Item {
   public boolean isItemTool(ItemStack stack) {
      return false;
   }

   public EnumRarity getRarity(ItemStack stack) {
      return this.getEnchantments(stack).tagCount() > 0?EnumRarity.UNCOMMON:super.getRarity(stack);
   }

   public NBTTagList getEnchantments(ItemStack stack) {
      NBTTagCompound nbttagcompound = stack.getTagCompound();
      return nbttagcompound != null && nbttagcompound.hasKey("StoredEnchantments", 9)?(NBTTagList)nbttagcompound.getTag("StoredEnchantments"):new NBTTagList();
   }

   public void addEnchantment(ItemStack stack, EnchantmentData enchantment) {
      NBTTagList nbttaglist = this.getEnchantments(stack);
      boolean flag = true;

      for(int i = 0; i < nbttaglist.tagCount(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
         if(nbttagcompound.getShort("id") == enchantment.enchantmentobj.effectId) {
            if(nbttagcompound.getShort("lvl") < enchantment.enchantmentLevel) {
               nbttagcompound.setShort("lvl", (short)enchantment.enchantmentLevel);
            }

            flag = false;
            break;
         }
      }

      if(flag) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.setShort("id", (short)enchantment.enchantmentobj.effectId);
         nbttagcompound1.setShort("lvl", (short)enchantment.enchantmentLevel);
         nbttaglist.appendTag(nbttagcompound1);
      }

      if(!stack.hasTagCompound()) {
         stack.setTagCompound(new NBTTagCompound());
      }

      stack.getTagCompound().setTag("StoredEnchantments", nbttaglist);
   }

   public ItemStack getEnchantedItemStack(EnchantmentData data) {
      ItemStack itemstack = new ItemStack(this);
      this.addEnchantment(itemstack, data);
      return itemstack;
   }

   public WeightedRandomChestContent getRandom(Random rand) {
      return this.getRandom(rand, 1, 1, 1);
   }

   public WeightedRandomChestContent getRandom(Random rand, int minChance, int maxChance, int weight) {
      ItemStack itemstack = new ItemStack(Items.book, 1, 0);
      EnchantmentHelper.addRandomEnchantment(rand, itemstack, 30);
      return new WeightedRandomChestContent(itemstack, minChance, maxChance, weight);
   }
}
