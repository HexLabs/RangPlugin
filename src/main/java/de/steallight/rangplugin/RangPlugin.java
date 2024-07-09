package de.steallight.rangplugin;

import de.steallight.rangplugin.commands.Rang;
import de.steallight.rangplugin.messaging.MessageFormatter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RangPlugin extends JavaPlugin {


    private MessageFormatter messageFormatter;

    private static RangPlugin INSTANCE;


    //start logic for the RangPlugin
    @Override
    public void onEnable() {

        messageFormatter = new MessageFormatter();
        INSTANCE = this;

        getCommand("rang").setExecutor(new Rang());
        getCommand("rang").setTabCompleter(new Rang());


        Bukkit.getConsoleSender().sendMessage(messageFormatter.format(true, "enable.plugin-enabled"));

    }

    //stop logic of the RangPlugin
    @Override
    public void onDisable() {


        Bukkit.getConsoleSender().sendMessage(messageFormatter.format(true, "disable.plugin-disabled"));
        messageFormatter = null;
    }

    public static RangPlugin getPlugin() {
        return INSTANCE;
    }

    public MessageFormatter getMessageFormatter() {
        return messageFormatter;
    }


}
