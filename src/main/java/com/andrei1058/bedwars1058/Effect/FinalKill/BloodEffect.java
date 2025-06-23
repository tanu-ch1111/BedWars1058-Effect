package com.andrei1058.bedwars1058.Effect.FinalKill;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Location;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.Random;

public class BloodEffect implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onFinalKillBlood(PlayerKillEvent event) {
        if (event.getVictim() == null || !event.getCause().isFinalKill() || event.getKiller() == null) {
            return;
        }

        Player killer = event.getKiller();
        String playerEffect = Main.getInstance().getPlayerFinalKillEffect(killer.getUniqueId());

        if ("BLOOD".equals(playerEffect)) {
            Location deathLocation = event.getVictim().getLocation().add(0, 1.0, 0);

            if (deathLocation != null && deathLocation.getWorld() != null) {
                for (int i = 0; i < 50; i++) {
                    double offsetX = (random.nextDouble() * 1.0) - 0.5;
                    double offsetY = (random.nextDouble() * 1.0) - 0.5;
                    double offsetZ = (random.nextDouble() * 1.0) - 0.5;

                    deathLocation.getWorld().playEffect(
                            deathLocation.clone().add(offsetX, offsetY, offsetZ),
                            Effect.BLOCK_BREAK,
                            Material.REDSTONE_BLOCK.getId()
                    );
                }
            }
        }
    }
}