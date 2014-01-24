package io.github.xxyy.weirdgrenade.util;

import io.github.xxyy.weirdgrenade.config.ConfigNode;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides some utility methods.
 *
 * @author <a href="http://xxyy.github.io/">xxyy98</a>
 */
public final class Util {

    private Util() {
    }

    /**
     * Parses a String so that a) ChatColor is parsed with '&' instead of '\u00a7' b) HTML special chars are unescaped.
     *
     * @author <a href="http://xxyy.github.io/">xxyy98</a>
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

    public static boolean craftingMatrixHasShaped(final ItemStack[] matrix, final Material... materials) {
        int i = 0;
        for (Material mat : materials) {
            if ((matrix[i] == null && mat != null) //If mat is null and provided is also null, ignore
                    || !matrix[i].getType().equals(mat)) {
                return false;
            }
            i++;
        }

        return true;
    }

    public static ItemStack getWeirdGrenadeStack(final Plugin plugin) {
        ItemStack stk = new ItemStack(
                ConfigNode.CRAFTING_OUTCOME_MATERIAL.<Material>getValue(),
                ConfigNode.CRAFTING_OUTCOME_AMOUNT.<Integer>getValue(),
                ConfigNode.CRAFTING_OUTCOME_DAMAGE.<Short>getValue());

        ItemMeta itemMeta = stk.getItemMeta();
        itemMeta.setDisplayName(applyCodes(ConfigNode.CRAFTING_OUTCOME_NAME.<String>getValue()));
        itemMeta.setLore(applyCodesList(ConfigNode.CRAFTING_OUTCOME_LORE.<List<String>>getListValue()));
        stk.setItemMeta(itemMeta);

        return stk;
    }
}
