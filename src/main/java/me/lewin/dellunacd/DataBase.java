package me.lewin.dellunacd;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class DataBase {
    public static void createCD(ItemStack item, String player, String name) {
        FileConfiguration config = getConfig(player, name);

        config.set("item" , item);

        saveDataFile(config, getFile(player, name));
    }
    public static void removeCD(String player, String name) {
        File file = getFile(player, name);
        file.delete();
    }

    private static File getFile(String uuid, String name) {
        Plugin plugin = JavaPlugin.getPlugin(Main.class);
        return new File(plugin.getDataFolder() + "\\" + uuid, name + ".dat");
    }
    private static FileConfiguration getConfig(String uuid, String name) {
        return YamlConfiguration.loadConfiguration(getFile(uuid, name));
    }
    private static void saveDataFile(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            System.out.println("§cFile I/O Error!!");
        }
    }
}
