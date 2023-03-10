package net.minecraft.server.dedicated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Properties serverProperties = new Properties();
   private final File serverPropertiesFile;

   public PropertyManager(File propertiesFile) {
      this.serverPropertiesFile = propertiesFile;
      if(propertiesFile.exists()) {
         FileInputStream fileinputstream = null;

         try {
            fileinputstream = new FileInputStream(propertiesFile);
            this.serverProperties.load((InputStream)fileinputstream);
         } catch (Exception exception) {
            LOGGER.warn((String)("Failed to load " + propertiesFile), (Throwable)exception);
            this.generateNewProperties();
         } finally {
            if(fileinputstream != null) {
               try {
                  fileinputstream.close();
               } catch (IOException var11) {
                  ;
               }
            }
         }
      } else {
         LOGGER.warn(propertiesFile + " does not exist");
         this.generateNewProperties();
      }
   }

   public void generateNewProperties() {
      LOGGER.info("Generating new properties file");
      this.saveProperties();
   }

   public void saveProperties() {
      FileOutputStream fileoutputstream = null;

      try {
         fileoutputstream = new FileOutputStream(this.serverPropertiesFile);
         this.serverProperties.store((OutputStream)fileoutputstream, "Minecraft server properties");
      } catch (Exception exception) {
         LOGGER.warn((String)("Failed to save " + this.serverPropertiesFile), (Throwable)exception);
         this.generateNewProperties();
      } finally {
         if(fileoutputstream != null) {
            try {
               fileoutputstream.close();
            } catch (IOException var10) {
               ;
            }
         }
      }
   }

   public File getPropertiesFile() {
      return this.serverPropertiesFile;
   }

   public String getStringProperty(String key, String defaultValue) {
      if(!this.serverProperties.containsKey(key)) {
         this.serverProperties.setProperty(key, defaultValue);
         this.saveProperties();
         this.saveProperties();
      }

      return this.serverProperties.getProperty(key, defaultValue);
   }

   public int getIntProperty(String key, int defaultValue) {
      try {
         return Integer.parseInt(this.getStringProperty(key, "" + defaultValue));
      } catch (Exception var4) {
         this.serverProperties.setProperty(key, "" + defaultValue);
         this.saveProperties();
         return defaultValue;
      }
   }

   public long getLongProperty(String key, long defaultValue) {
      try {
         return Long.parseLong(this.getStringProperty(key, "" + defaultValue));
      } catch (Exception var5) {
         this.serverProperties.setProperty(key, "" + defaultValue);
         this.saveProperties();
         return defaultValue;
      }
   }

   public boolean getBooleanProperty(String key, boolean defaultValue) {
      try {
         return Boolean.parseBoolean(this.getStringProperty(key, "" + defaultValue));
      } catch (Exception var4) {
         this.serverProperties.setProperty(key, "" + defaultValue);
         this.saveProperties();
         return defaultValue;
      }
   }

   public void setProperty(String key, Object value) {
      this.serverProperties.setProperty(key, "" + value);
   }
}
