package net.minecraft.inventory;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerRepair extends Container {
   private static final Logger logger = LogManager.getLogger();
   private IInventory outputSlot;
   private IInventory inputSlots;
   private World theWorld;
   private BlockPos selfPosition;
   public int maximumCost;
   private int materialCost;
   private String repairedItemName;
   private final EntityPlayer thePlayer;

   public ContainerRepair(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {
      this(playerInventory, worldIn, BlockPos.ORIGIN, player);
   }

   public ContainerRepair(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn, EntityPlayer player) {
      this.outputSlot = new InventoryCraftResult();
      this.inputSlots = new InventoryBasic("Repair", true, 2) {
         public void markDirty() {
            super.markDirty();
            ContainerRepair.this.onCraftMatrixChanged(this);
         }
      };
      this.selfPosition = blockPosIn;
      this.theWorld = worldIn;
      this.thePlayer = player;
      this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
      this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
      this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47) {
         public boolean isItemValid(ItemStack stack) {
            return false;
         }

         public boolean canTakeStack(EntityPlayer playerIn) {
            return (playerIn.capabilities.isCreativeMode || playerIn.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && this.getHasStack();
         }

         public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
            if(!playerIn.capabilities.isCreativeMode) {
               playerIn.addExperienceLevel(-ContainerRepair.this.maximumCost);
            }

            ContainerRepair.this.inputSlots.setInventorySlotContents(0, (ItemStack)null);
            if(ContainerRepair.this.materialCost > 0) {
               ItemStack itemstack = ContainerRepair.this.inputSlots.getStackInSlot(1);
               if(itemstack != null && itemstack.stackSize > ContainerRepair.this.materialCost) {
                  itemstack.stackSize -= ContainerRepair.this.materialCost;
                  ContainerRepair.this.inputSlots.setInventorySlotContents(1, itemstack);
               } else {
                  ContainerRepair.this.inputSlots.setInventorySlotContents(1, (ItemStack)null);
               }
            } else {
               ContainerRepair.this.inputSlots.setInventorySlotContents(1, (ItemStack)null);
            }

            ContainerRepair.this.maximumCost = 0;
            IBlockState iblockstate = worldIn.getBlockState(blockPosIn);
            if(!playerIn.capabilities.isCreativeMode && !worldIn.isRemote && iblockstate.getBlock() == Blocks.anvil && playerIn.getRNG().nextFloat() < 0.12F) {
               int l = ((Integer)iblockstate.getValue(BlockAnvil.DAMAGE)).intValue();
               ++l;
               if(l > 2) {
                  worldIn.setBlockToAir(blockPosIn);
                  worldIn.playAuxSFX(1020, blockPosIn, 0);
               } else {
                  worldIn.setBlockState(blockPosIn, iblockstate.withProperty(BlockAnvil.DAMAGE, Integer.valueOf(l)), 2);
                  worldIn.playAuxSFX(1021, blockPosIn, 0);
               }
            } else if(!worldIn.isRemote) {
               worldIn.playAuxSFX(1021, blockPosIn, 0);
            }
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
      }
   }

   public void onCraftMatrixChanged(IInventory inventoryIn) {
      super.onCraftMatrixChanged(inventoryIn);
      if(inventoryIn == this.inputSlots) {
         this.updateRepairOutput();
      }
   }

   public void updateRepairOutput() {
      int i = 0;
      int j = 1;
      int k = 1;
      int l = 1;
      int i1 = 2;
      int j1 = 1;
      int k1 = 1;
      ItemStack inputstack = this.inputSlots.getStackInSlot(0);
      this.maximumCost = 1;
      int l1 = 0;
      int i2 = 0;
      int j2 = 0;
      if(inputstack == null) {
         this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
         this.maximumCost = 0;
      } else {
         ItemStack outputStack = inputstack.copy();
         ItemStack materialStack = this.inputSlots.getStackInSlot(1);
         Map<Integer, Integer> map = EnchantmentHelper.getEnchantments(outputStack);
         boolean flag = false;
         i2 = i2 + inputstack.getRepairCost() + (materialStack == null?0:materialStack.getRepairCost());
         this.materialCost = 0;
         if(materialStack != null) {
            flag = materialStack.getItem() == Items.enchanted_book && Items.enchanted_book.getEnchantments(materialStack).tagCount() > 0;
            if(outputStack.isItemStackDamageable() && outputStack.getItem().getIsRepairable(inputstack, materialStack)) {
               int j4 = Math.min(outputStack.getItemDamage(), outputStack.getMaxDamage() / 4);
               if(j4 <= 0) {
                  this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
                  this.maximumCost = 0;
                  return;
               }

               int l4;
               for(l4 = 0; j4 > 0 && l4 < materialStack.stackSize; ++l4) {
                  int j5 = outputStack.getItemDamage() - j4;
                  outputStack.setItemDamage(j5);
                  ++l1;
                  j4 = Math.min(outputStack.getItemDamage(), outputStack.getMaxDamage() / 4);
               }

               this.materialCost = l4;
            } else {
               if(!flag && (outputStack.getItem() != materialStack.getItem() || !outputStack.isItemStackDamageable())) {
                  this.outputSlot.setInventorySlotContents(0, (ItemStack)null);
                  this.maximumCost = 0;
                  return;
               }

               if(outputStack.isItemStackDamageable() && !flag) {
                  int k2 = inputstack.getMaxDamage() - inputstack.getItemDamage();
                  int l2 = materialStack.getMaxDamage() - materialStack.getItemDamage();
                  int i3 = l2 + outputStack.getMaxDamage() * 12 / 100;
                  int j3 = k2 + i3;
                  int k3 = outputStack.getMaxDamage() - j3;
                  if(k3 < 0) {
                     k3 = 0;
                  }

                  if(k3 < outputStack.getMetadata()) {
                	  outputStack.setItemDamage(k3);
                     l1 += 2;
                  }
               }

               Map<Integer, Integer> map1 = EnchantmentHelper.getEnchantments(materialStack);
               Iterator iterator1 = map1.keySet().iterator();

               while(iterator1.hasNext()) {
                  int i5 = ((Integer)iterator1.next()).intValue();
                  Enchantment enchantment = Enchantment.getEnchantmentById(i5);
                  if(enchantment != null) {
                     int k5 = map.containsKey(Integer.valueOf(i5))?((Integer)map.get(Integer.valueOf(i5))).intValue():0;
                     int l3 = ((Integer)map1.get(Integer.valueOf(i5))).intValue();
                     int i6;
                     if(k5 == l3) {
                        ++l3;
                        i6 = l3;
                     } else {
                        i6 = Math.max(l3, k5);
                     }

                     l3 = i6;
                     boolean flag1 = enchantment.canApply(inputstack);
                     if(this.thePlayer.capabilities.isCreativeMode || inputstack.getItem() == Items.enchanted_book) {
                        flag1 = true;
                     }

                     Iterator iterator = map.keySet().iterator();

                     while(iterator.hasNext()) {
                        int i4 = ((Integer)iterator.next()).intValue();
                        if(i4 != i5 && !enchantment.canApplyTogether(Enchantment.getEnchantmentById(i4))) {
                           flag1 = false;
                           ++l1;
                        }
                     }

                     if(flag1) {
                        if(l3 > enchantment.getMaxLevel()) {
                           l3 = enchantment.getMaxLevel();
                        }

                        map.put(Integer.valueOf(i5), Integer.valueOf(l3));
                        int l5 = 0;
                        switch(enchantment.getWeight()) {
                        case 1:
                           l5 = 8;
                           break;
                        case 2:
                           l5 = 4;
                        case 3:
                        case 4:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        default:
                           break;
                        case 5:
                           l5 = 2;
                           break;
                        case 10:
                           l5 = 1;
                        }

                        if(flag) {
                           l5 = Math.max(1, l5 / 2);
                        }

                        l1 += l5 * l3;
                     }
                  }
               }
            }
         }

         if(StringUtils.isBlank(this.repairedItemName)) {
            if(inputstack.hasDisplayName()) {
               j2 = 1;
               l1 += j2;
               outputStack.clearCustomName();
            }
         } else if(!this.repairedItemName.equals(inputstack.getDisplayName())) {
            j2 = 1;
            l1 += j2;
            outputStack.setStackDisplayName(this.repairedItemName);
         }

         this.maximumCost = i2 + l1;
         if(l1 <= 0) {
        	 outputStack = null;
         }

         if(j2 == l1 && j2 > 0 && this.maximumCost >= 40) {
            this.maximumCost = 39;
         }

         if(this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
        	 outputStack = null;
         }

         if(outputStack != null) {
            int k4 = outputStack.getRepairCost();
            if(materialStack != null && k4 < materialStack.getRepairCost()) {
               k4 = materialStack.getRepairCost();
            }

            k4 = k4 * 2 + 1;
            outputStack.setRepairCost(k4);
            EnchantmentHelper.setEnchantments(map, outputStack);
         }

         this.outputSlot.setInventorySlotContents(0, outputStack);
         this.detectAndSendChanges();
      }
   }

   public void onCraftGuiOpened(ICrafting listener) {
      super.onCraftGuiOpened(listener);
      listener.sendProgressBarUpdate(this, 0, this.maximumCost);
   }

   public void updateProgressBar(int id, int data) {
      if(id == 0) {
         this.maximumCost = data;
      }
   }

   public void onContainerClosed(EntityPlayer playerIn) {
      super.onContainerClosed(playerIn);
      if(!this.theWorld.isRemote) {
         for(int i = 0; i < this.inputSlots.getSizeInventory(); ++i) {
            ItemStack itemstack = this.inputSlots.removeStackFromSlot(i);
            if(itemstack != null) {
               playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
         }
      }
   }

   public boolean canInteractWith(EntityPlayer playerIn) {
      return this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.anvil?false:playerIn.getDistanceSq((double)this.selfPosition.getX() + 0.5D, (double)this.selfPosition.getY() + 0.5D, (double)this.selfPosition.getZ() + 0.5D) <= 64.0D;
   }

   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.inventorySlots.get(index);
      if(slot != null && slot.getHasStack()) {
         ItemStack itemstack1 = slot.getStack();
         itemstack = itemstack1.copy();
         if(index == 2) {
            if(!this.mergeItemStack(itemstack1, 3, 39, true)) {
               return null;
            }

            slot.onSlotChange(itemstack1, itemstack);
         } else if(index != 0 && index != 1) {
            if(index >= 3 && index < 39 && !this.mergeItemStack(itemstack1, 0, 2, false)) {
               return null;
            }
         } else if(!this.mergeItemStack(itemstack1, 3, 39, false)) {
            return null;
         }

         if(itemstack1.stackSize == 0) {
            slot.putStack((ItemStack)null);
         } else {
            slot.onSlotChanged();
         }

         if(itemstack1.stackSize == itemstack.stackSize) {
            return null;
         }

         slot.onPickupFromSlot(playerIn, itemstack1);
      }

      return itemstack;
   }

   public void updateItemName(String newName) {
      this.repairedItemName = newName;
      if(this.getSlot(2).getHasStack()) {
         ItemStack itemstack = this.getSlot(2).getStack();
         if(StringUtils.isBlank(newName)) {
            itemstack.clearCustomName();
         } else {
            itemstack.setStackDisplayName(this.repairedItemName);
         }
      }

      this.updateRepairOutput();
   }
}
