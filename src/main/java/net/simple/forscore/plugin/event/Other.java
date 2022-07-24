//package net.simple.forscore.plugin.event;
//
//import net.simple.forscore.plugin.Main;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.EntityResurrectEvent;
//import org.bukkit.event.player.*;
//import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
//
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//
//public class Other implements Listener {
//
//    public Other(Main plugin){
//        this.plugin = plugin;
//    }
//    private final Main plugin;
//
//    @EventHandler
//    public void onPlayerMove(PlayerMoveEvent event){
//        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW,1000000000,0,true,false,false));
//    }
//
////    @EventHandler
////    public void playerPortal(PlayerPortalEvent event){
////        if(event.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world")) event.setCancelled(true);
////    }
//    @EventHandler
//    public void playerOpenEC(PlayerInteractEvent event){
//        if(event.getClickedBlock() == null) return;
//        if(event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) event.getPlayer().breakBlock(event.getClickedBlock());
//    }
//    @EventHandler
//    public void playerStepOnDirtPath(PlayerMoveEvent event){
//        Location loc = event.getPlayer().getLocation();
//        Material b = event.getPlayer().getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType();
//        if(b.equals(Material.DIRT_PATH)) event.getPlayer().setWalkSpeed(0.26f);
//        else event.getPlayer().setWalkSpeed(0.2f);
//    }
//    @EventHandler
//    public void deleteTrident(PlayerItemHeldEvent event){
//        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
//        ItemStack item2 = event.getPlayer().getInventory().getItemInOffHand();
//        ItemMeta meta = item.getItemMeta();
//        ItemMeta meta2 = item2.getItemMeta();
//        if(meta !=null) if(meta.hasEnchant(Enchantment.RIPTIDE)){
//            meta.removeEnchant(Enchantment.RIPTIDE);
//            item.setItemMeta(meta);
//        }
//        if(meta2 !=null) if(meta2.hasEnchant(Enchantment.RIPTIDE)){
//            meta2.removeEnchant(Enchantment.RIPTIDE);
//            item2.setItemMeta(meta2);
//        }
//    }
//    @EventHandler
//    public void disableTotems(EntityResurrectEvent event){
//        event.setCancelled(true);
//    }
//}