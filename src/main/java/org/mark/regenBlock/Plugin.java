package org.mark.regenBlock;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.mark.regenBlock.config.InjectorConfig;
import org.mark.regenBlock.manager.RegenBlockManager;

public final class Plugin extends JavaPlugin {

    public static Plugin INSTANCE;
    private Injector injector;

    @Inject
    private RegenBlockManager regenBlockStatusManager;


    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        injectorSetting();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void injectorSetting() {
        injector = Guice.createInjector(new InjectorConfig());

        injector.injectMembers(this);
    }

    public static void setListener(Listener listener) {
        INSTANCE.getServer().getPluginManager().registerEvents(listener, INSTANCE);
    }

    public static void setCommand(String s, CommandExecutor command) {
        INSTANCE.getServer().getPluginCommand(s).setExecutor(command);
    }
}
