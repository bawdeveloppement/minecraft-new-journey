package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
   private final boolean isBurning;
   private static boolean keepInventory;

   protected BlockFurnace(boolean isBurning) {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
      this.isBurning = isBurning;
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Item.getItemFromBlock(Blocks.furnace);
   }

   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
      this.setDefaultFacing(worldIn, pos, state);
   }

   private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
      if(!worldIn.isRemote) {
         Block block = worldIn.getBlockState(pos.north()).getBlock();
         Block block1 = worldIn.getBlockState(pos.south()).getBlock();
         Block block2 = worldIn.getBlockState(pos.west()).getBlock();
         Block block3 = worldIn.getBlockState(pos.east()).getBlock();
         EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
         if(enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()) {
            enumfacing = EnumFacing.SOUTH;
         } else if(enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()) {
            enumfacing = EnumFacing.NORTH;
         } else if(enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()) {
            enumfacing = EnumFacing.EAST;
         } else if(enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()) {
            enumfacing = EnumFacing.WEST;
         }

         worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
      }
   }

   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
      if(worldIn.isRemote) {
         return true;
      } else {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if(tileentity instanceof TileEntityFurnace) {
            playerIn.displayGUIChest((TileEntityFurnace)tileentity);
            playerIn.triggerAchievement(StatList.field_181741_Y);
         }

         return true;
      }
   }

   public static void setState(boolean active, World worldIn, BlockPos pos) {
      IBlockState iblockstate = worldIn.getBlockState(pos);
      TileEntity tileentity = worldIn.getTileEntity(pos);
      keepInventory = true;
      if(active) {
         worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
         worldIn.setBlockState(pos, Blocks.lit_furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      } else {
         worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
         worldIn.setBlockState(pos, Blocks.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
      }

      keepInventory = false;
      if(tileentity != null) {
         tileentity.validate();
         worldIn.setTileEntity(pos, tileentity);
      }
   }

   public TileEntity createNewTileEntity(World worldIn, int meta) {
      return new TileEntityFurnace();
   }

   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
   }

   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
      worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
      if(stack.hasDisplayName()) {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if(tileentity instanceof TileEntityFurnace) {
            ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
         }
      }
   }

   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
      if(!keepInventory) {
         TileEntity tileentity = worldIn.getTileEntity(pos);
         if(tileentity instanceof TileEntityFurnace) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
         }
      }

      super.breakBlock(worldIn, pos, state);
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
      return Container.calcRedstone(worldIn.getTileEntity(pos));
   }

   public int getRenderType() {
      return 3;
   }

   public IBlockState getStateFromMeta(int meta) {
      EnumFacing enumfacing = EnumFacing.getFront(meta);
      if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
         enumfacing = EnumFacing.NORTH;
      }

      return this.getDefaultState().withProperty(FACING, enumfacing);
   }

   public int getMetaFromState(IBlockState state) {
      return ((EnumFacing)state.getValue(FACING)).getIndex();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING});
   }
}
