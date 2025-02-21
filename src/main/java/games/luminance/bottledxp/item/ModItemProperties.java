package games.luminance.bottledxp.item;

import games.luminance.bottledxp.BottledXP;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.XP_CAPSULE.get(), ResourceLocation.fromNamespaceAndPath(BottledXP.MODID, "has_xp"), (itemStack, clientLevel, livingEntity, i) -> {
            if (itemStack.hasTag() && itemStack.getOrCreateTag().contains("stored_xp")) {
                if (itemStack.getOrCreateTag().getInt("stored_xp") > 0) {
                    return 1;
                }
            }
            return 0;
        });
    }
}
