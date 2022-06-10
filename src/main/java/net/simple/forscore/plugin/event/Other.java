package net.simple.forscore.plugin.event;

import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import net.simple.forscore.plugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.List;
import java.util.Random;

public class Other implements Listener {

    public Other(Main plugin){
        this.plugin = plugin;
    }
    private final Main plugin;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000000,0,true,false,false));
    }


    @EventHandler
    public void playerStepOnDirtPath(PlayerMoveEvent event){
        Location loc = event.getPlayer().getLocation();
        Material b = event.getPlayer().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType();
        if(b.equals(Material.DIRT_PATH)) event.getPlayer().setWalkSpeed(0.26f);
        else event.getPlayer().setWalkSpeed(0.2f);
    }
    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().toString().equals("NATURAL")) {
            File configFile = new File(plugin.getDataFolder() + File.separator + "/event/config.yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            Random random = new Random();
            Location coords = event.getEntity().getLocation();
            if(coords.getBlock().getType().equals(Material.WATER)) return;
            int health = random.ints(20, 50).findFirst().getAsInt();
            int rand = random.ints(1, 100).findFirst().getAsInt();
            if (!event.getEntity().getWorld().getName().equalsIgnoreCase("world")) return;
            event.setCancelled(true);

            int maxmobs = 153;
            long time = coords.getWorld().getTime();
            if(config.contains("fs.other.maxMobs")) maxmobs = config.getInt("fs.other.maxMobs");
            if(time>13000) {
                maxmobs*=2;
                if(coords.getBlockY()<30&&random.nextBoolean()) return;
            }
            if (countmobs(coords.getWorld()) > maxmobs) return;

            ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
            ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
            ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
            ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
            ItemMeta armor = helmet.getItemMeta();
            assert armor != null;
            armor.setDisplayName("UCF");
            helmet.setItemMeta(armor);
            chestplate.setItemMeta(armor);
            leggings.setItemMeta(armor);
            boots.setItemMeta(armor);


            if(rand>50) {
                LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.SKELETON);
                ent.setMaxHealth(health);
                ItemStack bow = new ItemStack(Material.BOW);
                ItemMeta bowMeta = bow.getItemMeta();
                bowMeta.setDisplayName("MDAR-17");
                bowMeta.addEnchant(Enchantment.ARROW_DAMAGE,15,true);
                bow.setItemMeta(bowMeta);

                ent.getEquipment().setHelmet(helmet);
                ent.getEquipment().setChestplate(chestplate);
                ent.getEquipment().setLeggings(leggings);
                ent.getEquipment().setBoots(boots);
                ent.getEquipment().setItemInMainHand(bow);

                ent.getEquipment().setHelmetDropChance(0);
                ent.getEquipment().setChestplateDropChance(0);
                ent.getEquipment().setLeggingsDropChance(0);
                ent.getEquipment().setBootsDropChance(0);
                ent.getEquipment().setItemInMainHandDropChance(0);
            }
            else {
                LivingEntity ent = (LivingEntity) event.getEntity().getWorld().spawnEntity(coords, EntityType.WITHER_SKELETON);
                ent.setMaxHealth(health);
                ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
                ItemMeta swordMeta = sword.getItemMeta();
                swordMeta.setDisplayName("Катана UCF");
                swordMeta.addEnchant(Enchantment.DAMAGE_ALL,10,true);
                sword.setItemMeta(swordMeta);

                ent.getEquipment().setHelmet(helmet);
                ent.getEquipment().setChestplate(chestplate);
                ent.getEquipment().setLeggings(leggings);
                ent.getEquipment().setBoots(boots);
                ent.getEquipment().setItemInMainHand(sword);

                ent.getEquipment().setHelmetDropChance(0);
                ent.getEquipment().setChestplateDropChance(0);
                ent.getEquipment().setLeggingsDropChance(0);
                ent.getEquipment().setBootsDropChance(0);
                ent.getEquipment().setItemInMainHandDropChance(0);
            }
        }
    }

    private int countmobs(World world) {
        int mobcount = 0;
        List<LivingEntity> entities = world.getLivingEntities();
        for (LivingEntity ent : entities) {
            if (ent.getType().equals(EntityType.SKELETON)) mobcount++;
            else if (ent.getType().equals(EntityType.WITHER_SKELETON)) mobcount++;
        }
        return mobcount;
    }
}