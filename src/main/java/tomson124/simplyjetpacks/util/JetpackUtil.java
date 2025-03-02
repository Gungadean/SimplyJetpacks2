package tomson124.simplyjetpacks.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import tomson124.simplyjetpacks.item.JetpackItem;
import top.theillusivec4.curios.api.CuriosApi;

public class JetpackUtil {

    public static ItemStack getFromBothSlots(Player player) {
        ItemStack jetpackItem = ItemStack.EMPTY;
        if (ModList.get().isLoaded("curios")) {
            jetpackItem = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof JetpackItem, player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
        }
        return jetpackItem == ItemStack.EMPTY ? getFromChest(player) : jetpackItem;
    }

    public static ItemStack getFromChest(Player player) {
        return player.getItemBySlot(EquipmentSlot.CHEST);
    }

    public static void removeFromBothSlots(Player player) {
        if (ModList.get().isLoaded("curios")) {
            ItemStack itemStack = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof JetpackItem, player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            CuriosApi.getCuriosHelper().getCurio(itemStack).ifPresent(p -> p.curioBreak(itemStack, player));
        } else {
            player.getInventory().removeItem(getFromChest(player));
        }
    }

    /*
    * Train of thought:
    * Curios slot has priority
    * - If a jetpack is in the curios slot, only use this jetpack
    * - Jetpack must be identified by item stack match
    *
    * */
    public static boolean checkTickForEquippedSlot(int index, ItemStack which, Player player) {
        boolean isNormalChestSlotCorrect = index == EquipmentSlot.CHEST.getIndex() && player.getItemBySlot(EquipmentSlot.CHEST) == which;
        int checkCuriosFlag = checkCuriosSlot(which, player);

        if (checkCuriosFlag >= 0) {
            if (checkCuriosFlag > 0) {
                return checkCuriosFlag > 1;
            } else {
                return isNormalChestSlotCorrect;
            }
        } else {
            return isNormalChestSlotCorrect;
        }
    }

    // -1 if no curios
    // 0 if curios but not correct slot
    // 1 if curios and any jetpack item in slot
    // 2 if curios and correct slot
    private static int checkCuriosSlot(ItemStack which, Player player) {
        if (ModList.get().isLoaded("curios")) {
            ItemStack curioStack = CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem() instanceof JetpackItem, player)
                    .map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
            if (curioStack.isEmpty()) {
                return 0;
            } else {
                if (curioStack == which) {
                    return 2;
                } else {
                    return 1;
                }
            }
        } else {
            return -1;
        }
    }
}
