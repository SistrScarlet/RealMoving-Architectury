package net.realmoving.mixin;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.realmoving.util.IActionable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public abstract class MixinActionModel<T extends LivingEntity> extends AnimalModel<T> {

    @Shadow public float leaningPitch;
    @Shadow public ModelPart leftArm;
    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart rightLeg;
    @Shadow public ModelPart leftLeg;
    private boolean isCrawling;

    @Inject(at = @At("RETURN"), method = "animateModel")
    public void onSetLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick, CallbackInfo ci) {
        this.isCrawling = 0 < entityIn.getLeaningPitch(partialTick) && !entityIn.isTouchingWater();
    }

    @Inject(at = @At("RETURN"), method = "setAngles")
    public void onSetRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        float rad = (float) Math.PI / 180F;

        if (isCrawling) {
            float limbRange = 5;
            //0 < x < limbRange
            float limb = limbSwing % limbRange;
            //0 < x <= 1
            float swim = this.handSwingProgress > 0.0F ? 0.0F : this.leaningPitch;

            float armD = 165F;
            float armU = 180F;
            float armI = 0F;
            float armO = 20F;
            float legD = 15F;
            float legU = 0F;
            float legI = 0F;
            float legO = 20F;

            //PlayerModelをMixinしてやろうと思ったけど別にPlayerModelはプレイヤー限定ではないのだ…
            if (entityIn instanceof IActionable && ((IActionable) entityIn).isSliding_RealMoving()) {
                this.leftArm.pitch = MathHelper.lerp(swim, this.leftArm.pitch, -armU * rad);
                this.leftArm.yaw = MathHelper.lerp(swim, this.leftArm.yaw, 0F * rad);
                this.leftArm.roll = MathHelper.lerp(swim, this.leftArm.roll, armO * rad);
                this.rightArm.pitch = MathHelper.lerp(swim, this.rightArm.pitch, -armU * rad);
                this.rightArm.yaw = MathHelper.lerp(swim, this.rightArm.yaw, 0F * rad);
                this.rightArm.roll = MathHelper.lerp(swim, this.rightArm.roll, -armO * rad);
                this.leftLeg.pitch = MathHelper.lerp(swim, this.leftLeg.pitch, -legU * rad);
                this.leftLeg.roll = MathHelper.lerp(swim, this.leftLeg.roll, -legO * rad);
                this.rightLeg.pitch = MathHelper.lerp(swim, this.rightLeg.pitch, -legU * rad);
                this.rightLeg.roll = MathHelper.lerp(swim, this.rightLeg.roll, legO * rad);
            }//接地している手足はzが増す
            // 上げる手足はzを減らしxを上げ下げ
            else if (limb < limbRange / 2) {
                float pct = limb / (limbRange / 2);
                float half = 1 - Math.abs((pct - 0.5F) * 2);

                //下げ
                this.leftArm.pitch = MathHelper.lerp(swim, this.leftArm.pitch, -armD * rad);
                this.leftArm.yaw = MathHelper.lerp(swim, this.leftArm.yaw, 0F * rad);
                this.leftArm.roll = lerp(swim, this.leftArm.roll, pct, armI, armO);
                //上げ
                this.rightArm.pitch = lerp(swim, this.rightArm.pitch, half, -armD, -armU);
                this.rightArm.yaw = MathHelper.lerp(swim, this.rightArm.yaw, 0F * rad);
                this.rightArm.roll = lerp(swim, this.rightArm.roll, pct, -armO, -armI);
                //上げ
                this.leftLeg.pitch = lerp(swim, this.leftLeg.pitch, half, -legD, -legU);
                this.leftLeg.roll = lerp(swim, this.leftLeg.roll, pct, -legO, -legI);
                //下げ
                this.rightLeg.pitch = MathHelper.lerp(swim, this.rightLeg.pitch, -legD * rad);
                this.rightLeg.roll = lerp(swim, this.rightLeg.roll, pct, legI, legO);
            } else {
                float pct = (limb - limbRange / 2) / (limbRange / 2);
                float half = 1 - Math.abs((pct - 0.5F) * 2);
                //上げ
                this.leftArm.pitch = lerp(swim, this.leftArm.pitch, half, -armD, -armU);
                this.leftArm.yaw = MathHelper.lerp(swim, this.leftArm.yaw, 0F * rad);
                this.leftArm.roll = lerp(swim, this.leftArm.roll, pct, armO, armI);
                //下げ
                this.rightArm.pitch = MathHelper.lerp(swim, this.rightArm.pitch, -armD * rad);
                this.rightArm.yaw = MathHelper.lerp(swim, this.rightArm.yaw, 0F * rad);
                this.rightArm.roll = lerp(swim, this.rightArm.roll, pct, -armI, -armO);
                //下げ
                this.leftLeg.pitch = MathHelper.lerp(swim, this.leftLeg.pitch, -legD * rad);
                this.leftLeg.roll = lerp(swim, this.leftLeg.roll, pct, -legI, -legO);
                //上げ
                this.rightLeg.pitch = lerp(swim, this.rightLeg.pitch, half, -legD, -legU);
                this.rightLeg.roll = lerp(swim, this.rightLeg.roll, pct, legO, legI);
            }
        } else if (entityIn instanceof IActionable && ((IActionable) entityIn).isClimbing_RealMoving()) {
            float pct = climbProgress((IActionable) entityIn);
            float armC = 5F;
            float armU = 160F;
            float armO = 5F;
            this.leftArm.pitch = MathHelper.lerp(pct, -armU * rad, -armC * rad);
            this.leftArm.yaw = MathHelper.lerp(pct, 0 * rad, 0F * rad);
            this.leftArm.roll = MathHelper.lerp(pct, armO * rad, -armO * rad);
            this.rightArm.pitch = MathHelper.lerp(pct, -armU * rad, -armC * rad);
            this.rightArm.yaw = MathHelper.lerp(pct, 0 * rad, 0F * rad);
            this.rightArm.roll = MathHelper.lerp(pct, -armO * rad, armO * rad);
        }

    }

    private static float lerp(float swim, float armLeg, float pct, float start, float end) {
        return MathHelper.lerp(swim, armLeg,
                MathHelper.lerp(pct, start * (float) Math.PI / 180F, end * (float) Math.PI / 180F));
    }

    private static float climbProgress(IActionable actionable) {
        float climbHeight = actionable.getClimbHeight_RealMoving();
        return 1F - MathHelper.clamp(climbHeight / 2F, 0F, 1F);
    }

}
