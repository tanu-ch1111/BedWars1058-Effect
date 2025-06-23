package com.andrei1058.bedwars1058.Effect.FinalKill;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieEffect implements Listener {

    @EventHandler
    public void onFinalKillZombie(PlayerKillEvent event) {
        if (event.getVictim() == null || !event.getCause().isFinalKill() || event.getKiller() == null) {
            return;
        }

        Player killer = event.getKiller();
        String playerEffect = Main.getInstance().getPlayerFinalKillEffect(killer.getUniqueId());

        if ("ZOMBIE".equals(playerEffect)) {
            Location deathLocation = event.getVictim().getLocation();
            Player victim = event.getVictim();

            if (deathLocation != null && deathLocation.getWorld() != null) {
                Zombie zombie = (Zombie) deathLocation.getWorld().spawnEntity(deathLocation, EntityType.ZOMBIE);
                zombie.setBaby(false);
                zombie.setVillager(false);
                zombie.setCustomName(ChatColor.RED + victim.getName());
                zombie.setCustomNameVisible(true);
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * 60 * 60, 0));
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 60 * 60, 100));
                zombie.setCanPickupItems(false);

                ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
                if (skullMeta != null) {
                    skullMeta.setOwner(victim.getName());
                    head.setItemMeta(skullMeta);
                }
                zombie.getEquipment().setHelmet(head);

                zombie.getEquipment().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                zombie.getEquipment().setBoots(new ItemStack(Material.LEATHER_BOOTS));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (zombie.isValid()) {
                            zombie.remove();
                        }
                    }
                }.runTaskLater(Main.getInstance(), 20L * 5);
            }
        }
    }
}