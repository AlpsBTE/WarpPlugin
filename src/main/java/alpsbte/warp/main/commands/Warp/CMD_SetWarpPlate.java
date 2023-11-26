package alpsbte.warp.main.commands.Warp;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CMD_SetWarpPlate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, java.lang.@NotNull String s, java.lang.@NotNull String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        if (!p.hasPermission("alpsbte.moderator")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
        }

        if (args.length == 1) {
            if (Warp.exists(args[0])) {
                // Add Warp Plate to database
                Warp warp = new Warp(args[0]);
                warp.addWarpPlate(p.getLocation());

                Main.getWarpPlateList().put(p.getLocation(),warp.getName());

                // Set Blocks
                Block[] blocks = new Block[7];

                for (int i = 0; i < 7; i++) {
                    blocks[i] = p.getWorld().getBlockAt(p.getLocation().add(0,i,0));
                }

                blocks[0].setType(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
                blocks[1].setType(Material.AIR);
                blocks[2].setType(Material.RED_STAINED_GLASS_PANE,true);
                blocks[3].setType(Material.HOPPER);
                blocks[4].setType(Material.ANVIL);
                blocks[5].setType(Material.COBBLESTONE_WALL);
                blocks[6].setType(Material.IRON_BARS);

                //Set Hologram
                Location hologramLocation = new Location(
                        p.getLocation().getWorld(),
                        Math.floor(p.getLocation().getX()) + 0.5,
                        Math.floor(p.getLocation().getY()) + 1.5,
                        Math.floor(p.getLocation().getZ()) + 0.5);

                p.sendMessage(Utils.getInfoMessageFormat("Successfully placed warp plate for warp " + warp.getName() + "!"));
            } else {
                p.sendMessage(Utils.getErrorMessageFormat("Could not find warp " + args[0] + "!"));
            }
        } else {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /setwarpplate <name>"));
        }
        return false;
    }
}
