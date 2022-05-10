package net.realmoving.network;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.realmoving.RealMovingMod;
import net.realmoving.util.IActionable;

public class PressActionPacket {
    public static Identifier ID = new Identifier(RealMovingMod.MODID, "press_action");

    @Environment(EnvType.CLIENT)
    public static void sendC2SPacket(ActionType type) {
        PacketByteBuf buf = createC2SPacket(type);
        NetworkManager.sendToServer(ID, buf);
    }

    @Environment(EnvType.CLIENT)
    public static PacketByteBuf createC2SPacket(ActionType type) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeEnumConstant(type);
        return buf;
    }

    public static void receiveC2SPacket(PacketByteBuf buf, NetworkManager.PacketContext ctx) {
        ActionType type = buf.readEnumConstant(ActionType.class);
        ctx.queue(() -> applyC2SPacket((ServerPlayerEntity) ctx.getPlayer(), type));
    }

    public static void applyC2SPacket(ServerPlayerEntity player, ActionType type) {
        switch (type) {
            case ACTION_TRUE:
                ((IActionable) player).setActioning_RealMoving(true);
                break;
            case ACTION_FALSE:
                ((IActionable) player).setActioning_RealMoving(false);
                break;
            case CRAWLING_TRUE:
                ((IActionable) player).setCrawling_RealMoving(true);
                break;
            case CRAWLING_FALSE:
                ((IActionable) player).setCrawling_RealMoving(false);
                break;
            case CLIMBING_TRUE:
                ((IActionable) player).setClimbing_RealMoving(true);
                break;
            case CLIMBING_FALSE:
                ((IActionable) player).setClimbing_RealMoving(false);
                break;
        }
    }

    public enum ActionType {
        ACTION_TRUE,
        ACTION_FALSE,
        CRAWLING_TRUE,
        CRAWLING_FALSE,
        CLIMBING_TRUE,
        CLIMBING_FALSE
    }

}
