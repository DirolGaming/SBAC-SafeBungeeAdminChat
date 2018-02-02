package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

final class Command_MCT extends Command {
private final Main cac;

        Command_MCT(Main cac) {
        super("mctoggle", null, "mct");
        this.cac = cac;
        }

@Override
public void execute(CommandSender s, String[] args) {
    if (!(s instanceof ProxiedPlayer)) {
        s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("console-message"))));
        return;
    }

    if (!s.hasPermission("sbac.mct")) {
        s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("no-permission")))));
        return;
    }

    ProxiedPlayer player = (ProxiedPlayer) s;

    if (cac.mctlist.contains(player)) {
        cac.mctlist.remove(player);
        s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("modchat-toggle-disabled")))));
    } else {
        cac.mctlist.add(player);
        s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("modchat-toggle-enabled")))));
        }
    }
}