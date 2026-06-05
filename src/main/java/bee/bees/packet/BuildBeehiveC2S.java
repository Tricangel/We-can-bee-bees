package bee.bees.packet;

import bee.bees.Bees;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record BuildBeehiveC2S(int playerId) implements CustomPacketPayload {
    public static final Identifier BUILD_BEEHIVE_ID = Identifier.fromNamespaceAndPath(Bees.MOD_ID, "build_beehive");

    public static final CustomPacketPayload.Type<BuildBeehiveC2S> TYPE = new CustomPacketPayload.Type<>(BUILD_BEEHIVE_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, BuildBeehiveC2S> CODEC = StreamCodec.composite(ByteBufCodecs.INT, BuildBeehiveC2S::playerId, BuildBeehiveC2S::new);



    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
