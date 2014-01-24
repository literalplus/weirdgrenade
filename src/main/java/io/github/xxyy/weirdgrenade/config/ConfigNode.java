package io.github.xxyy.weirdgrenade.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Describes a node in the plugin's configuration.
 *
 * @author <a href="http://xxyy.github.io/">xxyy98</a>
 */
public enum ConfigNode {

    CRAFTING_OUTCOME_AMOUNT("crafting.outcome.amount", 2),
    CRAFTING_OUTCOME_DAMAGE("crafting.outcome.damage", 16_387),
    CRAFTING_OUTCOME_MATERIAL("crafting.outcome.material", Material.POTION),

    CRAFTING_OUTCOME_NAME("crafting.outcome.name", "&f&lWeird Grenade"),
    CRAFTING_OUTCOME_LORE("crafting.outcome.lore", new String[]{
            "&7It is so weird, if you throw it at the floor, it explodes.",
            "&3You can even use special characters in this description: &auml; &szlig; &euro; &8&o(Use HTML escapes)"}),

    CRAFTING_RECIPE_SHAPE("crafting.recipe.shape", new Material[]{
            Material.CLAY_BALL, Material.CLAY_BALL, Material.CLAY_BALL,
            null, Material.POTION, null,
            null, null, null
    }),

    THROW_DELAY("throw.delay_in_ticks", 60L), //3 seconds
    THROW_EXPLOSION_STRENGTH("throw.explosion.strength", 3.0F),
    THROW_EXPLOSION_FIRE("throw.explosion.fire", true)
    ;
    private String path;
    private Object def;
    private static Plugin plugin;

    private ConfigNode(String path, Object def) {
        this.path = path;
        this.def = def;
    }

    protected void registerDefault(final FileConfiguration cfg) {
        cfg.addDefault(path, def);
    }

    public String getPath() {
        return path;
    }

    public Object getDefault() {
        return def;
    }

    private void checkHasPlugin() {
        if (plugin == null) {
            throw new IllegalStateException("plugin is null");
        }
    }

    public <T> T getValue() {
        checkHasPlugin();
        return (T) plugin.getConfig().get(path, def);
    }

    public <T extends List> T getListValue() {
        checkHasPlugin();
        return (T) plugin.getConfig().getList(path, (List<?>) def);
    }

    public static void setPlugin(final Plugin plugin) {
        ConfigNode.plugin = plugin;
    }
}
