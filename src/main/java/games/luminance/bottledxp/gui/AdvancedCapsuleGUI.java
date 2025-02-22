package games.luminance.bottledxp.gui;

import games.luminance.bottledxp.BottledXP;
import games.luminance.bottledxp.item.AdvancedCapsuleItem;
import games.luminance.bottledxp.item.ModItems;
import games.luminance.bottledxp.openmods.utils.EnchantmentUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultilineTextField;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import static games.luminance.bottledxp.Config.maxXP;
import static games.luminance.bottledxp.Config.showXPInName;

public class AdvancedCapsuleGUI extends Screen {
    private static final Component TITLE = Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.title");
    private static final Component STORE = Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.store");
    private static final Component GET = Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.get");
    private static final net.minecraft.resources.ResourceLocation TEXTURE = new net.minecraft.resources.ResourceLocation(BottledXP.MODID, "textures/gui/advanced_capsule_gui_bg.png");

    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    private net.minecraft.client.gui.components.EditBox textField;
    private Button storeButton, getButton;
    private String logText;

    private Player pPlayer;
    private InteractionHand pUsedHand;

    private Font titleFont;
    public AdvancedCapsuleGUI() {
        super(TITLE);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    public void SetEventParams(Player pPlayer, InteractionHand pUsedHand) {
        this.pPlayer = pPlayer;
        this.pUsedHand = pUsedHand;
    }

    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.logText = "";

        // add widget to take user input in text field
        this.textField = addRenderableWidget(
                new EditBox(this.font, this.leftPos + 11, this.topPos + 40, 150, 25, Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.text_field"))
        );
        this.storeButton = addRenderableWidget(
                Button.builder(
                        STORE,
                        this::store
                ).pos(this.leftPos + 10, this.topPos + 70).size(73, 20).build()
        );
        this.getButton = addRenderableWidget(
                Button.builder(
                        GET,
                        this::get
                ).pos(this.leftPos + 89, this.topPos + 70).size(73, 20).build()
        );
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(graphics, mouseX, mouseY, partialTicks);
        FormattedCharSequence TitleCharSequence = TITLE.getVisualOrderText();
        graphics.drawString(this.font, TITLE, this.leftPos + (this.imageWidth - this.font.width(TitleCharSequence)) / 2, this.topPos + 5, 0x404040, false);
        graphics.drawString(this.font, Component.literal(Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.stored_xp").getString() + pPlayer.getItemInHand(pUsedHand).getOrCreateTag().getInt("stored_xp")), this.leftPos + 10, this.topPos + 20, 0xffffff, true);

        if (this.logText.split("\n").length > 5) {
            String[] logs = this.logText.split("\n");
            this.logText = String.join("\n", Arrays.stream(logs).toList().subList(0, 6));
        }
        graphics.drawWordWrap(this.font, Component.literal(logText), this.leftPos + 10, this.topPos + 100, this.imageWidth - 20, 0x1fff1f);
    }

    private void store(Button button) {
        if (pPlayer == null || pUsedHand == null) {
            return;
        }
        try {
            int xp;
            if (textField.getValue().isEmpty()) {
                xp = maxXP;
            } else {
                xp = Integer.parseInt(textField.getValue(), 10);
            }

            if (xp > maxXP) {
                xp = maxXP;
            }

            int pxp = EnchantmentUtils.getPlayerXP(pPlayer);

            ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
            if (!itemStack.getOrCreateTag().contains("stored_xp")) {
                itemStack.getOrCreateTag().putInt("stored_xp", 0);
            }
            int storedxp = itemStack.getOrCreateTag().getInt("stored_xp");

            if (xp > pxp) {
                xp = pxp;
                textField.setValue(String.valueOf(xp));
            }
            if (xp + storedxp > maxXP) {
                xp = maxXP - storedxp;
                textField.setValue(String.valueOf(xp));
            }
            pPlayer.giveExperiencePoints(-1 * xp);
            itemStack.getOrCreateTag().putInt("stored_xp", storedxp + xp);

            if (showXPInName) {
                itemStack.setHoverName(Component.literal("Filled Advanced XP Capsule (" + (storedxp + xp) + " XP)"));
            }

            pPlayer.sendSystemMessage(Component.literal(String.format(Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.user_message_stored").getString(), xp)));
            this.logText = String.format(Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.log_message_stored").getString(), xp) + "\n" + this.logText;

        } catch (NumberFormatException e) {
            return;
        }
    }

    private void get(Button button) {
        if (pPlayer == null || pUsedHand == null) {
            return;
        }
        try {
            int xp;
            if (textField.getValue().isEmpty()) {
                xp = maxXP;
            } else {
                xp = Integer.parseInt(textField.getValue(), 10);
            }

            ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
            if (!itemStack.getOrCreateTag().contains("stored_xp")) {
                itemStack.getOrCreateTag().putInt("stored_xp", 0);
            }
            int storedxp = itemStack.getOrCreateTag().getInt("stored_xp");

            if (xp > storedxp) {
                xp = storedxp;
                textField.setValue(String.valueOf(xp));
            }
            pPlayer.giveExperiencePoints(xp);
            itemStack.getOrCreateTag().putInt("stored_xp", storedxp - xp);

            if (showXPInName) {
                itemStack.setHoverName(Component.literal("Filled Advanced XP Capsule (" + (storedxp - xp) + " XP)"));
            }
            pPlayer.sendSystemMessage(Component.literal(String.format(Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.user_message_got").getString(), xp)));
            this.logText = String.format(Component.translatable("gui." + BottledXP.MODID + ".advanced_capsule_gui.log_message_got").getString(), xp) + "\n" + this.logText;

        } catch (NumberFormatException e) {
            return;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
