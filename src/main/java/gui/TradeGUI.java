package gui;

import dellunacd.me.tori.dellunacd.Me.tori.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TradeGUI implements Listener {
    public Inventory getInventory(Player player, ItemStack item) {
        Inventory inv = Bukkit.getServer().createInventory(null, 27, "§x§0§0§b§3§b§6" + player.getName() + "§x§0§0§b§3§b§6의 음반교환");
        Plugin plugin = JavaPlugin.getPlugin(Main.class);

        inv.setItem(13, icontrade());
        for (int i = 0; i < 27; i++) {
            if (i == 11 || i == 13 || i == 15) {
                continue;
            }
            inv.setItem(i, nullicon());
        }
        inv.setItem(4, item);

        return inv;
    }


    private ItemStack icontrade() {
        return Icon.set(Material.ARROW, "§7[ §6§l교환 §7]");
    }

    private ItemStack nullicon() {
        return Icon.set(Material.BONE, " ", 1023);
    }


    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("§x§0§0§b§3§b§6의 음반교환")) {
            if (event.getClickedInventory() == null || event.getClickedInventory().equals(event.getView().getBottomInventory())) {
                return; //
            }
            Player player = (Player) event.getWhoClicked();


            switch (event.getSlot()) {
                case 11:
                case 15:
                    return;
                case 13:
                    event.setCancelled(true);

                    FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JavaPlugin.getPlugin(Main.class).getDataFolder(), "setting.dat"));
                    ItemStack item = (ItemStack) config.get("item");

                    if (isInventoryFull(player)) {
                        player.sendMessage("§c[!] 인벤토리를 2칸 이상 비워주세요");
                        break;
                    }

                    if (event.getClickedInventory().getItem(11).getType().equals(item.getType())) {
                        if (event.getClickedInventory().getItem(11).getAmount() >= item.getAmount()) {
                            ItemStack item2 = event.getClickedInventory().getItem(11);
                            item2.setAmount(event.getClickedInventory().getItem(11).getAmount()-item.getAmount());

                            event.getClickedInventory().setItem(11 , item2 );
                            if (event.getClickedInventory().getItem(15) != null ) {
                                event.getWhoClicked().getInventory().addItem(event.getClickedInventory().getItem(15));
                                event.getClickedInventory().clear(15);
                            }
                            event.getClickedInventory().setItem(15, event.getClickedInventory().getItem(4));
                        }
                    }
            }
            event.setCancelled(true);
        }
    }

    //아이템 증발 방지
    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().contains("§x§0§0§b§3§b§6의 음반교환")) {

            if (event.getInventory().getItem(11) != null) {
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(11));
            }
            if (event.getInventory().getItem(15) != null) {
                event.getPlayer().getInventory().addItem(event.getInventory().getItem(15));
            }
        }
    }
    // 인벤토리가 비어있는지 확인
    private boolean isInventoryFull(Player player) {
        int stack = 0;
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item != null) {
                stack++;
            }
        }

        return stack > (player.getInventory().getStorageContents().length - 2);

    }



}






