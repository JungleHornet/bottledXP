package games.luminance.bottledxp.item;

import games.luminance.bottledxp.BottledXP;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BottledXP.MODID);

    public static final RegistryObject<Item> XP_CAPSULE = ITEMS.register("capsule", () -> new CapsuleItem(new Item.Properties().rarity(Rarity.RARE).setNoRepair()));

    public static final RegistryObject<Item> FILLED_CAPSULE = ITEMS.register("filled_capsule", () -> new FilledCapsuleItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).setNoRepair()));

    public static final RegistryObject<Item> ADVANCED_CAPSULE = ITEMS.register("advanced_capsule", () -> new AdvancedCapsuleItem(new Item.Properties().rarity(Rarity.RARE).setNoRepair()));

    public static final RegistryObject<Item> FILLED_ADVANCED_CAPSULE = ITEMS.register("filled_advanced_capsule", () -> new FilledAdvancedCapsuleItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).setNoRepair()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
