package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * @author Mark Vainomaa
 */
final class Utils {
    static String buildMessage(String[] args) {
        StringBuilder acb = new StringBuilder();
        for(String arg : args)
            acb.append(arg).append(" ");
        return acb.toString();
    }

    static void broadcastAdminchatMessage(Main safeBAC, ProxiedPlayer p, String message) {
        TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', safeBAC.getConfig().getString("adminchat-format")
                .replace("%player%", p.getName())
                .replace("%message%", message))));

        if (safeBAC.getConfig().getBoolean("adminchat-hover.enable")) {
            evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', safeBAC.getConfig().getString("adminchat-hover.message")
                    .replace("%server%", p.getServer().getInfo().getName()))).create()));
        }

        if (safeBAC.getConfig().getBoolean("adminchat-click.enable")) {
            if (safeBAC.getConfig().getBoolean("adminchat-click.url.enable-")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, safeBAC.getConfig().getString("adminchat-click.url.url")));
            }

            if (safeBAC.getConfig().getBoolean("adminchat-click.command.enable")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, safeBAC.getConfig().getString("adminchat-click.command.command")
                        .replace("%player%", p.getName())
                        .replace("%server%", p.getServer().getInfo().getName())));
            }
        }

        for (ProxiedPlayer ev : safeBAC.getProxy().getPlayers()) {
            if (ev.hasPermission("sbac.ac")) {
                ev.sendMessage(evac);
            }
        }
    }
}
