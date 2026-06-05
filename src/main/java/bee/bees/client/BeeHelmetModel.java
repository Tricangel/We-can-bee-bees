package bee.bees.client;

import bee.bees.Bees;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class BeeHelmetModel extends EntityModel<BeeRenderState> {
    public static final ModelLayerLocation HELMET_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Bees.MOD_ID, "helmet"), "main");
    public final ModelPart helmet;
    public BeeHelmetModel(ModelPart root) {
        super(root);
        this.helmet = root.getChild("helmet");
    }

    public static LayerDefinition createHelmet() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-7.001F, -3.0F, -1.0F, 8.002F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 17.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(BeeRenderState state) {
        super.setupAnim(state);

        if (!state.isAngry && !state.isOnGround) {
            float speed = Mth.cos((double)(state.ageInTicks * 0.18F));
            this.bobUpAndDown(speed, state.ageInTicks);
        }

        float rollAmount = state.rollAmount;
        if (rollAmount > 0.0F) {
            this.helmet.xRot = Mth.rotLerpRad(rollAmount, this.helmet.xRot, 3.0915928F);
        }

    }

    protected void bobUpAndDown(final float speed, final float ageInTicks) {
        this.helmet.xRot = 0.1F + speed * (float)Math.PI * 0.025F;
        this.helmet.y -= Mth.cos((double)(ageInTicks * 0.18F)) * 0.9F;

    }
}
