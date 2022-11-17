package me.MrRafter.hispanarky.events;

import lombok.RequiredArgsConstructor;
import me.MrRafter.hispanarky.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class FirstMessage implements Listener {
    private final Main plugin;

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent evt) {
        FileConfiguration config = plugin.getConfig();

            if (config.getBoolean("join-messages.enabled")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!plugin.connectionMsgs.contains(p.getName())) {


                        if (config.getBoolean("join-messages.enabled") && !evt.getPlayer().hasPlayedBefore()) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("join-messages.FirstJoinMessage")
                                    .replace("%player%", evt.getPlayer().getName())
                                    .replace("%players_num%", formatToOrdinal(Bukkit.getOfflinePlayers().length + Bukkit.getOnlinePlayers().toArray().length + 1))
                            ));
                            return;
                        }
                    }
                }
                return;
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (plugin.connectionMsgs.contains(p.getName())) {
                    if (config.getBoolean("join-messages.enabled") && !evt.getPlayer().hasPlayedBefore()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("join-messages.FirstJoinMessage")
                                .replace("%player%", evt.getPlayer().getName())
                                .replace("%players_num%", formatToOrdinal(Bukkit.getOfflinePlayers().length + Bukkit.getOnlinePlayers().toArray().length + 1))
                        ));
                        return;
                    }
                }
            }
        }


    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent evt) {
        FileConfiguration config = plugin.getConfig();
    }

    private String formatToOrdinal(int i) {
        String[] suffixes = new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        switch (i % 100) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                return i + suffixes[i % 10];
        }
    }
}
