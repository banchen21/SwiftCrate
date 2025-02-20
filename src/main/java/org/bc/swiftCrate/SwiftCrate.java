package org.bc.swiftCrate;

import org.bc.swiftCrate.api.SwiftCrateAPI;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SwiftCrate extends JavaPlugin {
    static Logger log;
    private DatabaseManager dbManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = this.getLogger();

        dbManager = new DatabaseManager(this);
        ServicesManager sm = getServer().getServicesManager();
        sm.register(SwiftCrateAPI.class, dbManager, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
    }
}
