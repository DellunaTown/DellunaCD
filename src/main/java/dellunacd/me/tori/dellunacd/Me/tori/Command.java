package dellunacd.me.tori.dellunacd.Me.tori;

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


public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        Plugin plugin = JavaPlugin.getPlugin(Main.class);
        Player player = (Player) sender;
        if (sender == null) return true;

        switch (args[0]) {
            case "gui": // /dellunacd gui 일반 유저와 오피 둘 다 사용가능한 명령어. 현재 본인의 음반리스트GUI 보이기.
                if (args.length != 1) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false;
                }
                if (new File(plugin.getDataFolder() + "\\" + player.getUniqueId().toString() ).listFiles() == null ) {
                    sender.sendMessage("[!] 제작한 음반이 없습니다.");
                    return false;
                }
                player.openInventory(new CdGUI().getInventory(player, 1));
                //제작한 음반이 없으면 빈 화면 또는 " 제작한 음반이 없습니다 "메세지 출력
                break;

            case "set": // /dellunacd set <name> 유저파일에 음반추가 , 오른손에 음반을 들고 명령어 실행.
                onCommandSet(sender, args);
                break;

            case "trade": //dellunacd trade 재료설정 명령어 , 오른손에 재료를 들고 명령어 실행.
                if (!sender.isOp()) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false;
                }
                if (args.length != 1) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false;
                }
                if (!sender.isOp())
                    DataBase.setTrade(((Player) sender).getItemInHand());
                sender.sendMessage("[!] 교환 재료 설정 완료.");
                break;

            case "open": // /dellunacd open <name> , 오피전용 유저 음반 리스트 보기
                if (!sender.isOp() || args.length != 2) {
                    sender.sendMessage("[!] 잘못된 입력입니다.");
                    return false;
                }
                if (new File(plugin.getDataFolder() + "\\" + player.getUniqueId().toString() ).listFiles() == null ) {
                    sender.sendMessage("[!] 해당 플레이어가 제작한 음반이 없습니다.");
                    return false;
                }
                if (sender.isOp()) {
                    player.openInventory(new CdGUI().getInventory(Bukkit.getOfflinePlayer(args[1]) , 1));
                    break;
                }
        }
        return false;

    }

    // /dellunacd set Lewin22
    private void onCommandSet(CommandSender sender, String[] args) {
        if (args.length != 2 || !sender.isOp()) {
            sender.sendMessage("[!] 잘못된 입력입니다.");
            return;
        }

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