package dellunacd.me.tori.dellunacd.Me.tori;

import gui.CDList;
import gui.CdGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;


public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Plugin plugin = JavaPlugin.getPlugin(Main.class);
        Player player = (Player) sender;
        if (sender == null) return true;

        if ( args[0]==null || args.length== 0) {
            sender.sendMessage("§c[!] 잘못된 입력입니다.");
            return false;
        }

        switch (args[0]) {
            case "gui": // /dellunacd gui 일반 유저와 오피 둘 다 사용가능한 명령어. 현재 본인의 음반리스트GUI 보이기.
            case "열기":
                if (args.length != 1) {
                    sender.sendMessage("§c[!] 잘못된 입력입니다.");
                    return false;
                }
                if (new File(plugin.getDataFolder() + "\\" + player.getUniqueId().toString()).listFiles() == null) {
                    sender.sendMessage("§c[!] 제작한 음반이 없습니다. 음반을 만들어보세요! ");
                    return false;
                }
                player.openInventory(new CdGUI().getInventory(player, 1));
                break;

            case "set": // /dellunacd set <name> 유저파일에 음반추가 , 오른손에 음반을 들고 명령어 실행.
                onCommandSet(sender, args);
                break;

            case "trade": //dellunacd trade 재료설정 명령어 , 오른손에 재료를 들고 명령어 실행.
                if (!sender.isOp() || args.length != 1) {
                    sender.sendMessage("§c[!] 잘못된 입력입니다.");
                    return false;
                }
                DataBase.setTrade(((Player) sender).getItemInHand());
                sender.sendMessage("§a[!] 교환 재료 설정 완료.");

                break;

            case "open": // /dellunacd open <name> , 오피전용 유저 음반 리스트 보기
                if (!sender.isOp() || args.length != 2) {
                    sender.sendMessage("§c[!] 잘못된 입력입니다.");
                }

                if (!DataBase.exists(Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString())) {
                    sender.sendMessage("§c[!] 해당 플레이어가 제작한 음반이 없습니다");
                    return false;
                } else {
                    player.openInventory(new CdGUI().getInventory(Bukkit.getOfflinePlayer(args[1]), 1));
                    break;
                }
                
            case "list": // /dellunacd list , 오피전용 전체 음반리스트 보기
                if (!sender.isOp()) {
                    sender.sendMessage("§c[!] 잘못된 입력입니다.");
                    return false;
                }
                else {
                    player.openInventory(new CDList().getInventory(player , 1)) ; //전체 음반리스트가 뜨게 하기
                    break;
                }
        }
        return false;
    }


    // /dellunacd set Lewin22
    private void onCommandSet(CommandSender sender, String[] args) {
        if (args.length != 2 || !sender.isOp()) {
            sender.sendMessage("§c[!] 잘못된 입력입니다.");
            return;
        }

        ItemStack item = ((Player) sender).getItemInHand();
        if (item == null || !item.getType().equals(Material.MUSIC_DISC_CAT)) return;

        if (!Bukkit.getOfflinePlayer(args[1]).isWhitelisted()) return;
        String uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId().toString();

        String name = item.getItemMeta().getLore().get(2).split(": ")[1];

        DataBase.createCD(item, uuid, name);
        sender.sendMessage("§a[!] cd가 추가되었습니다.");


        return;
    }
}