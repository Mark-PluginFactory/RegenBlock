package org.mark.regenBlock.config;

import com.google.inject.AbstractModule;
import org.mark.regenBlock.command.RegenBlockCommand;
import org.mark.regenBlock.listener.UserRegenBlockInteractListener;
import org.mark.regenBlock.manager.RegenBlockManager;
import org.mark.regenBlock.manager.UserRegenBlockStatusManager;
import org.mark.regenBlock.service.RegenBlockService;
import org.mark.regenBlock.service.UserRegenBlockCoordinateService;


public class InjectorConfig extends AbstractModule {
    @Override
    protected void configure() {
        registerCommands();
        registerListeners();
        registerManagers();
        registerService();
    }

    private void registerListeners() {
        bind(UserRegenBlockInteractListener.class).asEagerSingleton();
    }

    private void registerCommands() {
        bind(RegenBlockCommand.class).asEagerSingleton();
    }

    private void registerManagers() {
        bind(UserRegenBlockStatusManager.class).asEagerSingleton();
        bind(RegenBlockManager.class).asEagerSingleton();
    }


    private void registerService() {
        bind(RegenBlockService.class).asEagerSingleton();
        bind(UserRegenBlockCoordinateService.class).asEagerSingleton();
    }
}
