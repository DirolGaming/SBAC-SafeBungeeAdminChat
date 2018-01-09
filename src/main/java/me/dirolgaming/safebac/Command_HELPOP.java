package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static me.dirolgaming.safebac.Utils.broadcasthelpopmsg;
import static me.dirolgaming.safebac.Utils.buildMessage;

final class Command_HELPOP extends Command {
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
        broadcasthelpopmsg(cac, p,  s, buildMessage(args));
    }
}
