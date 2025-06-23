package com.andrei1058.bedwars1058.Effect.GUI;

import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class Command implements CommandExecutor {

    private final Main plugin;

    public Command(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーのみ実行できます。");
            return true;
        }

        Player player = (Player) sender;

        player.sendMessage(ChatColor.GREEN + "[Effect] コマンドが実行されました。GUIを開きます...");
        plugin.getLogger().info("[Effect] Player " + player.getName() + " executed /effect command.");


        MainGUI.openGUI(player);
        return true;
    }
}