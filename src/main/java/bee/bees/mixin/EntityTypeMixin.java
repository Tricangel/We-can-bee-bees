package bee.bees.mixin;

import bee.bees.client.PlayerBeeModel;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(EntityType.class)
public class EntityTypeMixin {

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 0.6F, ordinal = 31))
    private static float init(float constant) {
        return 0.7F;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 1.8F, ordinal = 2))
    private static float wa(float constant) {
        return 0.6F;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(floatValue = 1.62F, ordinal = 4))
    private static float wawa(float constant) {
        return 0.3F;
    }

}
