//package net.simple.forscore.plugin.advancements;
//
//import net.simple.forscore.plugin.Main;
//import org.bukkit.Bukkit;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.entity.Player;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerMoveEvent;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class AchievementsAFK implements Listener {
//    private Main plugin;
//    public AchievementsAFK(Main plugin) {
//        this.plugin = plugin;
//    }
//    Map<Player, Integer> afkTimers = new HashMap<>();
//
//    public boolean hasAdv(Player player, String name) {
//        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + player.getUniqueId() +".yml");
//        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
//        return adv.getBoolean("forscore."+name);
//    }
//    public void giveAdv(Player player, String advName){
//        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + player.getUniqueId() +".yml");
//        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
//        adv.set("forscore."+advName, true);
//        try {
//            adv.save(adv_file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
//        //    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "advancement grant "+ player.getName() +" only forscore:"+advName);
//        //});
//    }
//
//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        Player player = event.getPlayer();
//        resetTimer(player);
//    }
//
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
//            synchronized (this) {
//                if (!hasAdv(event.getPlayer(), "afk")) {
//                    Player player = event.getPlayer();
//                    Bukkit.getScheduler().cancelTask(afkTimers.get(player));
//                    resetTimer(player);
//                }
//            }
//        });
//    }
//
//    public void resetTimer(final Player p) {
//        afkTimers.put(p, Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
//            if (Bukkit.getPlayer(p.getName()) == null) return;
//            if (!hasAdv(p, "afk")) {
//                giveAdv(p, "afk");
//            }
//        }, 72000)); // Time
//    }
//}
