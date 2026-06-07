package bee.bees.block;

import bee.bees.Bees;
import bee.bees.block.entity.PlayerBeeHiveBlockEntity;
import bee.bees.registry.ModEntityComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Random;

public class PlayerBeeHiveBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 0, 4);
    public PlayerBeeHiveBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PlayerBeeHiveBlock::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(LEVEL);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (ModEntityComponents.BEEHIVE.get(player).hasPos())  {
            if (level.getBlockState(ModEntityComponents.BEEHIVE.get(player).getPos()).isAir()) {
                ModEntityComponents.BEEHIVE.get(player).setPos(null);
            }
        }


        if (level.getBlockEntity(pos) instanceof PlayerBeeHiveBlockEntity beeHiveBlockEntity) {
            Random random = new Random();
            if (beeHiveBlockEntity.player == null) beeHiveBlockEntity.player = player;
            if (!beeHiveBlockEntity.player.equals(player)) return super.useWithoutItem(state, level, pos, player, hitResult);

            if (state.getValue(LEVEL) == 4) {
                if (!ModEntityComponents.BEEHIVE.get(player).hasPos()) {
                    ModEntityComponents.BEEHIVE.get(player).setPos(pos);
                }
                if (!level.isClientSide()) {
                    ServerLevel beehive = level.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(Bees.MOD_ID, "beehive")));
                    if (!beeHiveBlockEntity.hasBeeHive) beeHiveBlockEntity.createBeehive(beehive, new BlockPos(random.nextInt(-10000, 10000), 60, random.nextInt(-10000, 10000)), pos);

                    player.teleport(new TeleportTransition(beehive, beeHiveBlockEntity.beeHiveCoords.getCenter(), new Vec3(0, 0, 0), player.getYRot(), player.getXRot(), TeleportTransition.DO_NOTHING));
                }

            } else if (player.getFoodData().hasEnoughFood() && !ModEntityComponents.BEEHIVE.get(player).hasPos() && level.dimension().equals(ServerLevel.OVERWORLD)) {
                for (int i = 0; i < 30; i++) {
                    Vec3 center = pos.getCenter();
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, false, false,
                            center.x + random.nextFloat(-1, 1), center.y + random.nextFloat(-1, 1), center.z + random.nextFloat(-1, 1), .5, .5, .5);
                }
                player.getFoodData().addExhaustion(50);
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, state.getValue(LEVEL) + 1));
                return InteractionResult.SUCCESS;
            }
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }



    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new PlayerBeeHiveBlockEntity(worldPosition, blockState);
    }
}
