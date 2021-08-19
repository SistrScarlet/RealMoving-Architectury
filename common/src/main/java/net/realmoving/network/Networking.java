package net.realmoving.network;

import me.shedaniel.architectury.networking.NetworkManager;

public class Networking {

    public static void init() {
        serverInit();
    }

    public static void serverInit() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, PressActionPacket.ID, PressActionPacket::receiveC2SPacket);
    }

}
