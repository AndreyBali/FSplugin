//package net.simple.forscore.plugin.advancements;
//
//import net.simple.forscore.plugin.Main;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerJoinEvent;
//
//import java.io.File;
//import java.io.IOException;
//
//public class CreateFile implements Listener {
//    private Main plugin;
//    public CreateFile(Main plugin) {
//        this.plugin = plugin;
//    }
//
//    @EventHandler
//    public void onjoin(PlayerJoinEvent event) {
//        File advancements = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getUniqueId() +".yml");
//        if(!advancements.exists()) {
//            try {
//                advancements.createNewFile();
//
//                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getUniqueId() +".yml");
//                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
//                FileConfiguration abv = advanclist.advlist(adv);
//                abv.save(adv_file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        File progress = new File(plugin.getDataFolder() + File.separator + "/progress/" + event.getPlayer().getUniqueId() +".yml");
//        if(!progress.exists()) {
//            try {
//                progress.createNewFile();
//
//                File adv_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + event.getPlayer().getUniqueId() +".yml");
//                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
//                FileConfiguration abv = advanclist.prglist(adv);
//                abv.save(adv_file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
