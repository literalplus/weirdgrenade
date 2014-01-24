package io.github.xxyy.weirdgrenade.tasks;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import io.github.xxyy.weirdgrenade.config.ConfigNode;
import io.github.xxyy.weirdgrenade.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * Represents a task that fires a grenade after a specific amount of time (defined in config)
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 24/01/14
 */
public class ThrowGrenadeTask implements Runnable {

    private final Location location;

    private ThrowGrenadeTask(final Location location, final Plugin plugin){
        this.location = location;
        
        //spawn fake potion to indicate throwing
        Item item = this.location.getWorld().dropItem(location, Util.getWeirdGrenadeStack());
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setVelocity(new Vector(0, 12, 0)); //It will get destroyed by the explosion. Hopefully.
    }

    @Override
    public void run() {
        location.getWorld().createExplosion(location,
                (Float) ConfigNode.THROW_EXPLOSION_STRENGTH.getValue(),
                (Boolean) ConfigNode.THROW_EXPLOSION_FIRE.getValue());
    }

    public static void runNewTask(final WeirdGrenadePlugin plugin, final Location location){
        plugin.getServer().getScheduler().runTaskLater(plugin, new ThrowGrenadeTask(location, plugin), (long) ConfigNode.THROW_DELAY.getValue());
    }
}
