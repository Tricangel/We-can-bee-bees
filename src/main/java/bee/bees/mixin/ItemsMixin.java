package bee.bees.mixin;

import bee.bees.Bees;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Items.class)
public abstract class ItemsMixin {




    @ModifyVariable(method = "registerItem(Ljava/lang/String;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", at = @At(value = "HEAD"), argsOnly = true)
    private static Item.Properties init(Item.Properties value, String name) {
        if (Bees.FOODS.contains(name)) return value.food(new FoodProperties(0, 0, false), Consumables.POISONOUS_POTATO);
        if (name.equals("enchanted_golden_apple")) return value.food(Foods.POTATO, Consumables.ENCHANTED_GOLDEN_APPLE);
    return value;
    }


    @ModifyVariable(method = "registerBlock(Lnet/minecraft/world/level/block/Block;Ljava/util/function/BiFunction;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", at = @At(value = "HEAD"), argsOnly = true)
    private static Item.Properties wawa(Item.Properties value, Block block) {
        String name = block.getDescriptionId();
        name = name.replace("block.minecraft.", "");
        if (Bees.COMMON_FLOWERS.contains(name)) return value.food(Foods.POTATO);
        if (Bees.UNCOMMON_FLOWERS.contains(name)) return value.food(Foods.APPLE);
        if (Bees.RARE_FLOWERS.contains(name)) return value.food(Foods.COOKED_BEEF);
        if (Bees.BIOME_FLOWERS.contains(name)) return value.food(Foods.BEETROOT_SOUP);
        if (Bees.TALL_FLOWERS.contains(name)) return value.food(Foods.COOKED_COD);
        return value;
    }


}
