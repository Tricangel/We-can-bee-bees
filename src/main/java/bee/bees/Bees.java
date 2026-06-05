package bee.bees;

import bee.bees.client.PlayerBeeModel;
import bee.bees.packet.BuildBeehiveC2S;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Bees implements ModInitializer {
	public static final String MOD_ID = "bees";
	public static final List<String> COMMON_FLOWERS = List.of("wildflowers", "poppy", "dandelion", "pink_petals");
	public static final List<String> UNCOMMON_FLOWERS = List.of("red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "lily_of_the_valley", "azure_bluet", "allium");
	public static final List<String> RARE_FLOWERS = List.of("torchflower", "pitcher_plant", "spore_blossom");
	public static final List<String> BIOME_FLOWERS = List.of("blue_orchid", "closed_eyeblossom", "open_eyeblossom", "cactus_flower");
	public static final List<String> TALL_FLOWERS = List.of("lilac", "sunflower", "rose_bush", "peony");

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModelLayerRegistry.registerModelLayer(PlayerBeeModel.LAYER, PlayerBeeModel::createBodyLayer);
		PayloadTypeRegistry.serverboundPlay().register(BuildBeehiveC2S.TYPE, BuildBeehiveC2S.CODEC);


		ServerPlayNetworking.registerGlobalReceiver(BuildBeehiveC2S.TYPE, ((buildBeehiveC2S, context) -> {
			Player player = (Player) context.player().level().getEntity(buildBeehiveC2S.playerId());

			if (player == null) return;

			HitResult hitResult = player.pick(5, 1, false);

			if (!hitResult.getType().equals(HitResult.Type.BLOCK)) return;
			BlockHitResult blockHitResult = (BlockHitResult) hitResult;
			BlockPos pos = blockHitResult.getBlockPos();

			context.player().level().setBlockAndUpdate(pos, Blocks.BEEHIVE.defaultBlockState());


		}));

	}
}