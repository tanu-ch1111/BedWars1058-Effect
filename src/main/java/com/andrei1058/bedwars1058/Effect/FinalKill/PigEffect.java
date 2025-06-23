package com.andrei1058.bedwars1058.Effect.FinalKill;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Effect;

public class PigEffect implements Listener {

    @EventHandler
    public void onFinalKillPig(PlayerKillEvent event) {
        if (event.getVictim() == null || !event.getCause().isFinalKill() || event.getKiller() == null) {
            return;
        }

        Player killer = event.getKiller();
        String playerEffect = Main.getInstance().getPlayerFinalKillEffect(killer.getUniqueId());

        if ("PIG".equals(playerEffect)) {
            Location deathLocation = event.getVictim().getLocation();

            if (deathLocation != null && deathLocation.getWorld() != null) {
                Pig pig = (Pig) deathLocation.getWorld().spawnEntity(deathLocation, EntityType.PIG);
                pig.setCustomName(ChatColor.GOLD + event.getVictim().getName() + "'s Pig");
                pig.setCustomNameVisible(true);
                pig.setCanPickupItems(false);
                pig.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 0));

                new BukkitRunnable() {
                    final double initialY = pig.getLocation().getY();
                    int ticksElapsed = 0;

                    @Override
                    public void run() {
                        if (!pig.isValid() || ticksElapsed >= 200) {
                            pig.remove();
                            cancel();
                            return;
                        }

                        Location newLoc = pig.getLocation().clone();
                        newLoc.setY(initialY + (0.25 * (ticksElapsed + 1)));

                        pig.teleport(newLoc);

                        pig.getWorld().playEffect(pig.getLocation().clone().subtract(0, 0.2, 0), Effect.MOBSPAWNER_FLAMES, 0);

                        ticksElapsed++;
                    }
                }.runTaskTimer(Main.getInstance(), 0L, 1L);

                new BukkitRunnable() {
                    @Override
                    public void void run() {
                        if (pig.isValid()) {
                            pig.getWorld().playEffect(pig.getLocation(), Effect.EXPLOSION_LARGE, 0);
                            pig.remove();
                        }
                    }
                }.runTaskLater(Main.getInstance(), 20L * 5);
            }
        }
    }
}