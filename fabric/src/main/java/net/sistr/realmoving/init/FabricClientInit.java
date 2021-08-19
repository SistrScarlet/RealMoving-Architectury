package net.sistr.realmoving.init;

import net.fabricmc.api.ClientModInitializer;
import net.realmoving.setup.ClientSetup;

public class FabricClientInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientSetup.init();
    }
}
