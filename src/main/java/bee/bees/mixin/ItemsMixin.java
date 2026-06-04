package bee.bees.mixin;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Items.class)
public abstract class ItemsMixin {




    @ModifyVariable(method = "reg/world/item/Item;", at = @At(value = "HEAD"), argsOnly = true)
    private static Item.Properties init(Item.Properties value, ResourceKey<Item> key) {
        if (value.itemId().)
    }


    @ModifyVariable(method = "registerBlock(Lnet/minecraft/world/level/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", at = @At(value = "HEAD"), argsOnly = true)
    private static Item.Properties wawa(Item.Properties value, Block block) {
            if (block.getName().toString().contains("flower")) return value.food(Foods.APPLE);
        return value;
    }


}
