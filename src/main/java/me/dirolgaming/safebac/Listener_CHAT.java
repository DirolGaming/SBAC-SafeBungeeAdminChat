package me.dirolgaming.safebac;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import static me.dirolgaming.safebac.Utils.broadcastAdminchatMessage;
import static me.dirolgaming.safebac.Utils.broadcastModchatMessage;

public final class Listener_CHAT implements Listener {
    private final Main cac;

    Listener_CHAT(Main cac) {
        this.cac = cac;
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCancelled())
            return;

        if (e.isCommand())
            return;

        if (!(e.getSender() instanceof ProxiedPlayer))
            return;

        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String msg = e.getMessage();
        e.setCancelled(true);
        if (cac.actlist.contains(p)) {
            broadcastAdminchatMessage(cac, p, msg);
        }
        if (cac.mctlist.contains(p)) {
            broadcastModchatMessage(cac, p, msg);
        }
        if (!cac.actlist.contains(p))
            return;
        if (!cac.mctlist.contains(p))
            return;

    }
}
