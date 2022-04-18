package gui;

import dellunacd.me.tori.dellunacd.Me.tori.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TradeGUI implements Listener {
    public Inventory getInventory(Player player , ItemStack item) {
        Inventory inv = Bukkit.getServer().createInventory(null, 27, "§x§0§0§b§3§b§6" + player.getName() + "§x§0§0§b§3§b§6의 음반교환");
        Plugin plugin = JavaPlugin.getPlugin(Main.class);

        inv.setItem(13 , icontrade());
        for ( int i = 0 ; i < 27 ; i++ ) {
            if ( i== 11 || i== 13 || i==15 ) {
                continue;
            }
            inv.setItem(i, nullicon());

        }
        inv.setItem(4, item);





        return inv ;
    }
    private ItemStack icontrade() {
        return Icon.set(Material.ARROW, "§7[ §6§l교환 §7]");
    }
    private ItemStack nullicon() {
        return Icon.set(Material.BONE, " ", 1023 );
    }



    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().contains("§x§0§0§b§3§b§6의 음반교환")) {
            if ( event.getClickedInventory() == null || event.getClickedInventory().equals(event.getView().getBottomInventory())) {
                return ; //
            }
            Player player = (Player) event.getWhoClicked();


            switch (event.getSlot()) {
                case 11:
                    return;

                case 13:
                    event.setCancelled(true);

                    FileConfiguration config = YamlConfiguration.loadConfiguration(new File(JavaPlugin.getPlugin(Main.class).getDataFolder(),"setting.dat"));
                    ItemStack item = (ItemStack) config.get("item");
                    if ( event.getClickedInventory().getItem(11).equals(item)) {
                        event.getClickedInventory().clear(11);

                        event.getClickedInventory().setItem(15 , event.getClickedInventory().getItem(4));
                    }

                    break;

                case 15:
                    return;
            }
            event.setCancelled(true);
        }
    }


}
