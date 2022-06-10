//package net.simple.forscore.plugin.event;
//
//import net.simple.forscore.plugin.Main;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.configuration.file.FileConfiguration;
//import org.bukkit.configuration.file.YamlConfiguration;
//import org.bukkit.entity.Fireball;
//import org.bukkit.entity.Player;
//import org.bukkit.util.Vector;
//
//import java.io.File;
//import java.util.Random;
//
//public class Fireballspawn {
//    public Fireballspawn(){
//    }
//    private static Main plugin;
//
//    public void setPlugin(Main mPlugin){
//        plugin = mPlugin;
//    }
//
//    public void spawn(){
//        int y = 400;
//        int radius = 100;
//        long period = 500;
//        int explosionPower = 4;
//
//        File config_file = new File(plugin.getDataFolder() + File.separator + "/event/config.yml");
//        FileConfiguration config = YamlConfiguration.loadConfiguration(config_file);
//        if(config.contains("fs.y")) y = config.getInt("fs.y");
//        if(config.contains("fs.period")) period = config.getInt("fs.period");
//        if(config.contains("fs.radius")) radius = config.getInt("fs.radius");
//        if(config.contains("fs.explosionPower")) explosionPower = config.getInt("fs.explosionPower");
//
//        int finalExplosionPower = explosionPower;
//        int finalRadius = radius;
//        int finalY = y;
//
//        Random rand = new Random();
//
//        Bukkit.getServer().getScheduler().runTaskTimer(plugin, () -> {
//            for (Player p : Bukkit.getOnlinePlayers()) {
//                if (p.getWorld().getName().equalsIgnoreCase("world")){
//                    double x = p.getLocation().getX()+rand.nextInt(finalRadius *2)- finalRadius;
//                    double z = p.getLocation().getZ()+rand.nextInt(finalRadius *2)- finalRadius;
//                    Location location = new Location(p.getWorld(),x, finalY,z);
//                    Fireball fireball = p.getWorld().spawn(location, Fireball.class);
//                    fireball.setDirection(new Vector(0,-0.1,0));
//                    fireball.setYield(finalExplosionPower);
//                }
//            }
//        },0L,period);
//    }
//
//    public void loadConfig() {
//        File config_file = new File(plugin.getDataFolder() + File.separator + "/event/config.yml");
//        FileConfiguration config = YamlConfiguration.loadConfiguration(config_file);
//        if(config.getBoolean("fs.fireRain")) spawn();
//    }
//}