package games.luminance.bottledxp.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FilledCapsuleItem extends Item {

    public FilledCapsuleItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pPlayer.level().isClientSide) {
            if (!itemStack.hasTag() || !itemStack.getOrCreateTag().contains("stored_xp")) {
                return InteractionResultHolder.fail(itemStack);
            }
            pPlayer.level().addFreshEntity(new ExperienceOrb(pPlayer.level(), pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), itemStack.getOrCreateTag().getInt("stored_xp")));
        }
        pPlayer.getItemInHand(pUsedHand).setCount(0);
        pPlayer.addItem(new ItemStack(ModItems.XP_CAPSULE.get()));


        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
