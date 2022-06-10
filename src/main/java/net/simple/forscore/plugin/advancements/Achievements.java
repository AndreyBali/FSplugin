package net.simple.forscore.plugin.advancements;

import net.simple.forscore.plugin.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Achievements implements Listener{
    private final Main plugin;
    public Achievements(Main plugin) {
        this.plugin = plugin;
        advScheduler();
        afkTimer();
    }
    Map<String, Boolean> advs = new HashMap<>();

    public void advScheduler(){
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, ()->{
            for (Player p : Bukkit.getOnlinePlayers()) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + p.getUniqueId() + ".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                for (String x : getAdvArray()) {
                    adv.set("forscore." + x, advs.get(p.getName() + "." + x));
                }
                try {
                    adv.save(adv_file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 50, 50);
    }

    public boolean hasAdv(Player player, String advName) {
        return advs.get(player.getName()+"."+advName);
    }
    public void giveAdv(Player player, String advName){
        advs.put(player.getName()+"."+advName, true);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute as " + player.getName() + " as @s[advancements={forscore:" + advName + "=false}] run advancement grant "+ player.getName() +" only forscore:"+advName);
        });
    }
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


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getUniqueId() + ".yml");
        FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
        for (String x : getAdvArray()) {
            advs.put(event.getPlayer().getName() + "." + x, adv.getBoolean("forscore." + x));
        }
    }

    @EventHandler
    public void start(PlayerJoinEvent event) {
        Bukkit.getServer().getScheduler().runTaskLater(plugin, ()->{
            if (!hasAdv(event.getPlayer(), "start")) {
                giveAdv(event.getPlayer(), "start");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, ()->{
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "advancement grant "+ event.getPlayer().getName() +" only forscore:forscore_root");
                });
            }
        },7);
    }

    @EventHandler
    public void ezra8(PlayerDeathEvent event) {
        String nick = event.getEntity().getName();
        if (!nick.equals("EZRA_8")) return;
        if(event.getEntity().getKiller()==null) return;
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;
        if (!hasAdv(killer, "ezra8") && killer.getPlayer()!=null) {
            giveAdv(killer, "ezra8");
        }
    }

    @EventHandler
    public void leonid(PlayerInteractEntityEvent event) {
        if (!hasAdv(event.getPlayer(), "leonid")) {
            if (event.getRightClicked() instanceof Chicken) {
                Bukkit.getServer().getScheduler().runTaskLater(plugin, ()->{
                    if (!event.getRightClicked().getCustomName().equals("Леонид")) return;
                giveAdv(event.getPlayer(), "leonid");
                },2);
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
        if(event.getEntity().getKiller()==null) return;
        Player killer = event.getEntity().getKiller();
        if (!hasAdv(killer, "ban")) {
            giveAdv(killer, "ban");
        }
    }

    @EventHandler
    public void cavern(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                File adv_file = new File(plugin.getDataFolder() + File.separator + "/advancements/" + event.getPlayer().getUniqueId() + ".yml");
                FileConfiguration adv = YamlConfiguration.loadConfiguration(adv_file);
                if (!adv.getBoolean("forscore.cavern")) {
                    int y = event.getPlayer().getLocation().getBlockY();
                    if (y != -60) return;
                    giveAdv(event.getPlayer(), "cavern");
                }
            }
        });
    }

    @EventHandler
    public void momsanarchy(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().toString().equals("BUILD_WITHER")) return;
        int maxDist = 10;// whatever
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().distanceSquared(event.getEntity().getLocation()) <= maxDist) {
                if (!hasAdv(p, "momsanarchy")) {
                    File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + p.getName() +".yml");
                    FileConfiguration prg = YamlConfiguration.loadConfiguration(prg_file);
                    int count = prg.getInt("wither.spawncount");
                    if (count == 99) {
                        giveAdv(p,"momsanarchy");
                    } else {
                        prg.set("wither.spawncount", count + 1);
                        try {
                            prg.save(prg_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
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
                if (p.getLocation().distanceSquared(event.getEntity().getLocation()) <= maxDist) {
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
            if (p.getLocation().distanceSquared(event.getEntity().getLocation()) <= maxDist) {
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
            File prg_file = new File(plugin.getDataFolder() + File.separator + "/progress/" + killer.getName() +".yml");
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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (!hasAdv(event.getPlayer(), "forsazh")) {
                    if (!event.getPlayer().isInsideVehicle()) return;
                    if (event.getPlayer().getVehicle() instanceof Boat) {
                        Location loc = event.getPlayer().getLocation();
                        if (event.getPlayer().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType() != Material.PACKED_ICE) return;
                        giveAdv(event.getPlayer(), "forsazh");
                    }
                }
            }
        });
    }

    @EventHandler
    public void onek(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (!hasAdv(event.getPlayer(),"onek")) {
                    Location loc = event.getPlayer().getLocation();
                    if (loc.getX() > 1000 || loc.getX() < -1000 || loc.getZ() > 1000 || loc.getZ() < -1000) {
                        giveAdv(event.getPlayer(),"onek");
                    }
                }
            }
        });
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
                if (!hasAdv(damager,"punchfsg")) {
                    if (!maks.getName().equals("MaksMaruS_")) return;
                    giveAdv(damager,"punchfsg");
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
            if(event.getEntity().getKiller()==null) return;
            if (event.getDeathMessage().equals(event.getEntity().getName() + " blew up") ||
                    event.getDeathMessage().equals(event.getEntity().getName() + " was blown up by "+event.getEntity().getKiller().getName())){
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
            if (event.getEntity().getItemInHand().getType() != Material.WATER_BUCKET) return;
            giveAdv(event.getEntity(), "zakviel");
        }
    }

    @EventHandler
    public void mogilshik(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Skeleton) {
            if (event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                if (!hasAdv(damager, "mogilshik")) {
                    if (damager.getItemInHand().getType() != Material.IRON_SHOVEL) return;
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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (!hasAdv(event.getPlayer(), "sky")) {
                    int y = event.getPlayer().getLocation().getBlockY();
                    if (y < 1000) return;
                    giveAdv(event.getPlayer(), "sky");
                }
            }
        });
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
        if (!hasAdv(p,"areyouwinningson")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.GOLDEN_HOE) return;
            if (event.getInventory().getType() != InventoryType.WORKBENCH) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;
            giveAdv(p,"areyouwinningson");
        }
    }

    @EventHandler
    public void mylittlebag(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (!hasAdv(p, "mylittlebag")) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() != Material.BUNDLE) return;
            if (event.getInventory().getType() != InventoryType.WORKBENCH) return;
            if(event.getSlotType() != InventoryType.SlotType.RESULT) return;
            giveAdv(p, "mylittlebag");
        }
    }

    @EventHandler
    public void colosstitan(PlayerDeathEvent event) {
        if(event.getEntity().getKiller()==null) return;
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        afkTime.put(event.getPlayer().getName(), 0);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                afkTime.put(event.getPlayer().getName(), 0);
            }
        });
    }

    public void afkTimer(){
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, ()->{
            for (Player p : Bukkit.getOnlinePlayers()) {
                afkTime.put(p.getName(),afkTime.get(p.getName())+1);
                if(afkTime.get(p.getName())>=720) {
                    giveAdv(p, "afk");
                    afkTime.put(p.getName(), 0);
                }
            }
        }, 50, 100);
    }
}