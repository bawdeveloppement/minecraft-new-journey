package net.minecraft.block;

import java.util.Random;
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

public class BlockStainedGlass extends BlockBreakable {
   public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.<EnumDyeColor>create("color", EnumDyeColor.class);

   public BlockStainedGlass(Material materialIn) {
      super(materialIn, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState state) {
      return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
   }

   public MapColor getMapColor(IBlockState state) {
      return ((EnumDyeColor)state.getValue(COLOR)).getMapColor();
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   protected boolean canSilkHarvest() {
      return true;
   }

   public boolean isFullCube() {
      return false;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
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

   public int getMetaFromState(IBlockState state) {
      return ((EnumDyeColor)state.getValue(COLOR)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{COLOR});
   }
}
