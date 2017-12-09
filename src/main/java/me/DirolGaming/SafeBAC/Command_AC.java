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

public class Command_AC extends Command {
    public Main cac;

    private String getMessage(String[] args) {
        StringBuilder acb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            acb.append(args[i]).append(" ");
        }
        return acb.toString();
    }

    public Command_AC(Main cac) {
        super("adminchat", null, new String[] { "ac" });
        this.cac = cac;
    }

    public void execute(CommandSender s, String[] args)
    {
        if (!(s instanceof ProxiedPlayer)) {
            s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("console-message"))));
            return;
        }

        ProxiedPlayer p = (ProxiedPlayer)s;
        for (ProxiedPlayer ev : ProxyServer.getInstance().getPlayers())
        {
            if (!p.hasPermission("sbac.ac"))
            {
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("no-permission"))));
                return;
            }

            if (args.length < 1)
            {
                p.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("invalid-usage"))));
                return;
            }

            if (ev.hasPermission("sbac.ac")) {
                TextComponent evac = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-format").replace("%player%", p.getName()).replace("%message%", getMessage(args)))));
                if (this.cac.getConfig().getBoolean("adminchat-hover.enable")) {
                    evac.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-hover.message").replace("%server%", p.getServer().getInfo().getName()))).create()));
                }

                if (this.cac.getConfig().getBoolean("adminchat-click.enable")) {
                    if (this.cac.getConfig().getBoolean("adminchat-click.url.enable-")) {
                        evac.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.cac.getConfig().getString("adminchat-click.url.url")));
                    }

                    if (this.cac.getConfig().getBoolean("adminchat-click.command.enable")) {
                        evac.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.cac.getConfig().getString("adminchat-click.command.command").replace("%player%", p.getName()).replace("%server%", p.getServer().getInfo().getName())));
                    }

                }
                ev.sendMessage(evac);
            }
        }
    }

}
