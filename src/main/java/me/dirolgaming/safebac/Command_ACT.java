package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

final class Command_ACT  extends Command {
    private final Main cac;

    Command_ACT(Main cac) {
        super("actoggle", null, "act");
        this.cac = cac;
    }

    @Override
    public void execute(CommandSender s, String[] args) {
        if (!(s instanceof ProxiedPlayer)) {
            s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("console-message"))));
            return;
        }

        if (!s.hasPermission("sbac.act")) {
            s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("no-permission")))));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) s;

        if (cac.actlist.contains(player)) {
            cac.actlist.remove(player);
            s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-toggle-disabled")))));
        } else {
            cac.actlist.add(player);
            s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-toggle-enabled")))));
        }
    }
}
