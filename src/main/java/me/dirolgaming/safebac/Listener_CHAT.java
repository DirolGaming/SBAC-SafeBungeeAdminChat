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

public class Listener_CHAT implements Listener {
    public Main cac;
    public Listener_CHAT(Main cac) {
        this.cac = cac;
    }

    @EventHandler
    public void onChat(ChatEvent e)
    {
        ProxiedPlayer p = (ProxiedPlayer)e.getSender();
        String msg = e.getMessage();
        if (e.isCommand()) {
            return;
        }
        for (ProxiedPlayer ev : ProxyServer.getInstance().getPlayers()) {
            if ((Command_ACT.actlist.contains(p.getName())) && ((ev.hasPermission("vmcac.act")) || (ev.hasPermission("vmcac.*")))) {
                e.setCancelled(true);
                TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-format").replace("%player%", p.getName()).replace("%message%", e.getMessage()))));
                if (this.cac.getConfig().getBoolean("enable-adminchat-hover")) {
                    evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("enable-adminchat-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
                }
                if (this.cac.getConfig().getBoolean("enable-adminchat-click")) {
                    if (this.cac.getConfig().getBoolean("enable-adminchat-click.enable-url")) {
                        evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.cac.getConfig().getString("enable-adminchat-click.enable-url.url")));
                    }

                    if (this.cac.getConfig().getBoolean("enable-adminchat-click.enable-command")) {
                        evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.cac.getConfig().getString("enable-adminchat-click.enable-command.command").replace("%player%", p.getName()).replace("%server%", p.getServer().getInfo().getName())));
                    }

                }
                ev.sendMessage(evac);
                return;
            }
        }
    }
}
