package io.github.xxyy.weirdgrenade.listeners;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import io.github.xxyy.weirdgrenade.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.shininet.bukkit.itemrenamer.api.ItemsListener;
import org.shininet.bukkit.itemrenamer.api.RenamerSnapshot;

import java.util.List;

/**
 * Listens for sending items and removes lore markers.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 25/01/14
 */
public class ItemLoreListener implements ItemsListener {
    @Override
    public void onItemsSending(Player player, RenamerSnapshot itemStacks) {
        for(ItemStack itemStack : itemStacks){
            if(itemStack != null && itemStack.hasItemMeta()){
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta.hasLore()) {
                    List<String> lore = itemMeta.getLore();
                    lore.remove(Util.LORE_GRENADE_MARKER);
                    itemMeta.setLore(lore);
                    itemStack.setItemMeta(itemMeta);
                }
                NbtCompound compound = (NbtCompound) NbtFactory.fromItemTag(itemStack);
                compound.put(NbtFactory.ofList("ench"));
            }
        }
    }
}
