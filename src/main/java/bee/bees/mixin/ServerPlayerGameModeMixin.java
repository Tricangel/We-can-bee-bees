package bee.bees.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {


    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "setGameModeForPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameType;updatePlayerAbilities(Lnet/minecraft/world/entity/player/Abilities;)V", shift = At.Shift.AFTER))
    private void init(GameType gameModeForPlayer, GameType previousGameModeForPlayer, CallbackInfo ci) {
        if (gameModeForPlayer.isSurvival()) {
            player.getAbilities().mayfly = !player.isInWaterOrRain();
            player.getAbilities().setFlyingSpeed(0.06000000238418579f);
        }
    }


}
