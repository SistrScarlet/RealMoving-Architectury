package net.realmoving.setup;

import me.shedaniel.architectury.registry.KeyBindings;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.realmoving.RealMovingMod;
import org.lwjgl.glfw.GLFW;

public class ClientSetup {
    public static KeyBinding action;

    public static void init() {
        action = new KeyBinding(
                RealMovingMod.MODID + ".key.action", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_V, // The keycode of the key
                "key.categories.movement" // The translation key of the keybinding's category.
        );
        KeyBindings.registerKeyBinding(action);
    }

}