package dellunacd.me.tori.dellunacd.Me.tori;

import gui.CdGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (sender == null) return true;

        switch (args[0]) {
            case "gui":
                if (args.length != 1) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false ;
                }
                player.openInventory(new CdGUI().getInventory(player , 1));
                break;

            case "set":
                onCommandSet(sender, args);
                break;

            case "trade" :
                if (!sender.isOp()) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false ;
                }
                if (args.length != 1) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false ;
                }
                if (!sender.isOp())
                DataBase.setTrade(((Player) sender).getItemInHand());
                sender.sendMessage("[!] 교환 재료 설정 완료.");
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