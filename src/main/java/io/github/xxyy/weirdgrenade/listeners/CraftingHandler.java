package io.github.xxyy.weirdgrenade.listeners;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import io.github.xxyy.weirdgrenade.config.ConfigNode;
import io.github.xxyy.weirdgrenade.util.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;

/**
 * Listens for crafting events and decides if a weird grenade should be crafted.
 *
 * @author <a href="http://xxyy.github.io/">xxyy98</a>
 */
@Deprecated
public final class CraftingHandler implements Listener {

    private final WeirdGrenadePlugin plugin;

    public CraftingHandler(final WeirdGrenadePlugin parent) {
        plugin = parent;
    }


    @EventHandler
    public void onCraft(final InventoryClickEvent evt) {
        if(evt.getSlotType() != InventoryType.SlotType.CRAFTING ||
                evt.getInventory().getType() != InventoryType.CRAFTING){
            return;
        }

        CraftingInventory craftingInventory = (CraftingInventory) evt.getInventory();

        if (Util.craftingMatrixHasShaped(
                craftingInventory.getMatrix(),
                (Material[]) ConfigNode.CRAFTING_RECIPE_SHAPE.getValue()
        )) { //Player is trying to craft a weird grenade
            craftingInventory.setResult(Util.getWeirdGrenadeStack());
        }
    }
}
