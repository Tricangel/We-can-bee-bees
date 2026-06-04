package bee.bees.mixin;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Shadow
    protected abstract AABB getHitbox();

    @Shadow
    protected abstract EntityDimensions getDefaultDimensions(Pose pose);

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void init(CallbackInfo ci) {
        if ((LivingEntity) (Object) this instanceof Player player) {
            if (player.isInWaterOrRain()) {
                player.getAbilities().flying = false;

            }



        }
    }

    @Inject(method = "updateSwimAmount", at = @At(value = "HEAD"), cancellable = true)
    private void wawa(CallbackInfo ci) {
        if ((LivingEntity) (Object) this instanceof Player player) {
            ci.cancel();

        }
    }

    @Inject(method = "getSwimAmount", at = @At(value = "HEAD"), cancellable = true)
    private void wawwa(float a, CallbackInfoReturnable<Float> cir) {
        if ((LivingEntity) (Object) this instanceof Player player) {
            cir.setReturnValue(0f);

        }
    }



}
