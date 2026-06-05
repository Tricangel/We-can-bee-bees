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

public class BeeChestplateModel extends EntityModel<BeeRenderState> {
    public static final ModelLayerLocation CHESTPLATE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Bees.MOD_ID, "chestplate"), "main");
    public final ModelPart chestplate;
    public BeeChestplateModel(ModelPart root) {
        super(root);
        this.chestplate = root.getChild("chestplate");
    }

    public static LayerDefinition createChestplate() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition chestplate = partdefinition.addOrReplaceChild("chestplate", CubeListBuilder.create().texOffs(33, 0).addBox(-7.0F, -2.75F, 1.999F, 8.0F, 8.0F, 7.002F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 17.0F, -5.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(BeeRenderState state) {
        super.setupAnim(state);
        super.setupAnim(state);


        if (!state.isAngry && !state.isOnGround) {
            float speed = Mth.cos((double)(state.ageInTicks * 0.18F));
            this.bobUpAndDown(speed, state.ageInTicks);
        }

        float rollAmount = state.rollAmount;
        if (rollAmount > 0.0F) {
            this.chestplate.xRot = Mth.rotLerpRad(rollAmount, this.chestplate.xRot, 3.0915928F);
        }

    }

    protected void bobUpAndDown(final float speed, final float ageInTicks) {
        this.chestplate.xRot = 0.1F + speed * (float)Math.PI * 0.025F;
        this.chestplate.y -= Mth.cos((double)(ageInTicks * 0.18F)) * 0.9F;

    }
}
