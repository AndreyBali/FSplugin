package net.simple.forscore.plugin;

import github.scarsz.discordsrv.DiscordSRV;
import net.simple.forscore.plugin.advancements.Achievements;
import net.simple.forscore.plugin.armorstand.ArmorPose;
import net.simple.forscore.plugin.armorstand.ArmorSpawn;
import net.simple.forscore.plugin.command.FScommand;
import net.simple.forscore.plugin.discordsrv.LinkEvent;
import net.simple.forscore.plugin.experience.drop.ExpDrop;
import net.simple.forscore.plugin.otherThings.Events;
import net.simple.forscore.plugin.otherThings.GUIManager;
import net.simple.forscore.plugin.raidfix.RaidFix;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    private static Main Instance;
    boolean isEnabledDiscordSRV = false;
    @Override
    public void onEnable() {

        if(!getConfig().contains("customAnvilSymbols")) saveDefaultConfig();

        File path = new File(getDataFolder() + File.separator + "/advancements/");
        if(!path.exists()) path.mkdirs();

        File path2 = new File(getDataFolder() + File.separator + "/progress/");
        if(!path2.exists()) path2.mkdirs();

//        File path3 = new File(getDataFolder() + File.separator + "/event/");
//        if(!path3.exists()) path3.mkdirs();



        // Commands
        Instance = this;
        new FScommand(this);

//        // FireBall
//        Fireballspawn fireballspawn = new Fireballspawn();
//        fireballspawn.setPlugin(this);
//        fireballspawn.loadConfig();
//        //Other
//        File config_file = new File(this.getDataFolder() + File.separator + "/event/config.yml");
//        FileConfiguration config = YamlConfiguration.loadConfiguration(config_file);
//        if(config.getBoolean("fs.other.enabled")) Bukkit.getPluginManager().registerEvents(new Other(this), this);

        // Advancements
//        Bukkit.getPluginManager().registerEvents(new CreateFile(this), this);
//        Bukkit.getPluginManager().registerEvents(new AchievementsAFK(this), this);
        Bukkit.getPluginManager().registerEvents(new Achievements(this), this);

        // DiscordSRV

        if(Bukkit.getPluginManager().isPluginEnabled("DiscordSRV")) {
            isEnabledDiscordSRV = true;
            DiscordSRV.api.subscribe(new LinkEvent(this));
        }
        else getLogger().warning("Please enable DiscordSRV plugin");

        // ArmorStand
        Bukkit.getPluginManager().registerEvents(new ArmorPose(), this);
        Bukkit.getPluginManager().registerEvents(new ArmorSpawn(), this);

        // RaidFix
        Bukkit.getPluginManager().registerEvents(new RaidFix(), this);

        // Death Experience Drop
        Bukkit.getPluginManager().registerEvents(new ExpDrop(), this);

        //gui
        Bukkit.getPluginManager().registerEvents(new GUIManager(), this);

        //other
        Bukkit.getPluginManager().registerEvents(new Events(), this);

        getLogger().info("Plugin successfully started up!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling plugin...");
        if(isEnabledDiscordSRV) DiscordSRV.api.unsubscribe(new LinkEvent(this));
        Bukkit.getScheduler().cancelTasks(this);

        getLogger().info("Plugin successfully disabled!");
    }
    public void menu(CommandSender sender){
        GUIManager.openInventory((HumanEntity) sender);
    }

    public static Main getInstance() {
        return Instance;
    }
}