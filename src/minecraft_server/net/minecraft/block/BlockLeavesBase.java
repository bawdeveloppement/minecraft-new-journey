package net.minecraft.block;

import net.minecraft.block.material.Material;

public class BlockLeavesBase extends Block {
   protected boolean fancyGraphics;

   protected BlockLeavesBase(Material materialIn, boolean fancyGraphics) {
      super(materialIn);
      this.fancyGraphics = fancyGraphics;
   }

   public boolean isOpaqueCube() {
      return false;
   }
}
