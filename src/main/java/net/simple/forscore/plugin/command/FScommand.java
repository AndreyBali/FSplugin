package net.simple.forscore.plugin.command;
import com.google.common.collect.Lists;
import net.simple.forscore.plugin.Main;
import net.simple.forscore.plugin.advancements.Achievements;
import net.simple.forscore.plugin.raidfix.RaidFix;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;


import java.io.File;
import java.util.*;

public class FScommand extends AbstractCommand {
    public FScommand(Main plugin){
        super("fs");
        this.plugin = plugin;
    }
    private final Main plugin;
    HashMap<String, Integer> eventM = new HashMap<String, Integer>();
//    Fireballspawn fireballspawn = new Fireballspawn();
    public String[] getAdvArray(){
        return Achievements.getAdvArray();
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§cUse /"+label+" [progress, takePage, sign]\n");
            return;
        }

        if (args[0].equalsIgnoreCase("fixadv")) fixadv(sender, label, args);
        //else if (args[0].equalsIgnoreCase("cooldown")) sender.sendMessage(cooldown(sender, label, args));
        else if (args[0].equalsIgnoreCase("progress")) sender.sendMessage(progress(sender, label, args));
        //else if (args[0].equalsIgnoreCase("getop")) getop(sender, label, args);
        else if(args[0].equalsIgnoreCase("revokeAdvs")) revokeAdvs(sender);
        //else if(args[0].equalsIgnoreCase("lol")) lol(sender);
        //else if(args[0].equalsIgnoreCase("ban")) ban(sender);
        //else if(args[0].equalsIgnoreCase("pardon")) pardon(sender,args);
        else if(args[0].equalsIgnoreCase("disablePlugin")) disablePlugin(sender,args);
        else if(args[0].equalsIgnoreCase("takePage")) takePage(sender,args);
        else if(args[0].equalsIgnoreCase("sign")) subscribe(sender);
        else if(args[0].equalsIgnoreCase("setColor")) setColor(sender);
        else if(args[0].equalsIgnoreCase("debug")) debug(sender,args);
        else if(args[0].equalsIgnoreCase("customModelData")) customModelData(sender,args);
        //else if(args[0].equalsIgnoreCase("config")) config(sender,args);
 //       else if (args[0].equalsIgnoreCase("event")) event(sender, label, args);//
        else sender.sendMessage("§cUse /"+label+" [progress, takePage, sign]\n");
        //
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = Lists.newArrayList("progress","takePage","sign");
        if(sender.isOp() || sender.hasPermission("fs.customModelData"))
            arguments.add("customModelData");//customModelData
        if(sender.isOp()) arguments.add("setColor");

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) result.add(a);
            }
            return result;
        }
        if(args.length==2){
            if(args[0].equalsIgnoreCase("config")){
                for (String a : Lists.newArrayList("fireworkVillagers","customAnvilSymbols","reload")) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase())) result.add(a);
                }
                return result;
            }
        }
        return Lists.newArrayList();
    }

    public void customModelData(CommandSender sender,String args[]){
        if(sender.isOp() || sender.hasPermission("fs.customModelData")){
            Player p = (Player) sender;
            ItemStack item = p.getInventory().getItemInMainHand();
            if(item.getType().isAir()) return;
            Integer md = Integer.parseInt(args[1]);
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(md);
            item.setItemMeta(meta);
            p.getInventory().setItemInMainHand(item);
        }
    }

    public void debug(CommandSender sender,String[] args){
        sender.sendMessage("§cUse /"+"fs"+" [cooldown, progress, takePage, sign, setColor]\n");
        if(args[1].equalsIgnoreCase("list")) sender.sendMessage(Bukkit.getOnlinePlayers()+"");
        if(args[1].equalsIgnoreCase("nameList")){
            String names = "Players: ";
            for(Player p: Bukkit.getOnlinePlayers()){
                names = names+p.getName()+", ";
            }
            sender.sendMessage(names);
        }
        if(args[1].equalsIgnoreCase("near")){
            if(args.length == 3){
                String names = "Players: ";
                for(Player p: Bukkit.getOnlinePlayers()){
                    Player player = (Player)sender;
                    if(Achievements.squaredDistance(player.getLocation(),p.getLocation())< Integer.parseInt(args[2]))
                        names = names+p.getName()+", ";
                }
                sender.sendMessage(names);
            }
            else {
                String names = "Players: ";
                for(Player p: Bukkit.getOnlinePlayers()){
                    Player player = (Player)sender;
                    if(Achievements.squaredDistance(player.getLocation(),p.getLocation())< 100)
                        names = names+p.getName()+", ";
                }
                sender.sendMessage(names);
            }
        }

    }

    public void config(CommandSender sender, String[] args){
        if(!sender.isOp()){
            if(!sender.getName().equalsIgnoreCase("AndreyBali")){
                sender.sendMessage("Для этого нужна опка!");
                return;
            }
        }
        if(args.length<=2) {
            sender.sendMessage("Use /fs config [reload, fireworkVillagers, customAnvilSymbols] [true, false]");
            return;
        }
        if(args[2].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage("Reloading config...");
            return;
        }
        if(args.length<=4) {
            sender.sendMessage("Use /fs config [reload, fireworkVillagers, customAnvilSymbols] [true, false]");
            return;
        }
        if(args[4].equalsIgnoreCase("true")) {
            plugin.getConfig().set(args[3],true);
            sender.sendMessage("Set "+args[3]+" to true");
            return;
        }
        if(args[4].equalsIgnoreCase("false")) {
            plugin.getConfig().set(args[3],false);
            sender.sendMessage("Set "+args[3]+" to false");
        }

    }

    public void setColor(CommandSender sender){
        if(sender.isOp()) plugin.menu(sender);
    }

    public void takePage(CommandSender sender, String[] args){
        if(args.length !=2) {
            sender.sendMessage("Use /fs takePage <page number>");
            return;
        }
        Player p = (Player)sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item.getType() != Material.WRITABLE_BOOK) {
            sender.sendMessage("It isn't a book in your hand!");
            return;
        }
        BookMeta meta = (BookMeta)item.getItemMeta();
        if(meta.getPageCount()<Integer.parseInt(args[1])) {
            sender.sendMessage("There is no such page in your book!");
            return;
        }
        if(meta.hasCustomModelData()) {
            int md = meta.getCustomModelData()-1;
            meta.setCustomModelData(md);
            meta.setLore(Arrays.asList("","§r§fПрочность: "+md+"/6"));
        }
        else {
            meta.setCustomModelData(5);
            meta.setLore(Arrays.asList("","§r§fПрочность: 5/6"));
        }

        item.setItemMeta(meta);
        if(meta.getCustomModelData()==0){
            p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            p.sendMessage("У вашей книги закончились листочки!");
        }

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta paperMeta = paper.getItemMeta();
        String page = meta.getPage(Integer.parseInt(args[1]));
        List<String> stringPage = new ArrayList<>(Arrays.asList(page.split("\n")));
        int i = 0;
        for (String str : stringPage)
        {
            stringPage.set(i, "§r§f"+str);
            i++;
        }
        stringPage.add(0,"");
        paperMeta.setLore(stringPage);
        paperMeta.setDisplayName("§rВырванный листочек");
        paperMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        paper.setItemMeta(paperMeta);
        p.getInventory().addItem(paper);


    }

    public void subscribe(CommandSender sender){
        Player p = (Player)sender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item.getType() != Material.PAPER) {
            sender.sendMessage("Подписать можно только вырванный листочек");
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if(!meta.hasItemFlag(ItemFlag.HIDE_UNBREAKABLE)) {
            sender.sendMessage("Подписать можно только вырванный листочек");
            return;
        }
        List<String> badLore = meta.getLore();
        List<String> lore = new ArrayList<>();
        if(badLore != null) lore.addAll(badLore);

        for (int j = lore.size(); j <= 15; j++) {
            lore.add("");
        }
        lore.add("§r§fПодпись: §7" + p.getName());
        meta.setLore(lore);
        item.setItemMeta(meta);
    }
    public void disablePlugin(CommandSender sender, String[] args){
        if(!sender.isOp()){
            sender.sendMessage("§cЭту команду может использовать только оператор!");
            return;
        }
        if(args.length==2)
            if(args[1].equalsIgnoreCase("confirm")){
                System.out.println(sender.getName() +" is disabling "+plugin.getName()+" plugin!");
                sender.sendMessage(plugin.getName()+" будет выключен через 10 тиков." );
                Bukkit.getScheduler().runTaskLater(plugin, ()->{ Bukkit.getPluginManager().disablePlugin(plugin); },10);
            }
        sender.sendMessage("Вы действительно хотите выключить плагин "+plugin.getName()+"? (Для его включения нужно будет перезапустить сервер)");
        sender.sendMessage("Если да, то напишите: /fs disablePlugin confirm");
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

    public void revokeAdvs(CommandSender sender){
        for(String advName: getAdvArray()){
            Achievements.advs.remove(sender.getName()+"."+advName);
        }
    }

    public String cooldown(CommandSender sender, String label, String[] args){
        HashMap<String, Long> cooldowns = new HashMap<>(RaidFix.cooldowns);
        if(!cooldowns.containsKey("x")) return "Нету кулдауна! (nv)";
        if(!cooldowns.containsKey(sender.getName())) return "Нету кулдауна! (np)";
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
    public void lol(CommandSender sender){
        sender.sendMessage("Ну и что ты хотел тут получить?");
    }
    public void ban(CommandSender sender){
        sender.sendMessage("Бан захотел? ну так");
        sender.sendMessage("/ban "+sender.getName());
    }
    public void pardon(CommandSender sender, String[] args){
        if(args.length == 1) {
            sender.sendMessage("Так ну зачем разбанивать тебя? Ты же и так разбанен");
            return;
        }

        sender.sendMessage("/pardon "+args[1]);
        for(OfflinePlayer p : Bukkit.getBannedPlayers()){
            if(p.getName().equalsIgnoreCase(args[1])){
                sender.sendMessage("§cНе удалось разблокировать игрока "+args[1]);
                return;
            }
        }
        for(OfflinePlayer p : Bukkit.getWhitelistedPlayers()){
            if(p.getName().equalsIgnoreCase(args[1])){
                sender.sendMessage("§cИгрок "+args[1] +" уже разблокирован!");
                return;
            }
        }
        sender.sendMessage("§cИгрок "+args[1]+" никогда не заходил на сервер!");
    }
    public void eventMap(CommandSender sender, String label, String[] args){
        eventM.clear();
        if(args.length<3) return;
        if(args[1].equalsIgnoreCase("other")) {
            if(args[2].equalsIgnoreCase("start")) {eventM.put("other-on",0); return;}
            if(args[2].equalsIgnoreCase("stop")) {eventM.put("other-off",0); return;}
        }
    }

//    public void event(CommandSender sender, String label, String[] args){
//        if(!sender.isOp()){
//            sender.sendMessage("§cВремя действия вашего статуса оператора подошло к концу! " +
//                    "Пожалуйста обратитесь к администратору, если вы считаете что это ошибка.");
//            return;
//        }
//        eventMap(sender, label, args);
//        if(eventM.isEmpty()){
//            String name = sender.getName();
//            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw "+name+ " [\"\",{\"text\":\"Use /fs event firerain [\"},{\"text\":\"start\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain start\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"stop\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain stop\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"restart\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain restart\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setRadius\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setRadius\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setY\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setY\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setPeriod\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setPeriod\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"setExplosionPower\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event firerain setExplosionPower\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"]\\nUse /fs event other [\"},{\"text\":\"start\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event other start\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"stop\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event other stop\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\", \",\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"maxMobs\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/fs event other maxMobs\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Click\",\"color\":\"aqua\"}}},{\"text\":\"]\"}]");
//            return;
//        }
//        File configFile = new File(plugin.getDataFolder() + File.separator + "/event/config.yml");
//        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
//        if(eventM.containsKey("other-on")) {
//            if(config.getBoolean("fs.other.enabled")) return;
//            config.set("fs.other.enabled", true);
//            Bukkit.getPluginManager().registerEvents(new Other(plugin), plugin);
//            for (Player p : Bukkit.getOnlinePlayers()){
//                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000000,0,false,false,false));
//            }
//            sender.sendMessage("(Что-то) Успешно включено!");
//        }
//        if(eventM.containsKey("other-off")){
//            if(config.getBoolean("fs.other.enabled")) {
//                config.set("fs.other.enabled", false);
//                sender.sendMessage("(Что-то) Успешно отключено, но требуется перезапуск сервера");
//            }
//            else sender.sendMessage("(Что-то) И так выключено");
//        }
//
//        try {
//            config.save(configFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}