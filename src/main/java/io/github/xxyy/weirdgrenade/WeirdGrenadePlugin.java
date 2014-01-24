package io.github.xxyy.weirdgrenade;

import io.github.xxyy.weirdgrenade.config.ConfigWrapper;
import io.github.xxyy.weirdgrenade.listeners.CraftingHandler;
import io.github.xxyy.weirdgrenade.listeners.GrenadeHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin that apparently allows crafting of some weird kind of grenade
 */
public class WeirdGrenadePlugin extends JavaPlugin {

    private ConfigWrapper configWrapper;

    @Override
    public void onEnable() {
        configWrapper = new ConfigWrapper(this);

        //REGISTER STUFF
        getServer().getPluginManager().registerEvents(new CraftingHandler(this), this);
        getServer().getPluginManager().registerEvents(new GrenadeHandler(this), this);
    }

    public ConfigWrapper getConfigWrapper() {
        return configWrapper;
    }
}
