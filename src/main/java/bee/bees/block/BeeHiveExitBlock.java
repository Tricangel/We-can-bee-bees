package bee.bees.block;

import bee.bees.cca.BeeHiveComponent;
import bee.bees.registry.ModEntityComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.core.jmx.Server;

public class BeeHiveExitBlock extends Block {
    public BeeHiveExitBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        BeeHiveComponent beeHive = ModEntityComponents.BEEHIVE.get(player);
        if (beeHive.hasPos() && !level.isClientSide()) {

            ServerLevel overworld = level.getServer().overworld();
            Direction direction = overworld.getBlockState(beeHive.getPos()).getValue(BlockStateProperties.FACING);
            player.teleport(new TeleportTransition(overworld, beeHive.getPos().relative(direction).getCenter(), Vec3.ZERO, player.getYRot(), player.getXRot(), TeleportTransition.DO_NOTHING));
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}
