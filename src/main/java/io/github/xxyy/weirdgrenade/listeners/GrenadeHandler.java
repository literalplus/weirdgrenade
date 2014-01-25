package io.github.xxyy.weirdgrenade.listeners;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import io.github.xxyy.weirdgrenade.tasks.ThrowGrenadeTask;
import io.github.xxyy.weirdgrenade.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
                Util.isWeirdGrenade(evt.getItem())) {

            plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() { //See JavaDoc
                @Override
                public void run() {
                    ItemStack itemInHand = evt.getPlayer().getInventory().getItemInHand();
                    if (itemInHand != null && itemInHand.getAmount() > 1) {
                        itemInHand.setAmount(evt.getItem().getAmount() - 1);
                    }else{
                        itemInHand = null;
                    }

                    evt.getPlayer().setItemInHand(itemInHand);

                    ThrowGrenadeTask.runNewTask(plugin, evt.getPlayer().getLocation());
                }
            }, 1L);

        }
    }
}
