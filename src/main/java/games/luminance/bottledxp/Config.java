package games.luminance.bottledxp;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = BottledXP.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue MAX_XP = BUILDER
            .comment("The maximum amount of XP that can be stored in a capsule. \nDefault value is 1395, which is the amount of XP required to reach level 30.")
            .defineInRange("maxXP", 1395, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.BooleanValue SHOW_XP_IN_NAME = BUILDER
            .comment("Whether or not to show the amount of XP stored in a capsule in the name of item. \nIf disabled, stored XP will still be shown when hovering over the item in the inventory. \nDefault: true")
            .define("showXPInName", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int maxXP;

    public static boolean showXPInName;



    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        maxXP = MAX_XP.get();
        showXPInName = SHOW_XP_IN_NAME.get();
    }
}
