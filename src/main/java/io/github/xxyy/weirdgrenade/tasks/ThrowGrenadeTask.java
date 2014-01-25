package io.github.xxyy.weirdgrenade.tasks;

import io.github.xxyy.weirdgrenade.WeirdGrenadePlugin;
import io.github.xxyy.weirdgrenade.config.ConfigNode;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;

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
        
        this.location.getWorld().playEffect(this.location.add(0,1,0), Effect.SMOKE, BlockFace.UP);
    }

    @Override
    public void run() {
        location.getWorld().createExplosion(location,
                ConfigNode.THROW_EXPLOSION_STRENGTH.<Double>getValue().floatValue(),
                ConfigNode.THROW_EXPLOSION_FIRE.getBooleanValue());
    }

    public static void runNewTask(final WeirdGrenadePlugin plugin, final Location location){
        plugin.getServer().getScheduler().runTaskLater(plugin, new ThrowGrenadeTask(location, plugin), ConfigNode.THROW_DELAY.<Integer>getValue().longValue());
    }
}
