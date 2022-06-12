package com.firework.client.Implementations.Mixins.MixinsList;

import com.firework.client.Features.Modules.Render.ItemViewModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ItemRenderer.class})
public class ViewModelMixin {
    @Inject(method={"renderItemSide"}, at={@At(value="HEAD")})
    public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
        if (ItemViewModel.enabled.getValue().booleanValue()) {
            GlStateManager.scale((double)(ItemViewModel.scaleX.getValue() / 100.0), (double)(ItemViewModel.scaleY.getValue() / 100.0), (double)(ItemViewModel.scaleZ.getValue() / 100.0));
            if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
                GlStateManager.translate((double)(ItemViewModel.translateX.getValue() / 100.0), (double)(ItemViewModel.translateY.getValue() / 100.0), (double)(ItemViewModel.translateZ.getValue() / 100.0));
                GlStateManager.rotate((float)ItemViewModel.rotateXR.getValue().floatValue(), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.rotate((float)ItemViewModel.rotateYR.getValue().floatValue(), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)ItemViewModel.rotateZR.getValue().floatValue(), (float)0.0f, (float)0.0f, (float)1.0f);
            } else if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
                GlStateManager.translate((double)(-ItemViewModel.translateX.getValue().doubleValue() / 100.0), (double)(ItemViewModel.translateY.getValue() / 100.0), (double)(ItemViewModel.translateZ.getValue() / 100.0));
                GlStateManager.rotate((float)(-ItemViewModel.rotateXL.getValue().floatValue()), (float)1.0f, (float)0.0f, (float)0.0f);
                GlStateManager.rotate((float)ItemViewModel.rotateYL.getValue().floatValue(), (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)ItemViewModel.rotateZL.getValue().floatValue(), (float)0.0f, (float)0.0f, (float)1.0f);
            }
        }
    }
}
