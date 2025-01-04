package games.luminance.bottledxp.item;

import games.luminance.bottledxp.openmods.utils.EnchantmentUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static games.luminance.bottledxp.Config.maxXP;
import static games.luminance.bottledxp.Config.showXPInName;

public class AdvancedCapsuleItem extends Item {
    public AdvancedCapsuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            if (EnchantmentUtils.getPlayerXP(pPlayer) == 0) {
                return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
            }

            int xp = EnchantmentUtils.getPlayerXP(pPlayer);
            if (xp > maxXP) {
                xp = maxXP;
            }

            pPlayer.getItemInHand(pUsedHand).setCount(pPlayer.getItemInHand(pUsedHand).getCount() - 1);
            ItemStack itemStack = new ItemStack(ModItems.FILLED_ADVANCED_CAPSULE.get());
            itemStack.getOrCreateTag().putInt("stored_xp", xp);
            if (showXPInName) { itemStack.setHoverName(Component.literal("Filled Advanced XP Capsule (" + xp + " XP)")); }
            pPlayer.sendSystemMessage(Component.literal("Stored " + xp + " XP in an advanced capsule"));

            pPlayer.giveExperiencePoints((-1 * xp));
            pPlayer.addItem(itemStack);
        }
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
