package net.simple.forscore.plugin.raidfix;

import net.simple.forscore.plugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

import java.util.HashMap;

public class RaidFix implements Listener {
    public static HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    int cooldownTime = 12000;

    @EventHandler
    public void onTriggerRaid(RaidTriggerEvent event) {
        cooldowns.put("x", (long) cooldownTime);

        if(cooldowns.containsKey(event.getPlayer().getName())) {
            long secondsLeft = ((cooldowns.get(event.getPlayer().getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
            if(secondsLeft>0) {
                event.setCancelled(true);
                return;
            }
        }
        cooldowns.put(event.getPlayer().getName(), System.currentTimeMillis());
    }

    public long getCooldown(String playerName) {
        return ((cooldowns.get(playerName)/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
    }
}