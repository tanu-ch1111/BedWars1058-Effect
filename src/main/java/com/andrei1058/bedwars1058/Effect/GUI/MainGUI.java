package com.andrei1058.bedwars1058.Effect.GUI;

import com.andrei1058.bedwars1058.Effect.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MainGUI implements Listener {

    private static final String GUI_TITLE = ChatColor.AQUA + "エフェクト設定";
    private static final int GUI_ROWS = 3;
    private static final int GUI_SIZE = GUI_ROWS * 9;

    private final Main plugin;

    public MainGUI(Main plugin) {
        this.plugin = plugin;
    }

    public static void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE);

        ItemStack finalKillItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta finalKillMeta = finalKillItem.getItemMeta();
        if (finalKillMeta != null) {
            finalKillMeta.setDisplayName(ChatColor.YELLOW + "ファイナルキル エフェクト");
            finalKillMeta.setLore(Collections.singletonList(ChatColor.GRAY + "ファイナルキル時のエフェクトを設定します。"));
            finalKillItem.setItemMeta(finalKillMeta);
        }
        gui.setItem(13, finalKillItem);

        player.openInventory(gui);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(GUI_TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (event.getClickedInventory() == null || !event.getClickedInventory().equals(event.getView().getTopInventory())) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) {
            return;
        }

        if (event.getSlot() == 13 && clickedItem.getType() == Material.IRON_SWORD) {
            ItemMeta meta = clickedItem.getItemMeta();
            if (meta != null && meta.getDisplayName() != null && meta.getDisplayName().equals(ChatColor.YELLOW + "ファイナルキル エフェクト")) {
                FinalKillGUI.openGUI(player);
            }
        }
    }
}