package com.andrei1058.bedwars1058.Effect.FinalKill;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Cow; // 牛をインポート
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Effect; // Effect をインポート

public class CowEffect implements Listener {

    @EventHandler
    public void onFinalKillCow(PlayerKillEvent event) { // メソッド名を変更
        if (event.getVictim() == null || !event.getCause().isFinalKill() || event.getKiller() == null) {
            return;
        }

        Player killer = event.getKiller();
        String playerEffect = Main.getInstance().getPlayerFinalKillEffect(killer.getUniqueId());

        // キラーが牛エフェクトを選択しているかを確認
        if ("COW".equals(playerEffect)) { // "SQUID"を"COW"に変更
            Location deathLocation = event.getVictim().getLocation();

            if (deathLocation != null && deathLocation.getWorld() != null) {
                // 牛をスポーンさせる
                Cow cow = (Cow) deathLocation.getWorld().spawnEntity(deathLocation, EntityType.COW); // EntityType.SQUIDをEntityType.COWに変更

                // 無敵にするためのResistanceを付与
                cow.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 255, false, false));
                cow.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, false));
                cow.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 255, false, false)); // ジャンプも無効化
                cow.setCustomNameVisible(false);
                cow.setCanPickupItems(false);

                new BukkitRunnable() {
                    final double initialY = cow.getLocation().getY();
                    int ticksElapsed = 0;

                    @Override
                    public void run() {
                        if (!cow.isValid() || ticksElapsed >= 200) { // 0.25マスずつなので、5秒で100ティック * 2 = 200ティック
                            cow.remove();
                            cancel();
                            return;
                        }

                        Location newLoc = cow.getLocation().clone();
                        newLoc.setY(initialY + (0.25 * (ticksElapsed + 1))); // 0.25マスずつ上昇

                        cow.teleport(newLoc);

                        // 牛の下に火のパーティクルを表示 (1.8.8互換)
                        cow.getWorld().playEffect(cow.getLocation().clone().subtract(0, 0.2, 0), Effect.MOBSPAWNER_FLAMES, 0);

                        ticksElapsed++;
                    }
                }.runTaskTimer(Main.getInstance(), 0L, 1L);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (cow.isValid()) {
                            // 消える時に爆発パーティクルを表示 (1.8.8互換)
                            cow.getWorld().playEffect(cow.getLocation(), Effect.EXPLOSION_LARGE, 0);
                            cow.remove();
                        }
                    }
                }.runTaskLater(Main.getInstance(), 20L * 5);
            }
        }
    }
}