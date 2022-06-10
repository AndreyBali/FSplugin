package net.simple.forscore.plugin.discordsrv;

import github.scarsz.discordsrv.api.ListenerPriority;
import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.AccountLinkedEvent;
import github.scarsz.discordsrv.api.events.DiscordGuildMessageReceivedEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.exceptions.HierarchyException;
import net.simple.forscore.plugin.Main;

public class LinkEvent {
    private Main plugin;
    public LinkEvent(Main plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void accountsLinked(AccountLinkedEvent event) {
        Member member = event.getUser().getJDA().getGuildById("765888901167972352").getMember(event.getUser());
        if (member == null) return;
        if (!member.canInteract(member)) return;
        try {
            member.modifyNickname(event.getPlayer().getName()).queue();
        } catch (HierarchyException e) {
            plugin.getLogger().warning("Can't modify nickname, member has a high role or administrator right.");
        }
    }
}
