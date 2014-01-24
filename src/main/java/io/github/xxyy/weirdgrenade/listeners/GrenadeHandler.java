package io.github.xxyy.weirdgrenade.listeners;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import io.github.xxyy.weirdgrenade.config.ConfigNode;
import io.github.xxyy.weirdgrenade.tasks.ThrowGrenadeTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Handles usage of weird grenades.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 24/01/14
 */
public final class GrenadeHandler implements Listener {
    private final WeirdGrenadePlugin plugin;

    public GrenadeHandler(final WeirdGrenadePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onInteract(final PlayerInteractEvent evt) {
        if (evt.getAction() == Action.RIGHT_CLICK_BLOCK &&
                evt.getItem().getType() == ConfigNode.CRAFTING_OUTCOME_MATERIAL.getValue() &&
                evt.getItem().getDurability() == (short) ConfigNode.CRAFTING_OUTCOME_DAMAGE.getValue()) {
            evt.getPlayer().getInventory().getItemInHand().setAmount(evt.getItem().getAmount() - 1);
            ThrowGrenadeTask.runNewTask(plugin, evt.getPlayer().getLocation());
        }
    }
}
