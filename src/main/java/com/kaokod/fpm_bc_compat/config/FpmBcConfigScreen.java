package com.kaokod.fpm_bc_compat.config;

import com.kaokod.fpm_bc_compat.FpmBcCompatMod;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class FpmBcConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("fpm_bc_compat.config.title").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD));

        FpmBcConfig.Client config = FpmBcConfig.CLIENT;
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory rightArmCat = builder.getOrCreateCategory(Component.translatable("fpm_bc_compat.config.category.right_arm"));
        addArmEntries(rightArmCat, entryBuilder, 
            config.rightArmPitchMultiplier, config.rightArmPitchOffset, 
            config.rightArmPitchCurve, config.rightArmPitchCurveFloor, 
            config.rightArmUpperPitchBias, config.rightArmLowerPitchBias,
            config.rightArmPositionXOffset, config.rightArmPositionYOffset, config.rightArmPositionZOffset,
            config.rightArmLookUpZShift);

        ConfigCategory leftArmCat = builder.getOrCreateCategory(Component.translatable("fpm_bc_compat.config.category.left_arm"));
        addArmEntries(leftArmCat, entryBuilder, 
            config.leftArmPitchMultiplier, config.leftArmPitchOffset, 
            config.leftArmPitchCurve, config.leftArmPitchCurveFloor, 
            config.leftArmUpperPitchBias, config.leftArmLowerPitchBias,
            config.leftArmPositionXOffset, config.leftArmPositionYOffset, config.leftArmPositionZOffset,
            config.leftArmLookUpZShift);

        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("fpm_bc_compat.config.category.general"));
        general.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.body_y_offset"), config.bodyYOffset.get())
                .setDefaultValue(-0.1).setSaveConsumer(config.bodyYOffset::set).build());

        general.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.transition_speed"), config.biasSmoothingSpeed.get())
                .setDefaultValue(0.1).setSaveConsumer(config.biasSmoothingSpeed::set).build());

        builder.setSavingRunnable(() -> FpmBcCompatMod.MOD_LOGGER.info("FPM x Better Combat Configuration Saved."));
        return builder.build();
    }

    private static void addArmEntries(ConfigCategory category, ConfigEntryBuilder entryBuilder,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue multiplier,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue pitchOffset,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue pitchCurve,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue curveFloor,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue upperBias,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue lowerBias,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue posX,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue posY,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue posZ,
                                     net.neoforged.neoforge.common.ModConfigSpec.DoubleValue lookUpZShift) {
        
        category.addEntry(entryBuilder.startTextDescription(Component.translatable("fpm_bc_compat.config.header.rotation").withStyle(ChatFormatting.BLUE, ChatFormatting.UNDERLINE)).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.pitch_multiplier"), multiplier.get())
                .setDefaultValue(0.8).setSaveConsumer(multiplier::set).build());
        
        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.pitch_offset"), pitchOffset.get())
                .setDefaultValue(0.15).setSaveConsumer(pitchOffset::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.upper_bias"), upperBias.get())
                .setDefaultValue(0.1).setSaveConsumer(upperBias::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.lower_bias"), lowerBias.get())
                .setDefaultValue(0.1).setSaveConsumer(lowerBias::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.curve_correction"), pitchCurve.get())
                .setDefaultValue(0.0).setSaveConsumer(pitchCurve::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.curve_floor"), curveFloor.get())
                .setDefaultValue(0.75).setSaveConsumer(curveFloor::set).build());

        category.addEntry(entryBuilder.startTextDescription(Component.translatable("fpm_bc_compat.config.header.position").withStyle(ChatFormatting.BLUE, ChatFormatting.UNDERLINE)).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.pos_x"), posX.get())
                .setDefaultValue(0.0).setSaveConsumer(posX::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.pos_y"), posY.get())
                .setDefaultValue(0.2).setSaveConsumer(posY::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.pos_z"), posZ.get())
                .setDefaultValue(2.0).setSaveConsumer(posZ::set).build());

        category.addEntry(entryBuilder.startDoubleField(Component.translatable("fpm_bc_compat.config.lookup_z_shift"), lookUpZShift.get())
                .setDefaultValue(2.0).setSaveConsumer(lookUpZShift::set).build());
    }
}
