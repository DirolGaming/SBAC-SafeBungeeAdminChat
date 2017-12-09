package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static me.dirolgaming.safebac.Utils.buildMessage;

final class Command_AC extends Command {
    private final Main cac;

    Command_AC(Main cac) {
        super("adminchat", null, "ac");
        this.cac = cac;
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof ProxiedPlayer)) {
            s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("console-message"))));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) s;

        if (!p.hasPermission("sbac.ac")) {
            p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("no-permission"))));
            return;
        }

        if (args.length < 1) {
            p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("invalid-usage"))));
            return;
        }

        TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("adminchat-format").replace("%player%", p.getName()).replace("%message%", buildMessage(args)))));

        if (cac.getConfig().getBoolean("adminchat-hover.enable")) {
            evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("adminchat-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
        }

        if (cac.getConfig().getBoolean("adminchat-click.enable")) {
            if (cac.getConfig().getBoolean("adminchat-click.url.enable-")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, cac.getConfig().getString("adminchat-click.url.url")));
            }

            if (cac.getConfig().getBoolean("adminchat-click.command.enable")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cac.getConfig().getString("adminchat-click.command.command").replace("%player%", p.getName()).replace("%server%", p.getServer().getInfo().getName())));
            }
        }

        for (ProxiedPlayer ev : cac.getProxy().getPlayers()) {
            if (ev.hasPermission("sbac.ac")) {
                ev.sendMessage(evac);
            }
        }
    }
}
