package net.simple.forscore.plugin.experience.drop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ExpDrop implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        double newdrop = getEpx(event.getEntity().getLevel()/2);
        event.setDroppedExp((int)newdrop);
    }
    public double getEpx(int lvl){
        if(lvl<16) return lvl*lvl+6*lvl;
        if(lvl<32) return 2.5*lvl*lvl-40.5*lvl+360;
        if(lvl>31) return 4.5 * lvl*lvl - 162.5 * lvl + 2220;
        return lvl;
    }
}
