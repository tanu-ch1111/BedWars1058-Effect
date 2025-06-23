package com.andrei1058.bedwars1058.Effect;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars1058.Effect.FinalKill.*;
import com.andrei1058.bedwars1058.Effect.GUI.Command;
import com.andrei1058.bedwars1058.Effect.GUI.FinalKillGUI;
import com.andrei1058.bedwars1058.Effect.GUI.MainGUI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private static Main instance;
    private BedWars bedwarsAPI;
    private Map<UUID, String> playerFinalKillEffects;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        playerFinalKillEffects = new HashMap<>();

        saveDefaultConfig();
        config = getConfig();

        loadPlayerEffects();

        Bukkit.getPluginManager().registerEvents(new FinalKillGUI(this), this);
        Bukkit.getPluginManager().registerEvents(new MainGUI(this), this);

        Bukkit.getPluginManager().registerEvents(new BloodEffect(), this);
        Bukkit.getPluginManager().registerEvents(new PigEffect(), this);
        Bukkit.getPluginManager().registerEvents(new SquidEffect(), this);
        Bukkit.getPluginManager().registerEvents(new CowEffect(), this);
        Bukkit.getPluginManager().registerEvents(new Thunder(), this);
        Bukkit.getPluginManager().registerEvents(new ZombieEffect(), this);

        getCommand("effect").setExecutor(new Command(this));

        if (Bukkit.getPluginManager().getPlugin("BedWars1058") != null) {
            bedwarsAPI = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        } else {
            getLogger().severe("BedWars1058 not found! This plugin requires BedWars1058.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        savePlayerEffects();
        instance = null;
    }

    public static Main getInstance() {
        return instance;
    }

    public String getPlayerFinalKillEffect(UUID playerUUID) {
        return playerFinalKillEffects.getOrDefault(playerUUID, "NONE");
    }

    public void setPlayerFinalKillEffect(UUID playerUUID, String effectName) {
        playerFinalKillEffects.put(playerUUID, effectName);
        savePlayerEffects();
    }

    private void loadPlayerEffects() {
        if (config.isConfigurationSection("player_effects")) {
            for (String uuidStr : config.getConfigurationSection("player_effects").getKeys(false)) {
                try {
                    UUID uuid = UUID.fromString(uuidStr);
                    String effect = config.getString("player_effects." + uuidStr);
                    if (effect != null) {
                        playerFinalKillEffects.put(uuid, effect);
                    }
                } catch (IllegalArgumentException e) {
                    getLogger().warning("Invalid UUID found in config: " + uuidStr);
                }
            }
        }
    }

    private void savePlayerEffects() {
        config.set("player_effects", null);
        for (Map.Entry<UUID, String> entry : playerFinalKillEffects.entrySet()) {
            config.set("player_effects." + entry.getKey().toString(), entry.getValue());
        }
        saveConfig();
    }
}