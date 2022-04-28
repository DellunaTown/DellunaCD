package dellunacd.me.tori.dellunacd.Me.tori;

import gui.CDList;
import gui.CdGUI;
import gui.TradeGUI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Plugin plugin = JavaPlugin.getPlugin(Main.class);

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        Bukkit.getPluginCommand("dellunacd").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new CdGUI(), this );
        Bukkit.getPluginManager().registerEvents(new TradeGUI(), this );
        Bukkit.getPluginManager().registerEvents(new CDList(), this );
    }
}