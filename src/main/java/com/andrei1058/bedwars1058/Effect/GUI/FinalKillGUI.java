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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;
import org.bukkit.entity.EntityType;

public class FinalKillGUI implements Listener {

    private static final String GUI_TITLE = ChatColor.DARK_PURPLE + "ファイナルキル エフェクト";
    private static final int GUI_ROWS = 6;
    private static final int GUI_SIZE = GUI_ROWS * 9;

    private final Main plugin;

    public FinalKillGUI(Main plugin) {
        this.plugin = plugin;
    }

    public static void openGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, GUI_SIZE, GUI_TITLE);

        ItemStack explosionItem = new ItemStack(Material.TNT);
        ItemMeta explosionMeta = explosionItem.getItemMeta();
        if (explosionMeta != null) {
            explosionMeta.setDisplayName(ChatColor.RED + "爆発");
            explosionMeta.setLore(Collections.singletonList(ChatColor.GRAY + "爆発エフェクトを再生します。"));
            explosionItem.setItemMeta(explosionMeta);
        }
        gui.setItem(10, explosionItem);

        ItemStack playerHeadItem = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta playerHeadMeta = (SkullMeta) playerHeadItem.getItemMeta();
        if (playerHeadMeta != null) {
            playerHeadMeta.setOwner(player.getName());
            playerHeadMeta.setDisplayName(ChatColor.GOLD + "自分の頭");
            playerHeadMeta.setLore(Collections.singletonList(ChatColor.GRAY + "自分の頭をドロップします。"));
            playerHeadItem.setItemMeta(playerHeadMeta);
        }
        gui.setItem(11, playerHeadItem);

        ItemStack pigEgg = new ItemStack(Material.MONSTER_EGG, 1, EntityType.PIG.getTypeId());
        ItemMeta pigMeta = pigEgg.getItemMeta();
        if (pigMeta != null) {
            pigMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "ブタ");
            pigMeta.setLore(Collections.singletonList(ChatColor.GRAY + "ブタをスポーンさせます。"));
            pigEgg.setItemMeta(pigMeta);
        }
        gui.setItem(16, pigEgg);

        ItemStack squidEgg = new ItemStack(Material.MONSTER_EGG, 1, EntityType.SQUID.getTypeId());
        ItemMeta squidMeta = squidEgg.getItemMeta();
        if (squidMeta != null) {
            squidMeta.setDisplayName(ChatColor.AQUA + "イカ");
            squidMeta.setLore(Collections.singletonList(ChatColor.GRAY + "イカをスポーンさせます。"));
            squidEgg.setItemMeta(squidMeta);
        }
        gui.setItem(14, squidEgg);

        ItemStack cowEgg = new ItemStack(Material.MONSTER_EGG, 1, EntityType.COW.getTypeId());
        ItemMeta cowMeta = cowEgg.getItemMeta();
        if (cowMeta != null) {
            cowMeta.setDisplayName(ChatColor.DARK_GREEN + "ウシ");
            cowMeta.setLore(Collections.singletonList(ChatColor.GRAY + "ウシをスポーンさせます。"));
            cowEgg.setItemMeta(cowMeta);
        }
        gui.setItem(12, cowEgg);

        ItemStack zombieEgg = new ItemStack(Material.MONSTER_EGG, 1, EntityType.ZOMBIE.getTypeId());
        ItemMeta zombieMeta = zombieEgg.getItemMeta();
        if (zombieMeta != null) {
            zombieMeta.setDisplayName(ChatColor.DARK_GRAY + "ゾンビ");
            zombieMeta.setLore(Collections.singletonList(ChatColor.GRAY + "ゾンビをスポーンさせます。"));
            zombieEgg.setItemMeta(zombieMeta);
        }
        gui.setItem(13, zombieEgg);

        ItemStack bloodItem = new ItemStack(Material.REDSTONE);
        ItemMeta bloodMeta = bloodItem.getItemMeta();
        if (bloodMeta != null) {
            bloodMeta.setDisplayName(ChatColor.DARK_RED + "血");
            bloodMeta.setLore(Collections.singletonList(ChatColor.GRAY + "血しぶきのようなパーティクルを再生します。"));
            bloodItem.setItemMeta(bloodMeta);
        }
        gui.setItem(14, bloodItem);

        ItemStack thunderItem = new ItemStack(Material.GLASS);
        ItemMeta thunderMeta = thunderItem.getItemMeta();
        if (thunderMeta != null) {
            thunderMeta.setDisplayName(ChatColor.YELLOW + "雷");
            thunderMeta.setLore(Collections.singletonList(ChatColor.GRAY + "雷を落とします。"));
            thunderItem.setItemMeta(thunderMeta);
        }
        gui.setItem(15, thunderItem);

        ItemStack noneItem = new ItemStack(Material.BARRIER);
        ItemMeta noneMeta = noneItem.getItemMeta();
        if (noneMeta != null) {
            noneMeta.setDisplayName(ChatColor.GRAY + "なし");
            noneMeta.setLore(Collections.singletonList(ChatColor.GRAY + "エフェクトを無効にします。"));
            noneItem.setItemMeta(noneMeta);
        }
        gui.setItem(17, noneItem);

        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backItem.getItemMeta();
        if (backMeta != null) {
            backMeta.setDisplayName(ChatColor.GREEN + "戻る");
            backItem.setItemMeta(backMeta);
        }
        gui.setItem(GUI_SIZE - 9, backItem);

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

        String selectedEffect = null;

        if (event.getSlot() == GUI_SIZE - 9 && clickedItem.getType() == Material.ARROW) {
            MainGUI.openGUI(player);
            return;
        } else if (event.getSlot() == 10 && clickedItem.getType() == Material.TNT) {
            selectedEffect = "EXPLOSION";
            player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「爆発」に設定しました。");
        } else if (event.getSlot() == 11 && clickedItem.getType() == Material.SKULL_ITEM) {
            selectedEffect = "PLAYER_HEAD";
            player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「自分の頭」に設定しました。");
        } else if (event.getSlot() == 17 && clickedItem.getType() == Material.BARRIER) {
            selectedEffect = "NONE";
            player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「なし」に設定しました。");
        } else if (event.getSlot() == 16 && clickedItem.getType() == Material.MONSTER_EGG) {
            if (clickedItem.getDurability() == EntityType.PIG.getTypeId()) {
                selectedEffect = "PIG";
                player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「ブタ」に設定しました。");
            }
        } else if (event.getSlot() == 12 && clickedItem.getType() == Material.MONSTER_EGG) {
            if (clickedItem.getDurability() == EntityType.COW.getTypeId()) {
                selectedEffect = "COW";
                player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「ウシ」に設定しました。");
            }
        } else if (event.getSlot() == 13 && clickedItem.getType() == Material.MONSTER_EGG) {
            if (clickedItem.getDurability() == EntityType.ZOMBIE.getTypeId()) {
                selectedEffect = "ZOMBIE";
                player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「ゾンビ」に設定しました。");
            }
        } else if (event.getSlot() == 14 && clickedItem.getType() == Material.REDSTONE) {
            selectedEffect = "BLOOD";
            player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「血」に設定しました。");
        } else if (event.getSlot() == 15 && clickedItem.getType() == Material.GLASS) {
            selectedEffect = "THUNDER";
            player.sendMessage(ChatColor.GREEN + "ファイナルキルエフェクトを「雷」に設定しました。");
        }

        if (selectedEffect != null) {
            plugin.setPlayerFinalKillEffect(player.getUniqueId(), selectedEffect);
            player.closeInventory();
        }
    }
}