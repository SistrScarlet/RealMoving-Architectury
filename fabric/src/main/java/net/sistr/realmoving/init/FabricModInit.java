package net.sistr.realmoving.init;

import net.fabricmc.api.ModInitializer;
import net.realmoving.setup.ModSetup;

public class FabricModInit implements ModInitializer {
    @Override
    public void onInitialize() {
        ModSetup.init();
    }
}
