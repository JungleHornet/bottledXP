package games.luminance.bottledxp.item;

import games.luminance.bottledxp.BottledXP;
import games.luminance.bottledxp.gui.AdvancedCapsuleGUI;
import games.luminance.bottledxp.openmods.utils.EnchantmentUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraftforge.items.ItemStackHandler;

import static games.luminance.bottledxp.Config.maxXP;
import static games.luminance.bottledxp.Config.showXPInName;

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
        return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));




        /*
        if (!pLevel.isClientSide) {
            if (EnchantmentUtils.getPlayerXP(pPlayer) == 0) {
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }

            int  xxp = EnchantmentUtils.getPlayerXP(pPlayer);
            if (xp > maxXP) {
                xp = maxXP;
            }

            pPlayer.getItemInHand(pUsedHand).shrink(1);
            ItemStack itemStack = new ItemStack(ModItems.FILLED_ADVANCED_CAPSULE.get());
            itemStack.getOrCreateTag().putInt("stored_xp", xp);
            if (showXPInName) {
                itemStack.setHoverName(Component.literal("Filled Advanced XP Capsule (" + xp + " XP)"));
            }
            pPlayer.sendSystemMessage(Component.literal("Stored " + xp + " XP in an advanced capsule"));

            pPlayer.giveExperiencePoints((-1 * xp));
            pPlayer.addItem(itemStack);
        }
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    /*
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.level().isClientSide) {
            if (!itemStack.hasTag() || !itemStack.getOrCreateTag().contains("stored_xp")) {
                return InteractionResultHolder.fail(itemStack);
            }
            pPlayer.level().addFreshEntity(new ExperienceOrb(pPlayer.level(), pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), itemStack.getOrCreateTag().getInt("stored_xp")));
        }
        pPlayer.getItemInHand(pUsedHand).shrink(1);
        pPlayer.addItem(new ItemStack(ModItems.ADVANCED_CAPSULE.get()));


        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
    */
    }

    @Mod.EventBusSubscriber(modid = BottledXP.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
        }
    }
}
