package bee.bees.mixin;

import bee.bees.cca.WaterComponent;
import bee.bees.registry.ModEntityComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {


    @Shadow
    protected abstract AABB getHitbox();

    @Shadow
    protected abstract EntityDimensions getDefaultDimensions(Pose pose);

    @Shadow
    @Final
    private AttributeMap attributes;

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void init(CallbackInfo ci) {
        if ((LivingEntity) (Object) this instanceof Player player) {
            WaterComponent component = ModEntityComponents.WATER.get(player);

            if (player.isInWaterOrRain() || component.water > 0) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            } else player.getAbilities().mayfly = true;

            if (player.isInWaterOrRain()) {
                component.water = 30;

            }
            player.fallDistance = 0;

            if (component.water > 0 && !player.isInWater()) {
                for (int i = 0; i < 3 * ( component.water * .05); i++) {
                    Random random = new Random();
                    player.level().addParticle(ParticleTypes.RAIN, player.getX() + random.nextDouble(-0.4, 0.4), player.getEyeY(), player.getZ() + random.nextDouble(-0.4, 0.4), 0, 0, 0);
                }
            }

            if (player.getAbilities().flying) {
                player.setSprinting(false);
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

    @Inject(method = "onAttributeUpdated", at = @At(value = "HEAD"), cancellable = true)
    private void wawwwawaawaa(Holder<Attribute> attribute, CallbackInfo ci) {
        if ((LivingEntity) (Object) this instanceof Player player) {
            if (attribute.equals(Attributes.FLYING_SPEED)) {
                player.getAbilities().setFlyingSpeed((float) this.attributes.getValue(attribute));
            }

        }
    }

    @Inject(method = "hurtServer", at = @At(value = "HEAD"), cancellable = true)
    private void wawwwwaawaawaa(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if ((LivingEntity) (Object) this instanceof Player player) {
            if (source.getEntity() != null) {
                if (source.getEntity().is(EntityType.DROWNED)) {
                    ModEntityComponents.WATER.get(player).water = 30;
                }
            }

        }
    }



}
