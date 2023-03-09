package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockStainedGlassPane extends BlockPane {
   public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color", EnumDyeColor.class);

   public BlockStainedGlassPane() {
      super(Material.glass, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(COLOR, EnumDyeColor.WHITE));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public int damageDropped(IBlockState state) {
      return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
   }

   public MapColor getMapColor(IBlockState state) {
      return ((EnumDyeColor)state.getValue(COLOR)).getMapColor();
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{NORTH, EAST, WEST, SOUTH, COLOR});
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      if(!worldIn.isRemote) {
         BlockBeacon.updateColorAsync(worldIn, pos);
      }
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      if(!worldIn.isRemote) {
         BlockBeacon.updateColorAsync(worldIn, pos);
      }
   }
}
