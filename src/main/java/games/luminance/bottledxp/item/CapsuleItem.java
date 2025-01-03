package games.luminance.bottledxp.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import games.luminance.bottledxp.openmods.utils.EnchantmentUtils;

import static games.luminance.bottledxp.Config.maxXP;
import static games.luminance.bottledxp.Config.showXPInName;

public class CapsuleItem extends Item {
    public CapsuleItem(Properties properties) {
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
            ItemStack itemStack = new ItemStack(ModItems.FILLED_CAPSULE.get());
            itemStack.getOrCreateTag().putInt("stored_xp", xp);
            if (showXPInName) { itemStack.setHoverName(Component.literal("Filled Experience Capsule (" + xp + " XP)")); }
            pPlayer.sendSystemMessage(Component.literal("Stored " + xp + " XP in a capsule"));

            pPlayer.giveExperiencePoints((-1 * xp));
            pPlayer.addItem(itemStack);
        }
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
