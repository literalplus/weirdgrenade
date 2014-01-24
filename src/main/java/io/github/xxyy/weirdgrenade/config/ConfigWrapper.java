package io.github.xxyy.weirdgrenade.config;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Manages the configuration file of the plugin.
 *
 * @author <a href="http://xxyy.github.io/">xxyy98</a>
 */
public final class ConfigWrapper {

    private final WeirdGrenadePlugin plugin;

    public ConfigWrapper(final WeirdGrenadePlugin parent) {
        ConfigNode.setPlugin(parent);
        initConfig(parent.getConfig());
        this.plugin = parent;
    }

    private void initConfig(final FileConfiguration pluginConfig) {
        pluginConfig.options().copyDefaults(true);
        pluginConfig.options().copyHeader(true);
        pluginConfig.options().header("Weird Grenade plugin config - get the src at http://github.com/xxyy/weirdgrenade\n" +
                "If you need help configuring, join #lit on irc.//irc.spi.gt/ or use the webchat: http://irc.spi.gt/iris/?channels=lit");

        for (ConfigNode node : ConfigNode.values()) {
            node.registerDefault(pluginConfig);
        }
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
