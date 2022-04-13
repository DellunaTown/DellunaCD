package me.lewin.dellunacd;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        switch (args[0]) {
            case "gui":

                break;
            case "set":
                onCommandSet(sender, args);
                break;
        }
        return false;
    }

    // /dellunacd set Lewin22
    private void onCommandSet(CommandSender sender, String[] args) {
        if (args.length != 2) return;
        if (!sender.isOp()) return;

        ItemStack item = ((Player) sender).getItemInHand();
        if (item == null || !item.getType().equals(Material.MUSIC_DISC_CAT)) return;

        if (!Bukkit.getOfflinePlayer(args[1]).isWhitelisted()) return;
        String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();

        String name = item.getItemMeta().getLore().get(2).split(": ")[1];

        DataBase.createCD(item, uuid, name);
        sender.sendMessage("[!] cd가 추가되었습니다.");

        return;
    }
}
