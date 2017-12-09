package me.DirolGaming.SafeBAC;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

import javax.xml.soap.Text;

public class Command_SAFEBAC extends Command {
    public Main cac;

    public Command_SAFEBAC(Main cac) {
        super("safebac", null, new String[]{"sbac"});
        this.cac = cac;
    }
    public void execute(CommandSender s, String[] args) {
        if (args.length == 0) {
            TextComponent info = new TextComponent("§7Plugin info - §cClick me");
            info.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/sbac-safebungeeadminchat.50530/"));
            info.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("§f§lClick me to open SafeBAC spigot page").create()));
            s.sendMessage(new TextComponent("§7Author: §cDirolGaming"));
            s.sendMessage(info);
            s.sendMessage(new TextComponent("§7Version: " + ChatColor.RED + cac.getDescription().getVersion()));
            s.sendMessage(new TextComponent("§7/ac <msg> -  §cchat with online staff"));
            s.sendMessage(new TextComponent("§7/act - §ctoggle admin chat"));
            s.sendMessage(new TextComponent("§7/helpop <msg> -  §csend a message to online staff"));
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (s.hasPermission("sbac.*")) {
                cac.loadConfig();
                s.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', cac.getConfig().getString("config-reloaded"))));
            }
        }
    }
}
