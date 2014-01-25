package io.github.xxyy.weirdgrenade.listeners;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import io.github.xxyy.weirdgrenade.tasks.ThrowGrenadeTask;
import io.github.xxyy.weirdgrenade.util.Util;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
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
        if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
            handleGrenade(evt.getItem(), evt.getPlayer(), evt.getPlayer().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPotionSplash(final PotionSplashEvent evt){
        handleGrenade(evt.getEntity().getItem(), null, evt.getPotion().getLocation());
    }

    private void handleGrenade(final ItemStack grenadeStack, final Player plrCause, final Location hitLocation){
        if(!Util.isWeirdGrenade(grenadeStack)){
            return;
        }

        plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() { //See JavaDoc
            @Override
            public void run() {
                if (plrCause != null && plrCause.getGameMode() != GameMode.CREATIVE) {
                    ItemStack itemInHand = plrCause.getInventory().getItemInHand();
                    if (itemInHand != null && itemInHand.getAmount() > 1) {
                        itemInHand.setAmount(grenadeStack.getAmount() - 1);
                    } else {
                        itemInHand = null;
                    }

                    plrCause.setItemInHand(itemInHand);
                }

                ThrowGrenadeTask.runNewTask(plugin, hitLocation);
            }
        }, 1L);
    }
}
