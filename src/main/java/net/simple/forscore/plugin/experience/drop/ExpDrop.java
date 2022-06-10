//package net.simple.forscore.plugin.experience.drop;
//
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.event.entity.PlayerDeathEvent;
//
//public class ExpDrop implements Listener {
//    @EventHandler
//    public void onDeath(PlayerDeathEvent event) {
//        int newdrop = event.getEntity().getTotalExperience() / 4;
//        event.setDroppedExp(newdrop);
//    }
//}
