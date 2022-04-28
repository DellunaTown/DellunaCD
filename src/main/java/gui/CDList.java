package gui;

import dellunacd.me.tori.dellunacd.Me.tori.DataBase;
import dellunacd.me.tori.dellunacd.Me.tori.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class CDList implements Listener {

    public Inventory getInventory(OfflinePlayer player, Integer page) {
        Inventory inv = Bukkit.getServer().createInventory(null, 54, "전체 음반리스트");
        Plugin plugin = JavaPlugin.getPlugin(Main.class);

        File[] list = new File(plugin.getDataFolder() + "\\" + player.getUniqueId().toString()).listFiles();
        int count = 0;
        int index = 0;

        for (File file : list) {
            if (count < (page - 1) * 10 || count > page * 10 - 1) {
                count++;
                continue;
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            inv.setItem(index, (ItemStack) config.get("item"));
            count++;
            index++;
        }

        if (page == 1) {
            inv.setItem(48, previous_null());
        } else {
            inv.setItem(48, previous());
        }


        inv.setItem(49, exit());


        if (DataBase.getCDCount(player.getUniqueId().toString()) > 10 * page) {
            inv.setItem(50, next());
        } else {
            inv.setItem(50, next_null());
        }

        inv.setItem(53, pageicon(page));

        inv.setItem(45, nullicon());
        inv.setItem(46, nullicon());
        inv.setItem(47, nullicon());
        inv.setItem(51, nullicon());
        inv.setItem(52, nullicon());

        return inv;
    }

    private ItemStack next_null() {
        return Icon.set(Material.BONE, "§7[ §x§9§3§8§d§9§4§l다음 §7]", 1033);
    }

    private ItemStack previous_null() {
        return Icon.set(Material.BONE, "§7[ §x§9§3§8§d§9§4§l이전 §7]", 1035);
    }

    private ItemStack next() {
        return Icon.set(Material.BONE, "§7[ §6§l다음 §7]", 1032);
    }

    private ItemStack previous() {
        return Icon.set(Material.BONE, "§7[ §6§l이전 §7]", 1034);
    }

    private ItemStack exit() {
        return Icon.set(Material.BONE, "§7[ §6§l나가기 §7]", 1036);
    }

    private ItemStack pageicon(Integer page) {
        return Icon.set(Material.BONE, page.toString(), 1023);
    }
    private ItemStack nullicon() {
        return Icon.set(Material.BONE, " ", 1023);
    }


    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("전체 음반리스트")) {
            if (event.getClickedInventory() == null || event.getClickedInventory().equals(event.getView().getBottomInventory())) {
                return;
            }
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            Integer page = Integer.parseInt(event.getClickedInventory().getItem(26).getItemMeta().getDisplayName());


            switch (event.getSlot()) {
                case 48:
                    if (event.getCurrentItem().getItemMeta().getCustomModelData() == 1034) {
                        player.openInventory((Inventory) new CdGUI().getInventory(player, --page));
                    }
                    break;

                case 49:
                    player.closeInventory();
                    break;

                case 50:
                    if (event.getCurrentItem().getItemMeta().getCustomModelData() == 1032) {
                        player.openInventory((Inventory) new CdGUI().getInventory(player, ++page));
                    }
                    break;
            }

            if (event.getCurrentItem().getType().equals(Material.MUSIC_DISC_CAT)) {
                player.openInventory(new TradeGUI().getInventory(player, event.getCurrentItem()));
            }
        }
    }
}


