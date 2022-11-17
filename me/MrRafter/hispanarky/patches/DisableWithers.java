package me.MrRafter.hispanarky.patches;

import lombok.RequiredArgsConstructor;
import me.MrRafter.hispanarky.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

@RequiredArgsConstructor
public class DisableWithers implements Listener {
    private final Main plugin;

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent evt) {
        FileConfiguration config = plugin.getConfig();

        if (config.getBoolean("DisableWithers")) {
            if (evt.getEntity().getType().equals(EntityType.WITHER_SKULL)) {
                evt.setCancelled(true);
            }
        } else if (config.getBoolean("RemoveWitherHeadsAutomatically")) {
            if (evt.getEntity().getType().equals(EntityType.WITHER_SKULL)) {
                Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                    if (evt.getEntity() != null) {
                        evt.getEntity().remove();
                    }
                }, plugin.getConfig().getInt("RemoveWitherHeadsAutomaticallyTicks"));
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent evt) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("RemoveWitherHeadsAutomatically")) {
            for (Entity e : evt.getChunk().getEntities()) {
                if (e.getType().equals(EntityType.WITHER_SKULL)) {
                    e.remove();
                }
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent evt) {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("RemoveWitherHeadsAutomatically")) {
            for (Entity e : evt.getChunk().getEntities()) {
                if (e.getType().equals(EntityType.WITHER_SKULL)) {
                    e.remove();
                }
            }
        }
    }
}
