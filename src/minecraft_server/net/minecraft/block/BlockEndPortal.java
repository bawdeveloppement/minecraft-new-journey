package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndPortal extends BlockContainer {
   protected BlockEndPortal(Material materialIn) {
      super(materialIn);
      this.setLightLevel(1.0F);
   }

   public TileEntity createNewTileEntity(World worldIn, int meta) {
      return new TileEntityEndPortal();
   }

   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
      float f = 0.0625F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
   }

   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public int quantityDropped(Random random) {
      return 0;
   }

   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
      if(entityIn.ridingEntity == null && entityIn.riddenByEntity == null && !worldIn.isRemote) {
         entityIn.travelToDimension(1);
      }
   }

   public MapColor getMapColor(IBlockState state) {
      return MapColor.blackColor;
   }
}
