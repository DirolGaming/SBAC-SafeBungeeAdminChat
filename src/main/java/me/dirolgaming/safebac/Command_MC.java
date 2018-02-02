package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static me.dirolgaming.safebac.Utils.broadcastModchatMessage;
import static me.dirolgaming.safebac.Utils.buildMessage;

public class Command_MC extends Command  {
        private final Main cac;

        Command_MC(Main cac) {
            super("modchat", null, "mc");
            this.cac = cac;
        }

        @Override
        public void execute(CommandSender s, String[] args) {
            if (!(s instanceof ProxiedPlayer)) {
                s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("console-message"))));
                return;
            }

            ProxiedPlayer p = (ProxiedPlayer) s;

            if (!p.hasPermission("sbac.mc")) {
                p.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("no-permission")))));
                return;
            }

            if (args.length < 1) {
                p.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("invalid-usage")))));
                return;
            }

            broadcastModchatMessage(cac, p, buildMessage(args));
        }
}
