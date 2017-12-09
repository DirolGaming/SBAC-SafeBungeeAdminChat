package me.DirolGaming.SafeBAC;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Command_HELPOP extends Command{
    public Main cac;

    public Command_HELPOP(Main cac) {
        super("helpop", null, new String[] { "hp" });
        this.cac = cac;
    }

    private String getMessage(String[] args) {
        StringBuilder acb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            acb.append(args[i]).append(" ");
        }
        return acb.toString();
    }

    public void execute(CommandSender s, String[] args) {
        if (!this.cac.getConfig().getBoolean("enable-helpop")) {
            return;
        }
            if (!(s instanceof ProxiedPlayer)) {
                s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("console-message"))));
                return;
            }

            ProxiedPlayer p = (ProxiedPlayer) s;
            for (ProxiedPlayer ev : ProxyServer.getInstance().getPlayers()) {
                if (!p.hasPermission("sbac.helpop")) {
                    p.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("no-permission")))));
                    return;
                }

                if (args.length < 1) {
                    p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("invalid-usage"))));
                    return;
                }

                if (ev.hasPermission("sbac.helpop.receive")) {
                    TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("helpop-format").replace("%player%", p.getName()).replace("%message%", getMessage(args)))));
                    TextComponent evhp = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("helpop-received").replace("%player%", p.getName()).replace("%message%", getMessage(args)))));

                    if (this.cac.getConfig().getBoolean("helpop-hover.enable")) {
                        evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("helpop-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
                    }
                    if (this.cac.getConfig().getBoolean("helpop-click.enable")) {
                        if (this.cac.getConfig().getBoolean("helpop-click.url.enable")) {
                            evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.cac.getConfig().getString("helpop-click.url.url")));
                        }
                        if (this.cac.getConfig().getBoolean("helpop-click.command.enable")) {
                            evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.cac.getConfig().getString("helpop-click.command.command").replace("%server%", p.getServer().getInfo().getName())));
                        }
                    }

                    if (this.cac.getConfig().getBoolean("helpop-received-hover.enable")) {
                        evhp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("helpop-received-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
                    }
                    if (this.cac.getConfig().getBoolean("helpop-received-click.enable")) {
                        if (this.cac.getConfig().getBoolean("helpop-received-click.url.url")) {
                            evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.cac.getConfig().getString("helpop-received-click.url.url")));
                        }

                        if (this.cac.getConfig().getBoolean("helpop-received-click.command.enable")) {
                            evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.cac.getConfig().getString("helpop-received-click.command.command").replace("%server%", p.getServer().getInfo().getName())));
                        }

                    }
                    ev.sendMessage(evac);
                    p.sendMessage(evhp);
                }
            }
        }
}
