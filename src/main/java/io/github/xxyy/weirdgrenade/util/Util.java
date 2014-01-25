package io.github.xxyy.weirdgrenade.util;

import io.github.xxyy.weirdgrenade.config.ConfigNode;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that provides some utility methods.
 *
 * @author <a href="http://xxyy.github.io/">xxyy98</a>
 */
public final class Util {

    public static final String LORE_GRENADE_MARKER = "~~INSTALL_ITEMRENAMER_PLEASE~~";

    private Util() {
    }

    /**
     * Parses a String so that a) ChatColor is parsed with '&' instead of '\u00a7' b) HTML special chars are unescaped.
     */
    public static String applyCodes(final String str) {
        return ChatColor.translateAlternateColorCodes('&', StringEscapeUtils.unescapeHtml(str));
    }

    public static List<String> applyCodesList(final List<String> stringList) {
        List<String> newList = new ArrayList<>();

        for (String str : stringList) {
            newList.add(applyCodes(str));
        }

        return newList;
    }

    public static boolean isWeirdGrenade(final ItemStack itemStack) {
        if (itemStack != null && itemStack.hasItemMeta()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasLore()) {
                return itemMeta.getLore().contains(Util.LORE_GRENADE_MARKER);
            }
        }

        return false;
    }

    public static boolean registerRecipes(final Server server) {
        ShapedRecipe recipe = new ShapedRecipe(getWeirdGrenadeStack())
                .shape(ConfigNode.CRAFTING_RECIPE_LINES.getValue().toString().split("\\|"));

        for (Map.Entry<String, Object> entry :
                ConfigNode.getPlugin().getConfig()
                        .getConfigurationSection(ConfigNode.CRAFTING_RECIPE_TYPES.getPath())
                        .getValues(false).entrySet()) {

            Material material = Material.matchMaterial(entry.getValue().toString());
            Validate.notNull(material, "One of your recipe types is not a valid Material: " + entry.getValue());

            recipe.setIngredient(entry.getKey().charAt(0), material);
        }

        return server.addRecipe(recipe);
    }

    private static ItemStack getWeirdGrenadeStack() {
        final Material material = Material.matchMaterial(ConfigNode.CRAFTING_OUTCOME_MATERIAL.<String>getValue());
        Validate.notNull(material, "Illegal grenade Material name defined in your config!");

        ItemStack stk = new ItemStack(
                material,
                ConfigNode.CRAFTING_OUTCOME_AMOUNT.<Integer>getValue(),
                ConfigNode.CRAFTING_OUTCOME_DAMAGE.<Integer>getValue().shortValue());

        ItemMeta itemMeta = stk.getItemMeta();
        itemMeta.setDisplayName(applyCodes(ConfigNode.CRAFTING_OUTCOME_NAME.<String>getValue()));
        List<String> lore = applyCodesList(ConfigNode.CRAFTING_OUTCOME_LORE.<List<String>>getListValue());
        lore.add(LORE_GRENADE_MARKER);
        itemMeta.setLore(lore);
        stk.setItemMeta(itemMeta);

        return stk;
    }

    public static void hookItemRenamer(final Plugin plugin) throws Exception, Error {
        org.shininet.bukkit.itemrenamer.api.RenamerAPI.getAPI()
                .addListener(plugin,
                        org.shininet.bukkit.itemrenamer.api.RenamerPriority.PRE_NORMAL,
                        (org.shininet.bukkit.itemrenamer.api.ItemsListener) Class.forName("io.github.xxyy.weirdgrenade.listeners.ItemLoreListener")
                                .getConstructor().newInstance());
    }
}
