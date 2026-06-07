package bee.bees.datagen;

import bee.bees.registry.ModBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class ModModelGen extends FabricModelProvider {
    public ModModelGen(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createTrivialCube(ModBlocks.EXIT_BLOCK);


    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

    }
}
