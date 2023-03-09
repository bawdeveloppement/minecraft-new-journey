package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;

public class ItemCoal extends Item {
   public ItemCoal() {
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   public String getUnlocalizedName(ItemStack stack) {
      return stack.getMetadata() == 1?"item.charcoal":"item.coal";
   }
}
