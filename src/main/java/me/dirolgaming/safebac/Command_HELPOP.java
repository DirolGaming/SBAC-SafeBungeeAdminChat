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

final class Command_HELPOP extends Command{
    private final Main cac;

    Command_HELPOP(Main cac) {
        super("helpop", null, "hp");
        this.cac = cac;
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!cac.getConfig().getBoolean("enable-helpop")) {
            return;
        }

        if (!(s instanceof ProxiedPlayer)) {
            s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("console-message"))));
            return;
        }

        if (!s.hasPermission("sbac.helpop")) {
            s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("no-permission")))));
            return;
        }

        if (args.length < 1) {
            s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("invalid-usage"))));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer) s;

        TextComponent evhp = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("helpop-received").replace("%player%", p.getName()).replace("%message%", buildMessage(args)))));
        TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("helpop-format").replace("%player%", p.getName()).replace("%message%", buildMessage(args)))));

        if (cac.getConfig().getBoolean("helpop-hover.enable")) {
            evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("helpop-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
        }

        if (cac.getConfig().getBoolean("helpop-click.enable")) {
            if (cac.getConfig().getBoolean("helpop-click.url.enable")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, cac.getConfig().getString("helpop-click.url.url")));
            }

            if (cac.getConfig().getBoolean("helpop-click.command.enable")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cac.getConfig().getString("helpop-click.command.command").replace("%server%", p.getServer().getInfo().getName())));
            }
        }

        if (cac.getConfig().getBoolean("helpop-received-hover.enable")) {
            evhp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("helpop-received-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
        }

        if (cac.getConfig().getBoolean("helpop-received-click.enable")) {
            if (cac.getConfig().getBoolean("helpop-received-click.url.url")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, cac.getConfig().getString("helpop-received-click.url.url")));
            }

            if (cac.getConfig().getBoolean("helpop-received-click.command.enable")) {
                evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cac.getConfig().getString("helpop-received-click.command.command").replace("%server%", p.getServer().getInfo().getName())));
            }
        }

        for (ProxiedPlayer ev : cac.getProxy().getPlayers()) {
            if (ev.hasPermission("sbac.helpop.receive")) {
                ev.sendMessage(evac);
            }
        }

        s.sendMessage(evhp);
    }
}
