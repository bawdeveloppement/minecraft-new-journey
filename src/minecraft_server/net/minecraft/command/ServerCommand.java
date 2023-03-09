package net.minecraft.command;

public class ServerCommand {
   public final String command;
   public final ICommandSender sender;

   public ServerCommand(String input, ICommandSender sender) {
      this.command = input;
      this.sender = sender;
   }
}
