package bee.bees;

import bee.bees.packet.BuildBeehiveC2S;
import bee.bees.registry.ModBlocks;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class BeesClient implements ClientModInitializer {

//    KeyMapping buildBeehiveKey = KeyMappingHelper.registerKeyMapping(
//            new KeyMapping(
//                    "key.bees.build_behive", // The translation key for the key mapping.
//                    InputConstants.Type.KEYSYM, // The type of the keybinding; KEYSYM for keyboard, MOUSE for mouse.
//                    GLFW.GLFW_KEY_H, // The GLFW keycode of the key.
//                    KeyMapping.Category.GAMEPLAY // The category of the mapping.
//            ));
    @Override
    public void onInitializeClient() {


//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (buildBeehiveKey.consumeClick()) {
//                if (client.player != null && client.level != null) {
//                    LocalPlayer player = client.player;
//                    HitResult hitResult = player.pick(5, 1, false);
//
//                    if (hitResult.getType().equals(HitResult.Type.BLOCK)) {
//                        BlockPos pos = ((BlockHitResult) hitResult).getBlockPos();
//                        BlockState state = client.level.getBlockState(pos);
//                        if (state.is(ModBlocks.PLAYER_BEEHIVE) || state.is(BlockTags.LOGS)) {
//                            if (client.player.tickCount % 15 == 0) {
//                                ClientPlayNetworking.send(new BuildBeehiveC2S(client.player.getId()));
//                                player.sendSystemMessage(Component.literal("packet sent"));
//                            }
//                            Vec3 center = pos.getCenter();
//                            for (int i = 0; i < 10; i++) {
//                                client.level.addParticle(ParticleTypes.HAPPY_VILLAGER, center.x, center.y, center.z, 1.5, 1.5, 1.5);
//                            }
//                        }
//                    }
//
//
//                }
//            }
//        });

    }
}
