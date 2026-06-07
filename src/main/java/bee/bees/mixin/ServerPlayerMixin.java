package bee.bees.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {



    @Inject(method = "checkMovementStatistics", at = @At(value = "HEAD"), cancellable = true)
    private void wawa(double dx, double dy, double dz, CallbackInfo ci) {
        ServerPlayer serverPlayer = (ServerPlayer) (Object) this;
        if (serverPlayer.getAbilities().flying) {
            int horizontalDistance = Math.round((float)Math.sqrt(dx * dx + dz * dz) * 100.0F);
            serverPlayer.causeFoodExhaustion(0.13F * horizontalDistance * 0.01F);
        }
    }





}
