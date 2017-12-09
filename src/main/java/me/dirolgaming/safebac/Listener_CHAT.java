package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

final class Listener_CHAT implements Listener {
    private final Main cac;

    Listener_CHAT(Main cac) {
        this.cac = cac;
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if(!(e.getSender() instanceof ProxiedPlayer))
            return;

        if (e.isCommand())
            return;
        
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String msg = e.getMessage();
        
        if(!cac.actlist.contains(p))
            return;

        e.setCancelled(true);
        
        for (ProxiedPlayer ev : ProxyServer.getInstance().getPlayers()) {
            if (ev.hasPermission("vmcac.act") || ev.hasPermission("vmcac.*")) {
                TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("adminchat-format").replace("%player%", p.getName()).replace("%message%", msg))));
                if (cac.getConfig().getBoolean("enable-adminchat-hover")) {
                    evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("enable-adminchat-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
                }
                if (cac.getConfig().getBoolean("enable-adminchat-click")) {
                    if (cac.getConfig().getBoolean("enable-adminchat-click.enable-url")) {
                        evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, cac.getConfig().getString("enable-adminchat-click.enable-url.url")));
                    }

                    if (cac.getConfig().getBoolean("enable-adminchat-click.enable-command")) {
                        evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cac.getConfig().getString("enable-adminchat-click.enable-command.command").replace("%player%", p.getName()).replace("%server%", p.getServer().getInfo().getName())));
                    }

                }
                ev.sendMessage(evac);
                return;
            }
        }
    }
}
