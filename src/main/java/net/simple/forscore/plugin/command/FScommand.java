package net.simple.forscore.plugin.command;
import com.google.common.collect.Lists;
import net.simple.forscore.plugin.Main;
//import net.simple.forscore.plugin.event.Fireballspawn;
import net.simple.forscore.plugin.event.Other;
import net.simple.forscore.plugin.raidfix.RaidFix;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FScommand extends AbstractCommand {
    public FScommand(Main plugin){
        super("fs");
        this.plugin = plugin;
    }
    private final Main plugin;
    HashMap<String, Integer> eventM = new HashMap<String, Integer>();
//    Fireballspawn fireballspawn = new Fireballspawn();
    public String[] getAdvArray(){
        String[] advArray = new String[29];
        advArray[0] = "start";
        advArray[1] = "ezra8";
        advArray[2] = "leonid";
        advArray[3] = "ban";
        advArray[4] = "cavern";
        advArray[5] = "momsanarchy";
        advArray[6] = "fathercommunism";
        advArray[7] = "enviroment";
        advArray[8] = "socialexperement";
        advArray[9] = "nightinvise";
        advArray[10] = "forsazh";
        advArray[11] = "onek";
        advArray[12] = "diedwither";
        advArray[13] = "punchfsg";
        advArray[14] = "peper";
        advArray[15] = "afk";
        advArray[16] = "reznya";
        advArray[17] = "atom";
        advArray[18] = "diedvoid";
        advArray[19] = "diedzombie";
        advArray[20] = "zakviel";
        advArray[21] = "mogilshik";
        advArray[22] = "aquaman";
        advArray[23] = "sky";
        advArray[24] = "sonic";
        advArray[25] = "turtle";
        advArray[26] = "areyouwinningson";
        advArray[27] = "mylittlebag";
        advArray[28] = "colosstitan";
        return advArray;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§cUse /"+label+" [fixadv, cooldown, progress, event]\n");
            return;
        }

        if (args[0].equalsIgnoreCase("fixadv")) fixadv(sender, label, args);
//        else if (args[0].equalsIgnoreCase("cooldown")) sender.sendMessage(cooldown(sender, label, args));
        else if (args[0].equalsIgnoreCase("progress")) sender.sendMessage(progress(sender, label, args));
        else if (args[0].equalsIgnoreCase("getop")) getop(sender, label, args);
        else if (args[0].equalsIgnoreCase("event")) event(sender, label, args);
        else sender.sendMessage("§cUse /"+label+" [fixadv, cooldown, progress, event]\n");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) return Lists.newArrayList("fixadv","cooldown","progress","event");
        return Lists.newArrayList();
    }

    public void fixadv(CommandSender sender, String label, String[] args){
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + sender.getServer().getPlayer(sender.getName()).getUniqueId() + ".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);

        for(String advName: getAdvArray()) {
            if (adv.getBoolean("forscore." + advName)) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute as " + sender.getName() + " as @s[advancements={forscore:" + advName + "=false}] run advancement grant " + sender.getName() + " only forscore:" + advName);
                });
            }
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute as " + sender.getName() + " as @s[advancements={forscore:forscore_root=false}] run advancement grant " + sender.getName() + " only forscore:forscore_root");
        });
    }

    public String cooldown(CommandSender sender, String label, String[] args){
        HashMap<String, Long> cooldowns = new HashMap<>(RaidFix.cooldowns);
        if(!cooldowns.containsKey("x")) return "Нету кулдауна! (error: x)";
        if(!cooldowns.containsKey(sender.getName())) return "Нету кулдауна! (error: no player)";
        long cooldownTime =  cooldowns.get("x");
        long secondsLeft = ((cooldowns.get(sender.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
        if(secondsLeft<0) return "Нету кулдауна!";
        long hours = secondsLeft/3600;
        long min = (secondsLeft - hours * 3600) / 60;
        long sec = secondsLeft - hours*3600 - min * 60;
        return  "Кулдаун: " + hours +" Часов, "+ min + " Минут, " + sec + " Секунд";
    }
    public String progress(CommandSender sender, String label, String[] args){
        File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + sender.getName() +".yml");
        FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
        int witherCount = prg.getInt("wither.spawncount");
        List<String> playerCount = prg.getStringList("player.killed");

        return "Убито визеров: " + witherCount + ", Убитые игроки: "+ playerCount;
    }
    public void getop(CommandSender sender, String label, String[] args){
        if (sender.isOp()){
            sender.sendMessage("Ты же уже оператор, зачем тебе ещё одна опка?");
            return;
        }
        sender.sendMessage(sender.getName() + " был назначен опетором сервера!");
    }
    public void eventMap(CommandSender sender, String label, String[] args){
        eventM.clear();
        if(args.length<3) return;
        if(args[1].equalsIgnoreCase("other")) {
            if(args[2].equalsIgnoreCase("start")) {eventM.put("other-on",0); return;}
            if(args[2].equalsIgnoreCase("stop")) {eventM.put("other-off",0); return;}
        }
    }

    public void event(CommandSender sender, String label, String[] args){
        if(!sender.isOp()){
            sender.sendMessage("§cВремя действия вашего статуса оператора подошло к концу! " +
                    "Пожалуйста обратитесь к администратору, если вы считаете что это ошибка.");
            return;
        }
        eventMap(sender, label, args);
        if(eventM.isEmpty()){
            String name = sender.getName();
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw "+name+ " [\"\",{\"text\":\"Use /fs event firerain [\"},{\"text\":\"start\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain start\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"stop\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain stop\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"restart\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain restart\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setRadius\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setRadius\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setY\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setY\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setPeriod\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setPeriod\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setExplosionPower\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setExplosionPower\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"]\\nUse /fs event other [\"},{\"text\":\"start\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event other start\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"stop\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event other stop\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"maxMobs\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event other maxMobs\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"]\"}]");
            return;
        }
        File configFile = new File(plugin.getDataFolder() + File.separator + "/event/config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if(eventM.containsKey("other-on")) {
            if(config.getBoolean("fs.other.enabled")) return;
            config.set("fs.other.enabled", true);
            Bukkit.getPluginManager().registerEvents(new Other(plugin), plugin);
            for (Player p : Bukkit.getOnlinePlayers()){
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000000,0,false,false,false));
            }
            sender.sendMessage("(Что-то) Успешно включено!");
        }
        if(eventM.containsKey("other-off")){
            if(config.getBoolean("fs.other.enabled")) {
                config.set("fs.other.enabled", false);
                sender.sendMessage("(Что-то) Успешно отключено, но требуется перезапуск сервера");
            }
            else sender.sendMessage("(Что-то) И так выключено");
        }

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}