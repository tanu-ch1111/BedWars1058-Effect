package com.andrei1058.bedwars1058.Effect.FinalKill;

import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.World;

public class Thunder implements Listener {

    @EventHandler
    public void onFinalKillThunder(PlayerKillEvent event) {
        if (event.getVictim() == null || !event.getCause().isFinalKill() || event.getKiller() == null) {
            return;
        }

        Player killer = event.getKiller();
        String playerEffect = Main.getInstance().getPlayerFinalKillEffect(killer.getUniqueId());

        if ("THUNDER".equals(playerEffect)) {
            Location deathLocation = event.getVictim().getLocation();

            if (deathLocation != null && deathLocation.getWorld() != null) {
                World world = deathLocation.getWorld();
                world.strikeLightningEffect(deathLocation);
            }
        }
    }
}