package bee.bees;

import bee.bees.client.BeeChestplateModel;
import bee.bees.client.BeeHelmetModel;
import bee.bees.client.PlayerBeeModel;
import bee.bees.packet.BuildBeehiveC2S;
import bee.bees.registry.ModBlocks;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Bees implements ModInitializer {
	public static final String MOD_ID = "bees";
	public static final List<String> COMMON_FLOWERS = List.of("wildflowers", "pink_petals");
	public static final List<String> UNCOMMON_FLOWERS = List.of("red_tulip", "orange_tulip", "white_tulip", "pink_tulip", "oxeye_daisy", "lily_of_the_valley", "azure_bluet", "allium", "poppy", "dandelion", "cornflower");
	public static final List<String> RARE_FLOWERS = List.of("torchflower", "pitcher_plant", "spore_blossom");
	public static final List<String> BIOME_FLOWERS = List.of("blue_orchid", "closed_eyeblossom", "open_eyeblossom", "cactus_flower");
	public static final List<String> TALL_FLOWERS = List.of("lilac", "sunflower", "rose_bush", "peony");

	public static final List<String> FOODS = List.of("suspicious_stew", "rabbit_stew", "cooked_porkchop", "pumpkin_pie", "cooked_beef", "beetroot_soup", "cooked_chicken", "cooked_mutton", "cooked_salmon", "golden_carrot", "honey_bottle", "mushroom_stew", "baked_potato", "bread", "cooked_cod", "cooked_rabbit", "apple", "chorus_fruit", "golden_apple", "rotten_flesh", "carrot", "beef", "porkchop", "raw_rabbit", "cookie", "glow_berries", "melon_slice", "poisonous_potato", "chicken", "cod", "mutton", "salmon", "spider_eye", "sweet_berries", "beetroot", "dried_kelp", "potato", "pufferfish", "tropical_fish");

	public static final Map<ResourceKey<EquipmentAsset>, Identifier> MAP = Map.of(
			EquipmentAssets.CHAINMAIL, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/chainmail.png"),
			EquipmentAssets.GOLD, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/gold.png"),
			EquipmentAssets.COPPER, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/copper.png"),
			EquipmentAssets.LEATHER, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/leather.png"),
			EquipmentAssets.IRON, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/iron.png"),
			EquipmentAssets.DIAMOND, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/diamond.png"),
			EquipmentAssets.NETHERITE, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/netherite.png"),
			EquipmentAssets.TURTLE_SCUTE, Identifier.fromNamespaceAndPath(MOD_ID, "textures/armor/turtle.png")
	);

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModelLayerRegistry.registerModelLayer(PlayerBeeModel.LAYER, PlayerBeeModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(BeeHelmetModel.HELMET_LAYER, BeeHelmetModel::createHelmet);
		ModelLayerRegistry.registerModelLayer(BeeChestplateModel.CHESTPLATE_LAYER, BeeChestplateModel::createChestplate);

		PayloadTypeRegistry.serverboundPlay().register(BuildBeehiveC2S.TYPE, BuildBeehiveC2S.CODEC);
		ModBlocks.init();


//		ServerPlayNetworking.registerGlobalReceiver(BuildBeehiveC2S.TYPE, ((buildBeehiveC2S, context) -> {
//			Player player = (Player) context.player().level().getEntity(buildBeehiveC2S.playerId());
//
//			if (player == null) return;
//
//			HitResult hitResult = player.pick(5, 1, false);
//
//			if (!hitResult.getType().equals(HitResult.Type.BLOCK)) return;
//			BlockHitResult blockHitResult = (BlockHitResult) hitResult;
//			BlockPos pos = blockHitResult.getBlockPos();
//			Level level = player.level();
//			BlockState state = level.getBlockState(pos);
//			if (state.is(ModBlocks.PLAYER_BEEHIVE) && state.getValue(PlayerBeeHiveBlock.LEVEL) != 4) {
//				player.getFoodData().addExhaustion(50);
//				level.setBlockAndUpdate(pos, state.setValue(PlayerBeeHiveBlock.LEVEL, state.getValue(PlayerBeeHiveBlock.LEVEL) + 1));
//			}
//
//			if (!state.is(BlockTags.LOGS)) return;
//			if (!level.getBlockState(pos.relative(player.getDirection().getOpposite())).isAir()) return;
//			if (player.getFoodData().hasEnoughFood()) {
//				player.getFoodData().addExhaustion(50);
//				level.setBlockAndUpdate(pos.relative(player.getDirection().getOpposite()), ModBlocks.PLAYER_BEEHIVE.defaultBlockState().setValue(BlockStateProperties.FACING, player.getDirection().getOpposite()));
//			}
//
//
//
//
//
//
//		}));

	}
}