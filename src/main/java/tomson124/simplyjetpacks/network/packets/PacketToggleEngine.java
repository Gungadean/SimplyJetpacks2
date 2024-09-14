package tomson124.simplyjetpacks.network.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import tomson124.simplyjetpacks.item.JetpackItem;
import tomson124.simplyjetpacks.util.JetpackUtil;

import java.util.function.Supplier;

public class PacketToggleEngine {

    public PacketToggleEngine(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public PacketToggleEngine() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ItemStack stack = JetpackUtil.getFromBothSlots(player);
                Item item = stack.getItem();
                if (item instanceof JetpackItem) {
                    JetpackItem jetpack = (JetpackItem) item;
                    jetpack.toggleEngine(stack, player);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}