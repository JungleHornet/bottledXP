package games.luminance.bottledxp.item;

import games.luminance.bottledxp.BottledXP;
import games.luminance.bottledxp.gui.AdvancedCapsuleGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class AdvancedCapsuleItem extends Item {
    private AdvancedCapsuleGUI gui;

    public AdvancedCapsuleItem(Properties properties) {
        super(properties);
        gui = new AdvancedCapsuleGUI();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            gui.SetEventParams(pPlayer, pUsedHand);
            Minecraft.getInstance().setScreen(gui);
        });
        ModItemProperties.addCustomItemProperties();
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
    }

    @Mod.EventBusSubscriber(modid = BottledXP.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
        }
    }
}
