package bee.bees.mixin;

import bee.bees.Bees;
import bee.bees.client.BeeChestplateModel;
import bee.bees.client.BeeHelmetModel;
import bee.bees.client.PlayerBeeModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.client.renderer.entity.LivingEntityRenderer.getOverlayCoords;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {


	@Shadow
	protected abstract boolean isBodyVisible(LivingEntityRenderState state);

	@Shadow
	protected abstract @Nullable RenderType getRenderType(LivingEntityRenderState state, boolean isBodyVisible, boolean forceTransparent, boolean appearGlowing);

	@Shadow
	protected abstract float getWhiteOverlayProgress(LivingEntityRenderState state);

	@Shadow
	protected abstract int getModelTint(LivingEntityRenderState state);

	@Shadow
	protected abstract void setupRotations(LivingEntityRenderState state, PoseStack poseStack, float bodyRot, float entityScale);

	@Shadow
	protected abstract void scale(LivingEntityRenderState state, PoseStack poseStack);

	@Shadow
	protected abstract boolean shouldRenderLayers(LivingEntityRenderState state);

	@Shadow
	@Final
	protected List<RenderLayer<EntityRenderState, EntityModel<EntityRenderState>>> layers;
	@Unique
	private PlayerBeeModel playerBeeModel;

	@Unique BeeHelmetModel helmetModel;
	@Unique BeeChestplateModel chestplateModel;




	@Inject(at = @At("HEAD"), method = "submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", cancellable = true)
	private void wawa(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {

		if (state instanceof AvatarRenderState avatarRenderState) {
			poseStack.pushPose();
			float scale = state.scale;
			poseStack.scale(scale, scale, scale);
			this.setupRotations(state, poseStack, state.bodyRot, scale);
			poseStack.scale(-1.0F, -1.0F, 1.0F);
			this.scale(state, poseStack);
			poseStack.translate(0.0F, -1.501F, 0.0F);
			boolean isBodyVisible = this.isBodyVisible(state);
			boolean forceTransparent = !isBodyVisible && !state.isInvisibleToPlayer;
			RenderType renderType = this.getRenderType(state, isBodyVisible, false, state.appearsGlowing());
			if (renderType != null) {
				int overlayCoords = getOverlayCoords(state, this.getWhiteOverlayProgress(state));
				int baseColor = forceTransparent ? 654311423 : -1;
				int tintedColor = ARGB.multiply(baseColor, this.getModelTint(state));
				BeeRenderState beeRenderState = new BeeRenderState();
				beeRenderState.bedOrientation = state.bedOrientation;
				beeRenderState.ageInTicks = state.ageInTicks;
				beeRenderState.x = state.x;
				beeRenderState.y = state.y;
				beeRenderState.z = state.z;
				beeRenderState.xRot = state.xRot;
				beeRenderState.yRot = state.yRot;
				beeRenderState.pose = state.pose;

				submitNodeCollector.submitModel(playerBeeModel, beeRenderState, poseStack, renderType, state.lightCoords, overlayCoords, tintedColor, (TextureAtlasSprite)null, state.outlineColor, (ModelFeatureRenderer.CrumblingOverlay)null);


				poseStack.translate(0, 0.045, 0);

				if (!avatarRenderState.headEquipment.isEmpty() && avatarRenderState.headEquipment.get(DataComponents.EQUIPPABLE) != null && avatarRenderState.headEquipment.get(DataComponents.EQUIPPABLE).assetId().isPresent()) {
					Identifier textureLocation = Bees.MAP.get(avatarRenderState.headEquipment.get(DataComponents.EQUIPPABLE).assetId().get());
					submitNodeCollector.submitModel(helmetModel, beeRenderState, poseStack, RenderTypes.armorCutoutNoCull(textureLocation), state.lightCoords, overlayCoords, tintedColor, null, state.outlineColor, null);

				}
				if (!avatarRenderState.chestEquipment.isEmpty() && avatarRenderState.chestEquipment.get(DataComponents.EQUIPPABLE) != null && avatarRenderState.chestEquipment.get(DataComponents.EQUIPPABLE).assetId().isPresent()) {
					Identifier textureLocation = Bees.MAP.get(avatarRenderState.chestEquipment.get(DataComponents.EQUIPPABLE).assetId().get());
					submitNodeCollector.submitModel(chestplateModel, beeRenderState, poseStack, RenderTypes.armorCutoutNoCull(textureLocation), state.lightCoords, overlayCoords, tintedColor, null, state.outlineColor, null);

				}


				if (this.shouldRenderLayers(state) && !this.layers.isEmpty()) {


					for(RenderLayer<EntityRenderState, EntityModel<EntityRenderState>> layer : this.layers) {
						if (!(layer instanceof HumanoidArmorLayer<?,?,?>)) {
							poseStack.translate(0, 0.3, 0);
							layer.submit(poseStack, submitNodeCollector, avatarRenderState.lightCoords, avatarRenderState, avatarRenderState.yRot, avatarRenderState.xRot);
						}
					}


				}
				if (state.leashStates != null) {
					for (EntityRenderState.LeashState leashState : state.leashStates) {
						submitNodeCollector.submitLeash(poseStack, leashState);
					}
				}

				poseStack.popPose();
				ci.cancel();
			}
		}

	}

	@Inject(at = @At("TAIL"), method = "<init>")
	private void init(EntityRendererProvider.Context context, EntityModel model, float shadow, CallbackInfo ci) {
		this.playerBeeModel = new PlayerBeeModel(context.bakeLayer(PlayerBeeModel.LAYER));
		this.helmetModel = new BeeHelmetModel(context.bakeLayer(BeeHelmetModel.HELMET_LAYER));
		this.chestplateModel = new BeeChestplateModel(context.bakeLayer(BeeChestplateModel.CHESTPLATE_LAYER));
	}



}