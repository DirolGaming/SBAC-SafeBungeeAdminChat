package me.dirolgaming.safebac;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.event.EventHandler;
import java.util.ArrayList;

public class Command_ACT  extends Command {
    public Main cac;
    public static ArrayList<String> actlist = new ArrayList();

    public Command_ACT(Main cac)
    {
        super("actoggle", null, new String[] { "act" });
        this.cac = cac;
    }

    @EventHandler
    public void execute(CommandSender s, String[] args)
    {
        if (!(s instanceof ProxiedPlayer)) {
            s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("console-message"))));
        }
        if (!s.hasPermission("sbac.act")) {
            s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("no-permission")))));
            }
        if (s.hasPermission("sbac.act")) {
            if (actlist.contains(s.getName())) {
                actlist.remove(s.getName());
                s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-toggle-disabled")))));
            }
            else {
                actlist.add(s.getName());
                s.sendMessage(new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', this.cac.getConfig().getString("adminchat-toggle-enabled")))));
            }
        }
    }
}
