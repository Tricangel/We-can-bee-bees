package bee.bees.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {




    @ModifyReturnValue(method = "createAttributes", at = @At(value = "RETURN"))
    private static AttributeSupplier.Builder init(AttributeSupplier.Builder original) {

        return original.add(Attributes.MAX_HEALTH, 10);
    }


    @Inject(method = "updateSwimming", at = @At(value = "HEAD"), cancellable = true)
    private void wawa(CallbackInfo ci) {

        Player player = (Player) (Object) this;
        player.setSwimming(false);
        ci.cancel();
    }

    @Inject(method = "isSwimming", at = @At(value = "HEAD"), cancellable = true)
    private void wwaawa(CallbackInfoReturnable<Boolean> cir) {

        cir.setReturnValue(false);
    }

    @Inject(method = "getBlockSpeedFactor", at = @At(value = "HEAD"), cancellable = true)
    private void wwawaawa(CallbackInfoReturnable<Float> cir) {

        cir.setReturnValue(1.0f);
    }

    @ModifyExpressionValue(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;onGround()Z"))
    private boolean wwawaawawa(boolean original) {

        return true;
    }




}
