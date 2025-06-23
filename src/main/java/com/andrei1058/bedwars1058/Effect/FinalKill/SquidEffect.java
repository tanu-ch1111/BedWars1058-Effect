package com.andrei1058.bedwars1058.Effect.FinalKill;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Effect;

public class SquidEffect implements Listener {

    @EventHandler
    public void onFinalKillSquid(PlayerKillEvent event) {
        if (event.getVictim() == null || !event.getCause().isFinalKill() || event.getKiller() == null) {
            return;
        }

        Player killer = event.getKiller();
        String playerEffect = Main.getInstance().getPlayerFinalKillEffect(killer.getUniqueId());

        if ("SQUID".equals(playerEffect)) {
            Location deathLocation = event.getVictim().getLocation();

            if (deathLocation != null && deathLocation.getWorld() != null) {
                Squid squid = (Squid) deathLocation.getWorld().spawnEntity(deathLocation, EntityType.SQUID);
                squid.setCustomName(ChatColor.GOLD + event.getVictim().getName() + "'s Squid");
                squid.setCustomNameVisible(true);
                squid.setCanPickupItems(false);
                squid.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));

                new BukkitRunnable() {
                    final double initialY = squid.getLocation().getY();
                    int ticksElapsed = 0;

                    @Override
                    public void run() {
                        if (!squid.isValid() || ticksElapsed >= 200) {
                            squid.remove();
                            cancel();
                            return;
                        }

                        Location newLoc = squid.getLocation().clone();
                        newLoc.setY(initialY + (0.25 * (ticksElapsed + 1)));

                        squid.teleport(newLoc);

                        squid.getWorld().playEffect(squid.getLocation().clone().subtract(0, 0.2, 0), Effect.MOBSPAWNER_FLAMES, 0);

                        ticksElapsed++;
                    }
                }.runTaskTimer(Main.getInstance(), 0L, 1L);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (squid.isValid()) {
                            squid.getWorld().playEffect(squid.getLocation(), Effect.EXPLOSION_LARGE, 0);
                            squid.remove();
                        }
                    }
                }.runTaskLater(Main.getInstance(), 20L * 5);
            }
        }
    }
}