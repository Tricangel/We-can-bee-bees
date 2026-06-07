package bee.bees.block.entity;

import bee.bees.Bees;
import bee.bees.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class PlayerBeeHiveBlockEntity extends BlockEntity {
    public PlayerBeeHiveBlockEntity( BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.PLAYER_BEEHIVE, worldPosition, blockState);
    }

    public Player player;
    public BlockPos beeHiveCoords;
    public BlockPos overworldBeeHiveCoords;
    public boolean hasBeeHive = false;


    public void createBeehive(ServerLevel level, BlockPos pos, BlockPos origin) {
        overworldBeeHiveCoords = origin;
        beeHiveCoords = pos.offset(9, 9 ,9);
        hasBeeHive = true;
        StructureTemplate template = level.getStructureManager().get(Identifier.fromNamespaceAndPath(Bees.MOD_ID, "beehive")).get();
        StructurePlaceSettings placeSettings = (new StructurePlaceSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setKnownShape(true);
        template.placeInWorld(level, pos, pos, placeSettings, RandomSource.create(), 1);
    }

}
