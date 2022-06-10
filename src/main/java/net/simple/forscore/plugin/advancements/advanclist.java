package net.simple.forscore.plugin.advancements;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class advanclist {
    public static FileConfiguration advlist(FileConfiguration f) {
        f.set("forscore.start", false);
        f.set("forscore.ezra8", false);
        f.set("forscore.leonid", false);
        f.set("forscore.ban", false);
        f.set("forscore.cavern", false);
        f.set("forscore.momsanarchy", false);
        f.set("forscore.fathercommunism", false);
        f.set("forscore.enviroment", false);
        f.set("forscore.socialexperement", false);
        f.set("forscore.nightinvise", false);
        f.set("forscore.forsazh", false);
        f.set("forscore.onek", false);
        f.set("forscore.diedwither", false);
        f.set("forscore.punchfsg", false);
        f.set("forscore.peper", false);
        f.set("forscore.afk", false);
        f.set("forscore.reznya", false);
        f.set("forscore.atom", false);
        f.set("forscore.diedvoid", false);
        f.set("forscore.diedzombie", false);
        f.set("forscore.zakviel", false);
        f.set("forscore.mogilshik", false);
        f.set("forscore.aquaman", false);
        f.set("forscore.sky", false);
        f.set("forscore.sonic", false);
        f.set("forscore.turtle", false);
        f.set("forscore.areyouwinningson", false);
        f.set("forscore.mylittlebag", false);
        f.set("forscore.colosstitan", false);

        return f;
    }

    public static FileConfiguration prglist(FileConfiguration f) {
        f.set("wither.spawncount", 0);
        List<String> ab = new ArrayList<String>();
        f.set("player.killed", ab);

        return f;
    }
}
