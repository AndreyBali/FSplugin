//package net.simple.forscore.plugin.command;
//
//import net.simple.forscore.plugin.Main;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.command.CommandSender;
//
//import java.util.Objects;
//
//public class CoreProtectCommand extends AbstractCommand{
//    private Main plugin;
//    public CoreProtectCommand(Main plugin) {
//        super("coi");
//        this.plugin = plugin;
//    }
//    @Override
//    public void execute(CommandSender sender, String label, String[] args) {
//        if(sender.getName ().equals("MaksMaruS_")
//                || sender.getName().equals("Chisto_kayfarik")
//                || sender.getName().equals("Gryazno_kayfarik")){
//            if(args.length==0 || args.length==1 || !(args[0].equals("give") || args[0].equals("take"))) {
//                sender.sendMessage("use /coi [give, take] \"PlayerName\"");
//            }
//            else {
//                if(args[0].equals("take")){
//                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
//                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "advancement grant "+ player.getName() +" only forscore:"+advName);
//                    });
//                }
//
//                if(args[0].equals("give"))
//
//            }
//        }
//        else {
//            sender.sendMessage(ChatColor.RED + sender.getName()+ ", you cant use this command!");
//        }
//    }
//}
