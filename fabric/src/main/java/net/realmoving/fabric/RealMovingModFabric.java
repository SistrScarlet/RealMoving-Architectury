package net.realmoving.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.realmoving.RealMovingMod;
import net.realmoving.setup.ClientSetup;
import net.realmoving.setup.ModSetup;

public class RealMovingModFabric implements ModInitializer, ClientModInitializer {

    @Override
    public void onInitialize() {
        RealMovingMod.init();
        ModSetup.init();
    }

    @Override
    public void onInitializeClient() {
        ClientSetup.init();
    }
}
