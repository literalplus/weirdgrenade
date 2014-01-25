package io.github.xxyy.weirdgrenade.config;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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
    CRAFTING_OUTCOME_MATERIAL("crafting.outcome.material", Material.POTION.name()),

    CRAFTING_OUTCOME_NAME("crafting.outcome.name", "&f&lWeird Grenade"),
    CRAFTING_OUTCOME_LORE("crafting.outcome.lore", Lists.newArrayList(
            "&7It is so weird, ",
            "&7if you throw it at the floor, it explodes.",
            "&3You can even use",
            "&3special characters in this description:",
            "&3&auml; &szlig; &euro; &8&o(Use HTML escapes)")),

    CRAFTING_RECIPE_LINES("crafting.recipe.lines", "CCC| P |   "),
    CRAFTING_RECIPE_TYPES("crafting.recipe.items", null){
        @Override
        protected void registerDefault(FileConfiguration cfg) {
            ConfigurationSection configurationSection = cfg.getConfigurationSection(this.getPath());
            if(configurationSection == null){
                cfg.addDefault(this.getPath()+".C", Material.CLAY_BALL.name());
                cfg.addDefault(this.getPath()+".P", Material.POTION.name());
            }
        }
    },

    THROW_DELAY("throw.delay_in_ticks", 30L), //1.5 seconds
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
        return (T) plugin.getConfig().getList(path, (List<?>)def);
    }

    public String getStringValue(){
        checkHasPlugin();
        return plugin.getConfig().getString(path, def.toString());
    }

    public Boolean getBooleanValue(){
        checkHasPlugin();
        return plugin.getConfig().getBoolean(path,
                (def instanceof Boolean ? //this works: http://ideone.com/xfOK3g
                        (Boolean) def
                        : Boolean.valueOf(def.toString())));
    }

    public static void setPlugin(final Plugin plugin) {
        ConfigNode.plugin = plugin;
    }
    public static Plugin getPlugin(){ return ConfigNode.plugin; }
}
