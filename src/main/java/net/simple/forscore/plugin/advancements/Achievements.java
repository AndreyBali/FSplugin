package net.simple.forscore.plugin.advancements;

import net.simple.forscore.plugin.Main;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Achievements implements Listener {
    private final Main plugin;

    public Achievements(Main plugin) {
        this.plugin = plugin;
        //advScheduler();
        //afkTimer();
    }

    public static Map<String, Boolean> advs = new HashMap<>();
    List<Player> playerDeaths = new ArrayList<>();
    Map<String, Boolean> mrPin = new HashMap<>();
    Map<String, Boolean> liShan = new HashMap<>();


//    public void advScheduler(){
//        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, ()->{
//            for (Player p : Bukkit.getOnlinePlayers()) {
//                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getUniqueId() + ".yml");
//                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
//                for (String x : getAdvArray()) {
//                    adv.set("forscore." + x, advs.get(p.getName() + "." + x));
//                }
//                try {
//                    adv.save(adv_file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 500, 500);
//    }




    public boolean hasAdv(Player player, String advName) {
        if (!advs.containsKey(player.getName() + "." + advName)) return false;
        return advs.get(player.getName() + "." + advName);
    }

    public void giveAdv(Player player, String advName) {
        advs.put(player.getName() + "." + advName, true);
        Bukkit.getServer().getScheduler().runTask(plugin, () -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute as " + player.getName() + " as @s[advancements={forscore:" + advName + "=false}] run advancement grant " + player.getName() + " only forscore:" + advName);
        });
    }

    public void giveNAdv(Player player, String advName) {
        advs.put(player.getName() + "." + advName, true);
        try{
        Bukkit.getServer().getScheduler().runTask(plugin, () -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute as " + player.getName() + " as @s[advancements={forscore3:" + advName + "=false}] run advancement grant " + player.getName() + " only forscore3:" + advName);
        });
        }catch (IllegalArgumentException e) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equals("AndreyBali")) {
                    p.sendMessage("Error while trying to give adv "+advName);
                    p.sendMessage("e.getMessage:  " + e.getMessage());
                    System.out.println("Error while trying to give adv "+advName);
                    throw e;
                }
            }
        }
    }

    public static Integer squaredDistance(Location l1,Location l2){
        if(l1 == null || l2 == null) return 2147483647;
        if(l1.getWorld() != l2.getWorld()) return 2147483647;
        double d =l1.distanceSquared(l2);
        if(d>2147483647) return 2147483647;
        return (int)d;
    }

    public static String[] getAdvArray() {
        String[] advArray = new String[47];
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
        advArray[29] = "killshulker";
        advArray[30] = "yep3";
        advArray[31] = "a5000trades";
        advArray[32] = "netheritebeacon";
        advArray[33] = "enderman";
        advArray[34] = "dragon_fight";
        advArray[35] = "accidents_not_accidental";
        advArray[36] = "father_and_father";
        advArray[37] = "die_in_bazalt_delts";
        advArray[38] = "skeleton_killed_warden";
        advArray[39] = "break_the_rules";
        advArray[40] = "bad_totem";
        advArray[41] = "lama_killed_ghast";
        advArray[42] = "merlin";
        advArray[43] = "zoglin_killed_hoglin";
        advArray[44] = "extreme_drying";
        advArray[45] = "real_phantom";
        advArray[46] = "star_fox";
        return advArray;
    }

    public void say(String text) {
        System.out.println(text);
        Bukkit.getServer().getScheduler().runTask(plugin, () -> {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "say " + text);
        });
    }

    @EventHandler
    public void starFox(EntityPickupItemEvent event) {
        if (event.getEntityType() != EntityType.FOX) return;
        if (event.getItem().getItemStack().getType() != Material.NETHER_STAR) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (squaredDistance(p.getLocation(),event.getEntity().getLocation()) < 100) {
                giveNAdv(p, "star_fox");
            }
        }
    }

    @EventHandler
    public void extremeDrying(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.WET_SPONGE)
            if (event.getBlock().getLocation().getWorld().toString().equalsIgnoreCase("the_nether") || event.getBlock().getLocation().getWorld().toString().equalsIgnoreCase("world_nether"))
                if (!hasAdv(event.getPlayer(), "extreme_drying"))
                    giveNAdv(event.getPlayer(), "extreme_drying");
    }

    @EventHandler
    public void ridingAdmin(PlayerMoveEvent event) {
        if (event.getPlayer().getStatistic(Statistic.STRIDER_ONE_CM) > 100000) {
            if (hasAdv(event.getPlayer(), "riding_admin")) return;
            giveNAdv(event.getPlayer(), "riding_admin");
        }
    }

    @EventHandler
    public void setName(PlayerInteractAtEntityEvent event) {
        String name = event.getRightClicked().getCustomName();
        if (name == null) return;
        EntityType et = event.getRightClicked().getType();
        Player p = event.getPlayer();
        if (name.equals("По") && et == EntityType.PANDA)
            if (!hasAdv(p, "dragon_fight")) giveNAdv(p, "dragon_fight");

        if (name.equals("Угвэй") && et == EntityType.TURTLE)
            if (!hasAdv(p, "accidents_not_accidental")) giveNAdv(p, "accidents_not_accidental");

        if (name.equals("Ли Шан") && et == EntityType.PANDA) {
            liShan.put(p.getName(), true);
            if (mrPin.containsKey(p.getName()))
                if (!hasAdv(p, "father_and_father")) giveNAdv(p, "father_and_father");
        }
        if (name.equals("Мистер Пин") && et == EntityType.CHICKEN) {
            mrPin.put(p.getName(), true);
            if (liShan.containsKey(p.getName()))
                if (!hasAdv(p, "father_and_father")) giveNAdv(p, "father_and_father");
        }
    }

    @EventHandler
    public void dieInBasaltDelts(PlayerDeathEvent event) {
        if (event.getEntity().getLocation().getBlock().getBiome() != Biome.BASALT_DELTAS) return;
        if (!hasAdv(event.getEntity(), "die_in_bazalt_delts"))
            giveNAdv(event.getEntity(), "die_in_bazalt_delts");
    }

    @EventHandler
    public void skeletonKilledWarden(EntityDamageByEntityEvent event) {
//        say("Entity: "+ event.getEntity().getType()+" Damager: "+event.getDamager().getType());
        Entity damager = null;
        if (event.getDamager().getType() == EntityType.ARROW)
            damager = ((Entity) ((Arrow) event.getDamager()).getShooter());
        if (damager == null) return;
        if (event.getEntity().getType() != EntityType.WARDEN) return;
        if (damager.getType() != EntityType.SKELETON) return;
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (event.getEntity().isDead())
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if ( squaredDistance(p.getLocation(),event.getDamager().getLocation()) < 121) {
                        giveNAdv(p, "skeleton_killed_warden");
                    }
                }
        }, 1);
    }

    @EventHandler
    public void breakTheRules(PlayerDeathEvent event) {
        if (event.getDeathMessage().equalsIgnoreCase(event.getEntity().getName() + " was killed by [Intentional Game Design]")) {
            if (hasAdv(event.getEntity(), "break_the_rules")) return;
            giveNAdv(event.getEntity(), "break_the_rules");
        }
    }

    @EventHandler
    public void badTotem(PlayerDeathEvent event) {
        if (event.getEntity().getInventory().getItemInMainHand().getType() == Material.TOTEM_OF_UNDYING
                || event.getEntity().getInventory().getItemInOffHand().getType() == Material.TOTEM_OF_UNDYING) {
            if (hasAdv(event.getEntity(), "bad_totem")) return;
            giveNAdv(event.getEntity(), "bad_totem");
        }
    }

    @EventHandler
    public void lamaKilledGhast(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.GHAST) return;
        if (event.getDamager().getType() != EntityType.LLAMA_SPIT) return;
        try{
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (event.getEntity().isDead())
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (squaredDistance(p.getLocation(),event.getDamager().getLocation()) < 256) {
                            giveNAdv(p, "lama_killed_ghast");
                        }
                    }
            }, 1);
        }catch (IllegalArgumentException e) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equals("AndreyBali")) {
                    p.sendMessage("lama_killed_ghast");
                    p.sendMessage("e.getMessage:  " + e.getMessage());
                    System.out.println("lama_killed_ghast");
                    throw e;
                }
            }
        }
    }

    @EventHandler
    public void pillagerKilledPhantom(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.PHANTOM) return;
        if (event.getDamager().getType() != EntityType.VINDICATOR) return;
        try {
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (event.getEntity().isDead())
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (squaredDistance(p.getLocation(),event.getDamager().getLocation()) < 100) {
                            giveNAdv(p, "real_phantom");
                        }
                    }
            }, 1);
        } catch (IllegalArgumentException e) {
            for(Player p :Bukkit.getOnlinePlayers()){
                if(p.getName().equals("AndreyBali")){
                    p.sendMessage("real_phantom");
                    p.sendMessage("e.getMessage:  "+e.getMessage());
                    System.out.println("real_phantom");
                    throw e;
                }
            }
        }

    }

    @EventHandler
    public void merlin(EnchantItemEvent event) {
        if (event.getEnchanter().getStatistic(Statistic.ITEM_ENCHANTED) > 521)
            if (!hasAdv(event.getEnchanter(), "merlin")) giveNAdv(event.getEnchanter(), "merlin");
    }

    @EventHandler
    public void zoglinKilledHoglin(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() != EntityType.HOGLIN) return;
        if (event.getDamager().getType() != EntityType.ZOGLIN) return;
        try{
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                if (event.getEntity().isDead())
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (squaredDistance(p.getLocation(),event.getDamager().getLocation()) < 99) {
                            giveNAdv(p, "zoglin_killed_hoglin");
                        }
                    }
            }, 1);
        }catch (IllegalArgumentException e) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().equals("AndreyBali")) {
                    p.sendMessage("zoglin_killed_hoglin");
                    p.sendMessage("e.getMessage:  " + e.getMessage());
                    System.out.println("zoglin_killed_hoglin");
                    throw e;
                }
            }
        }
    }

    @EventHandler
    public void killShulker(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.SHULKER) return;
        if (event.getEntity().getKiller() == null) return;
        if (hasAdv(event.getEntity().getKiller(), "killshulker")) return;
        if (event.getEntity().getWorld().getName().toString().equalsIgnoreCase("world"))
            giveNAdv(event.getEntity().getKiller(), "killshulker");
    }

    @EventHandler
    public void playerTeleport(PlayerTeleportEvent event) {
        if (event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
        if (squaredDistance(event.getTo(),event.getFrom()) < 1000000) return;
        if (hasAdv(event.getPlayer(), "enderman")) return;
        giveNAdv(event.getPlayer(), "enderman");
    }

    @EventHandler
    public void yep3(EntityDamageByEntityEvent event) {
        if (event.getDamager().toString().equalsIgnoreCase("CraftTippedArrow")
                && event.getEntity() instanceof Player) {
            playerDeaths.add((Player) event.getEntity());
            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                playerDeaths.remove((Player) event.getEntity());
            }, 21);

            Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                int count = 0;
                for (Player p : playerDeaths) {
                    if (squaredDistance(event.getEntity().getLocation(),p.getLocation()) < 100 && p.isDead()) count++;
                }
                if (count >= 3) {
                    for (Player p : playerDeaths) {
                        if (squaredDistance(event.getEntity().getLocation(),p.getLocation()) < 100 && p.isDead()) {
                            giveNAdv(p, "yep3");
                        }
                    }
                }
            }, 1);
        }
    }

    @EventHandler
    public void a5000Trades(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER) return;
        if (hasAdv(event.getPlayer(), "a5000trades")) return;
        int trades = event.getPlayer().getStatistic(Statistic.TRADED_WITH_VILLAGER);
        if (trades < 5000) return;
        giveNAdv(event.getPlayer(), "a5000trades");
    }

    @EventHandler
    public void netheriteBeacon(PlayerInteractEvent event) {


        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.BEACON) return;
        if (hasAdv(event.getPlayer(), "netheritebeacon")) return;
        Location loc = event.getClickedBlock().getLocation();
        World world = event.getClickedBlock().getWorld();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        //ye, i know, its TOO big
        //but idk how to make this code smaller
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (world.getBlockAt(x + i, y - 1, z + j).getType() != Material.NETHERITE_BLOCK) return;
            }
        }
        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if (world.getBlockAt(x + i, y - 2, z + j).getType() != Material.NETHERITE_BLOCK) return;
            }
        }
        for (int i = -3; i < 4; i++) {
            for (int j = -3; j < 4; j++) {
                if (world.getBlockAt(x + i, y - 3, z + j).getType() != Material.NETHERITE_BLOCK) return;
            }
        }
        for (int i = -4; i < 5; i++) {
            for (int j = -4; j < 5; j++) {
                if (world.getBlockAt(x + i, y - 4, z + j).getType() != Material.NETHERITE_BLOCK) return;
            }
        }
        giveNAdv(event.getPlayer(), "netheritebeacon");
    }


//    @EventHandler
//    public void onJoin(PlayerJoinEvent event) {
//        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getUniqueId() + ".yml");
//        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
//        for (String x : getAdvArray()) {
//            advs.put(event.getPlayer().getName() + "." + x, adv.getBoolean("forscore." + x));
//        }
//    }

    @EventHandler
    public void start(PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            if (!hasAdv(event.getPlayer(), "start")) {
                giveAdv(event.getPlayer(), "start");
                Bukkit.getServer().getScheduler().runTask(plugin, () -> {
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + event.getPlayer().getName() + " only forscore:forscore_root");
                });
            }
        }, 7);
    }

    @EventHandler
    public void ezra8(PlayerDeathEvent event) {
        String nick = event.getEntity().getName();
        if (!nick.equals("EZRA_8")) return;
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        if (!hasAdv(killer, "ezra8") && killer.getPlayer() != null) {
            giveAdv(killer, "ezra8");
        }
    }

    @EventHandler
    public void leonid(PlayerInteractEntityEvent event) {
        if (!hasAdv(event.getPlayer(), "leonid")) {
            if (event.getRightClicked() instanceof Chicken) {
                Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                    if (!event.getRightClicked().getCustomName().equals("Леонид")) return;
                    giveAdv(event.getPlayer(), "leonid");
                }, 2);
            }
        }
    }

    @EventHandler
    public void ban(PlayerDeathEvent event) {
        String nick = event.getEntity().getName();
        switch (nick) {
            case "MrGridlock":
            case "MindLooker":
            case "gangsetr848":
                break;
            default:
                return;
        }
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (!hasAdv(killer, "ban")) {
            giveAdv(killer, "ban");
        }
    }

    @EventHandler
    public void cavern(PlayerMoveEvent event) {
        if (!hasAdv(event.getPlayer(), "cavern")) {
            int y = event.getPlayer().getLocation().getBlockY();
            if (y > -61) return;
            giveAdv(event.getPlayer(), "cavern");
        }
    }

    @EventHandler
    public void momsanarchyANDking_of_wither(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.BUILD_WITHER) return;
        int maxDist = 10;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (squaredDistance(p.getLocation(),event.getEntity().getLocation()) <= maxDist * maxDist) {
                File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + p.getName() + ".yml");
                FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
                int count = prg.getInt("wither.spawncount");
                if (count >= 99) {
                    giveAdv(p, "momsanarchy");
                }
                if (count > 319) {
                    giveNAdv(p, "king_of_wither");
                }
                prg.set("wither.spawncount", count + 1);
                try {
                    prg.save(prg_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void fathercommunism(EntityCombustEvent event) {
        if (event.getEntity() instanceof Item) {
            ItemStack item = ((Item) event.getEntity()).getItemStack();
            if (item.getAmount() != 64) return;
            switch (item.getType()) {
                case DIAMOND_ORE:
                case DEEPSLATE_DIAMOND_ORE:
                    break;
                default:
                    return;
            }
            int maxDist = 10;// whatever
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (squaredDistance(p.getLocation(),event.getEntity().getLocation()) <= maxDist * maxDist) {
                    giveAdv(p, "fathercommunism");
                }
            }
        }
    }

    @EventHandler
    public void enviroment(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().toString().equals("BUILD_WITHER")) return;
        if (!event.getEntity().getWorld().getName().equals("world_nether")) return;

        int maxDist = 10;// whatever
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (squaredDistance(p.getLocation(),event.getEntity().getLocation()) <= maxDist * maxDist) {
                giveAdv(p, "enviroment");
            }
        }
    }

    @EventHandler
    public void socialexperement(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        if (event.getEntity().getKiller() == event.getEntity()) return;
        if (!hasAdv(event.getEntity(), "socialexperement")) {
            giveAdv(event.getEntity(), "socialexperement");
        }
    }

    @EventHandler
    public void nightinvise(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();
        if (!hasAdv(killer, "nightinvise")) {
            File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + killer.getName() + ".yml");
            FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
            List<String> count = prg.getStringList("player.killed");
            if (count.size() == 9) {
                giveAdv(killer, "nightinvise");
            } else {
                if (count.contains(event.getEntity().getName())) return;
                count.add(event.getEntity().getName());
                prg.set("player.killed", count);
                try {
                    prg.save(prg_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void forsazh(PlayerMoveEvent event) {
        if (!hasAdv(event.getPlayer(), "forsazh")) {
            if(event.getPlayer().getVehicle() == null) return;
            if (event.getPlayer().getVehicle().getType() == EntityType.BOAT) {
                Location loc = event.getPlayer().getLocation();
                if (event.getPlayer().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ()).getType() != Material.PACKED_ICE) return;
                giveAdv(event.getPlayer(), "forsazh");
            }
        }
    }

    @EventHandler
    public void onek(PlayerMoveEvent event) {
        if (!hasAdv(event.getPlayer(), "onek")) {
            Location loc = event.getPlayer().getLocation();
            if (loc.getX() > 1000 || loc.getX() < -1000 || loc.getZ() > 1000 || loc.getZ() < -1000) {
                giveAdv(event.getPlayer(), "onek");
            }
        }
    }

    @EventHandler
    public void diedwither(PlayerDeathEvent event) {
        if (!hasAdv(event.getEntity(), "diedwither")) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " withered away")) return;
            giveAdv(event.getEntity(), "diedwither");
        }
    }

    @EventHandler
    public void punchfsg(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player maks = (Player) event.getEntity();
                Player damager = (Player) event.getDamager();
                if (!hasAdv(damager, "punchfsg")) {
                    if (!maks.getName().equals("MaksMaruS_")) return;
                    giveAdv(damager, "punchfsg");
                }
            }
        }
    }

//    @EventHandler
//    public void peper(ChattyMessageEvent event) {
//        if (!event.getChat().getName().equals("global")) return;
//        if (!hasAdv(event.getPlayer(), "peper")) {
//            if (event.getMessage().equals("Пепер легенда!") || event.getMessage().equals("!Пепер легенда!"))
//            giveAdv(event.getPlayer(), "peper");
//        }
//    }

    @EventHandler
    public void reznya(PlayerExpChangeEvent event) {
        if (event.getPlayer().getLevel() >= 100) {
            if (!hasAdv(event.getPlayer(), "reznya")) {
                giveAdv(event.getPlayer(), "reznya");
            }
        }
    }

    @EventHandler
    public void atom(PlayerDeathEvent event) {
        if (!hasAdv(event.getEntity(), "atom")) {
            if (event.getDeathMessage() == null) return;
            if (event.getEntity().getKiller() == null) return;
            if (event.getDeathMessage().equals(event.getEntity().getName() + " blew up") ||
                    event.getDeathMessage().equals(event.getEntity().getName() + " was blown up by " + event.getEntity().getKiller().getName())) {
                giveAdv(event.getEntity(), "atom");
            }
        }
    }

    @EventHandler
    public void diedvoid(PlayerDeathEvent event) {
        if (!hasAdv(event.getEntity(), "diedvoid")) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " fell out of the world")) return;
            giveAdv(event.getEntity(), "diedvoid");
        }
    }

    @EventHandler
    public void diedzombie(PlayerDeathEvent event) {
        if (!hasAdv(event.getEntity(), "diedzombie")) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " was slain by Zombie")) return;
            if (!event.getEntity().getWorld().equals(Bukkit.getWorld("world_nether"))) return;
            giveAdv(event.getEntity(), "diedzombie");
        }
    }

    @EventHandler
    public void zakviel(PlayerDeathEvent event) {
        if (!hasAdv(event.getEntity(), "zakviel")) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " fell from a high place")) return;
            if (event.getEntity().getInventory().getItemInMainHand().getType() != Material.WATER_BUCKET) return;
            giveAdv(event.getEntity(), "zakviel");
        }
    }

    @EventHandler
    public void mogilshik(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Skeleton) {
            if (event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                if (!hasAdv(damager, "mogilshik")) {
                    if (damager.getInventory().getItemInMainHand().getType() != Material.IRON_SHOVEL) return;
                    giveAdv(damager, "mogilshik");
                }
            }
        }
    }

    @EventHandler
    public void aquaman(PlayerDeathEvent event) {
        if (!hasAdv(event.getEntity(), "aquaman")) {
            if (event.getDeathMessage() == null) return;
            if (!event.getDeathMessage().equals(event.getEntity().getName() + " drowned")) return;
            giveAdv(event.getEntity(), "aquaman");
        }
    }

    @EventHandler
    public void sky(PlayerMoveEvent event) {
        if (!hasAdv(event.getPlayer(), "sky")) {
            int y = event.getPlayer().getLocation().getBlockY();
            if (y < 1000) return;
            giveAdv(event.getPlayer(), "sky");
        }
    }

    @EventHandler()
    public void sonic(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.POTION) return;
        PotionMeta meta = (PotionMeta) event.getItem().getItemMeta();
        if (meta == null) return;
        PotionData data = meta.getBasePotionData();
        if (data.getType() != PotionType.SPEED) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals("энергетик")) return;
        if (!hasAdv(event.getPlayer(), "sonic")) {
            giveAdv(event.getPlayer(), "sonic");
        }
    }

    @EventHandler
    public void turtle(PlayerToggleSneakEvent event) {
        if (!hasAdv(event.getPlayer(), "turtle")) {
            if (!event.getPlayer().isSneaking()) return;
            int sneaking = event.getPlayer().getStatistic(Statistic.CROUCH_ONE_CM);
            if (sneaking < 100000) return;
            giveAdv(event.getPlayer(), "turtle");
        }
    }

    @EventHandler
    public void areyouwinningson(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (!hasAdv(p, "areyouwinningson")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.GOLDEN_HOE) return;
            if (event.getInventory().getType() != InventoryType.WORKBENCH) return;
            if (event.getSlotType() != InventoryType.SlotType.RESULT) return;
            giveAdv(p, "areyouwinningson");
        }
    }

    @EventHandler
    public void mylittlebag(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (!hasAdv(p, "mylittlebag")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.BUNDLE) return;
            if (event.getInventory().getType() != InventoryType.WORKBENCH) return;
            if (event.getSlotType() != InventoryType.SlotType.RESULT) return;
            giveAdv(p, "mylittlebag");
        }
    }

    @EventHandler
    public void colosstitan(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player killer = event.getEntity().getKiller();

        int one = event.getEntity().getLocation().getBlockY();
        int two = killer.getLocation().getBlockY();
        int three = 0;
        if (one > two) three = one - two;
        else if (two > one) three = two - one;
        if (59 > three) return;

        if (!hasAdv(killer, "colosstitan")) {
            giveAdv(killer, "colosstitan");
        }
    }


    Map<String, Integer> afkTime = new HashMap<>();

//    @EventHandler
//    public void onPlayerJoin(PlayerJoinEvent event) {
//        afkTime.put(event.getPlayer().getName(), 0);
//    }
//
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event) {
//        afkTime.put(event.getPlayer().getName(), 0);
//    }
//
//    public void afkTimer(){
//        Bukkit.getServer().getScheduler().runTaskTimer(plugin, ()->{
//            for (Player p : Bukkit.getOnlinePlayers()) {
//                afkTime.put(p.getName(),afkTime.get(p.getName())+1);
//                if(afkTime.get(p.getName())>=720) {
//                    giveAdv(p, "afk");
//                    afkTime.put(p.getName(), 0);
//                }
//            }
//        }, 50, 100);
//    }
}