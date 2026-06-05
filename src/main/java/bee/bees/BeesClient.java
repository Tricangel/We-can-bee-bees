package bee.bees;

import bee.bees.packet.BuildBeehiveC2S;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import org.lwjgl.glfw.GLFW;

public class BeesClient implements ClientModInitializer {

    KeyMapping buildBeehiveKey = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key.bees.build_behive", // The translation key for the key mapping.
                    InputConstants.Type.KEYSYM, // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
                    GLFW.GLFW_KEY_H, // The GLFW keycode of the key.
                    KeyMapping.Category.GAMEPLAY // The category of the mapping.
            ));
    @Override
    public void onInitializeClient() {


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (buildBeehiveKey.consumeClick()) {
                if (client.player != null) {
                    ClientPlayNetworking.send(new BuildBeehiveC2S(client.player.getId()));
                }
            }
        });

    }
}
