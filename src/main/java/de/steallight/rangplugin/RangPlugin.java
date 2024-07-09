package de.steallight.rangplugin;

import de.steallight.rangplugin.commands.Rang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class RangPlugin extends JavaPlugin {


    public static String prefix;

    //initialize the config method
    public void config() {


        File c = new File("plugins/RangPlugin", "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(c);
        if (!c.exists()) {

            cfg.options().copyDefaults(true);
            cfg.addDefault("prefix", "&8[&c&bRang&8] ");
            prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("prefix"));


        }
    }


    //start logic for the RangPlugin
    @Override
    public void onEnable() {

        config();
        saveDefaultConfig();

        getCommand("rang").setExecutor(new Rang());
        getCommand("rang").setTabCompleter(new Rang());


        Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §aaktiviert!");

    }

    //stop logic of the RangPlugin
    @Override
    public void onDisable() {


        Bukkit.getConsoleSender().sendMessage(prefix + "§7Plugin erfolgreich §cdeaktiviert!");

    }


}
