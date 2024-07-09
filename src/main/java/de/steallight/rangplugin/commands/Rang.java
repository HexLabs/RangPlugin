package de.steallight.rangplugin.commands;

import de.steallight.rangplugin.LuckPermsHandler;
import de.steallight.rangplugin.RangPlugin;
import de.steallight.rangplugin.messaging.MessageFormatter;
import jdk.internal.net.http.common.ImmutableExtendedSSLSession;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Rang implements CommandExecutor, TabCompleter {

    MessageFormatter messageFormatter = RangPlugin.getPlugin().getMessageFormatter();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            //check for the right command
            if (cmd.getName().equalsIgnoreCase("rang")) {
                Player p = (Player) sender;


                //check player permission to set Ranks or is Op
                if (p.hasPermission("rang.set") || p.isOp()) {
                    if (args.length == 2) {
                        String groupname = args[1];
                        String playername = args[0];
                        //check if player exist
                        if (Bukkit.getPlayer(playername) == null) {
                            p.sendMessage(messageFormatter.format(true, "error.player-not-exist"));
                        } else if (playername.equals(p.getName())) {
                            p.sendMessage(messageFormatter.format(true,"error.self-set"));
                        } else if (!LuckPermsHandler.isAvailable(groupname)) {
                            p.sendMessage(messageFormatter.format(true, "error.group-not-exist"));
                        } else {
                            Player pr = Bukkit.getPlayer(playername);
                            User player = LuckPermsHandler.getPlayer(pr);

                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                    "lp user " + playername + " parent set " + groupname);
                            pr.kickPlayer("§7Neuer Rang: §c" + groupname);
                            p.sendMessage(messageFormatter.prefix("§aDer Spieler §c" + playername + "§a wurde erfolgreich zur Gruppe §c" + groupname + "§a hinzugefügt!"));

                        }
                    } else {
                        p.sendMessage(messageFormatter.format(true, "error.wrong-usage"));
                    }
                } else {
                    p.sendMessage(messageFormatter.format(true, "error.no-permission"));
                }
            }
        } else if (cmd.getName().equalsIgnoreCase("rang")) {


            if (args.length == 2) {
                String groupname = args[1];
                String playername = args[0];
                if (Bukkit.getPlayer(playername) == null) {
                    Bukkit.getConsoleSender().sendMessage(messageFormatter.format(true, "error.player-not-exist"));
                } else if (!LuckPermsHandler.isAvailable(groupname)) {
                    Bukkit.getConsoleSender().sendMessage(messageFormatter.format(true, "error.group-not-exist"));
                } else {
                    Player pr = Bukkit.getPlayer(playername);
                    User player = LuckPermsHandler.getPlayer(pr);


                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            "lp user " + playername + " parent set " + groupname);
                    pr.kickPlayer("§7Neuer Rang: §c" + groupname);
                    Bukkit.getConsoleSender().sendMessage(messageFormatter.prefix("§aDer Spieler §c" + playername + "§a wurde erfolgreich zur Gruppe§c " + groupname + "§a hinzugefügt!"));

                }
            } else {
                Bukkit.getConsoleSender().sendMessage(messageFormatter.format(true, "error.wrong-usage"));
            }

        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> tabComplete = new ArrayList<>();
        if (args.length == 0) return tabComplete;
        if (args.length == 1) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                tabComplete.add(all.getName());
            }

        } else if (args.length == 2) {

            Set<Group> groups = LuckPermsHandler.getAllGroups();


            for (Group group : groups) {
                tabComplete.add(group.getName());
            }
        }
        ArrayList<String> completerList = new ArrayList<>();
        String currentarg = args[args.length - 1].toLowerCase();
        for (String s : tabComplete) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(currentarg)) {
                completerList.add(s);
            }
        }


        return completerList;
    }
}

