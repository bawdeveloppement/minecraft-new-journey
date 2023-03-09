package net.minecraft.world.storage;

import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
   ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata);

   void flushCache();

   boolean deleteWorldDirectory(String p_75802_1_);

   boolean isOldMapFormat(String saveName);

   boolean convertMapFormat(String filename, IProgressUpdate progressCallback);
}
