package com.kaokod.fpm_x_compat;

import com.kaokod.fpm_x_compat.config.ForgeClientConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public final class ClothConfigScreenFactory {
    private ClothConfigScreenFactory() {
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("fpm_x_compat.config.title"));
        ConfigEntryBuilder entries = builder.entryBuilder();

        ConfigCategory global = builder.getOrCreateCategory(Component.translatable("fpm_x_compat.config.category.global"));
        global.addEntry(entries.startDoubleField(Component.translatable("fpm_x_compat.config.body_y_offset"), ForgeClientConfig.BODY_Y_OFFSET.get())
                .setDefaultValue(ForgeClientConfig.DEFAULT_BODY_Y_OFFSET)
                .setMin(-2.0)
                .setMax(2.0)
                .setSaveConsumer(ForgeClientConfig.BODY_Y_OFFSET::set)
                .build());
        global.addEntry(entries.startDoubleField(Component.translatable("fpm_x_compat.config.bias_smoothing_speed"), ForgeClientConfig.BIAS_SMOOTHING_SPEED.get())
                .setDefaultValue(ForgeClientConfig.DEFAULT_BIAS_SMOOTHING_SPEED)
                .setMin(0.0)
                .setMax(1.0)
                .setSaveConsumer(ForgeClientConfig.BIAS_SMOOTHING_SPEED::set)
                .build());
        global.addEntry(entries.startStrList(Component.translatable("fpm_x_compat.config.ignored_accessory_slots"), getIgnoredAccessorySlots())
                .setDefaultValue(ForgeClientConfig.DEFAULT_IGNORED_ACCESSORY_SLOTS)
                .setSaveConsumer(ForgeClientConfig.IGNORED_ACCESSORY_SLOTS::set)
                .build());

        addArmCategory(builder, entries, true);
        addArmCategory(builder, entries, false);

        builder.setSavingRunnable(ForgeClientConfig.SPEC::save);
        return builder.build();
    }

    private static void addArmCategory(ConfigBuilder builder, ConfigEntryBuilder entries, boolean right) {
        ConfigCategory category = builder.getOrCreateCategory(Component.translatable(
                right ? "fpm_x_compat.config.category.right_arm" : "fpm_x_compat.config.category.left_arm"
        ));

        addDouble(category, entries, "pitch_multiplier", right ? ForgeClientConfig.RIGHT_ARM_PITCH_MULTIPLIER : ForgeClientConfig.LEFT_ARM_PITCH_MULTIPLIER, 0.0, 2.0, ForgeClientConfig.DEFAULT_ARM_PITCH_MULTIPLIER);
        addDouble(category, entries, "pitch_offset", right ? ForgeClientConfig.RIGHT_ARM_PITCH_OFFSET : ForgeClientConfig.LEFT_ARM_PITCH_OFFSET, -2.0, 2.0, ForgeClientConfig.DEFAULT_ARM_PITCH_OFFSET);
        addDouble(category, entries, "pitch_curve", right ? ForgeClientConfig.RIGHT_ARM_PITCH_CURVE : ForgeClientConfig.LEFT_ARM_PITCH_CURVE, 0.0, 4.0, ForgeClientConfig.DEFAULT_ARM_PITCH_CURVE);
        addDouble(category, entries, "pitch_curve_floor", right ? ForgeClientConfig.RIGHT_ARM_PITCH_CURVE_FLOOR : ForgeClientConfig.LEFT_ARM_PITCH_CURVE_FLOOR, 0.0, 1.0, ForgeClientConfig.DEFAULT_ARM_PITCH_CURVE_FLOOR);
        addDouble(category, entries, "upper_pitch_bias", right ? ForgeClientConfig.RIGHT_ARM_UPPER_PITCH_BIAS : ForgeClientConfig.LEFT_ARM_UPPER_PITCH_BIAS, -2.0, 2.0, ForgeClientConfig.DEFAULT_ARM_UPPER_PITCH_BIAS);
        addDouble(category, entries, "lower_pitch_bias", right ? ForgeClientConfig.RIGHT_ARM_LOWER_PITCH_BIAS : ForgeClientConfig.LEFT_ARM_LOWER_PITCH_BIAS, -2.0, 2.0, ForgeClientConfig.DEFAULT_ARM_LOWER_PITCH_BIAS);
        addDouble(category, entries, "position_x_offset", right ? ForgeClientConfig.RIGHT_ARM_POSITION_X_OFFSET : ForgeClientConfig.LEFT_ARM_POSITION_X_OFFSET, -4.0, 4.0, ForgeClientConfig.DEFAULT_ARM_POSITION_X_OFFSET);
        addDouble(category, entries, "position_y_offset", right ? ForgeClientConfig.RIGHT_ARM_POSITION_Y_OFFSET : ForgeClientConfig.LEFT_ARM_POSITION_Y_OFFSET, -4.0, 4.0, ForgeClientConfig.DEFAULT_ARM_POSITION_Y_OFFSET);
        addDouble(category, entries, "position_z_offset", right ? ForgeClientConfig.RIGHT_ARM_POSITION_Z_OFFSET : ForgeClientConfig.LEFT_ARM_POSITION_Z_OFFSET, -4.0, 6.0, ForgeClientConfig.DEFAULT_ARM_POSITION_Z_OFFSET);
        addDouble(category, entries, "look_up_z_shift", right ? ForgeClientConfig.RIGHT_ARM_LOOK_UP_Z_SHIFT : ForgeClientConfig.LEFT_ARM_LOOK_UP_Z_SHIFT, -4.0, 6.0, ForgeClientConfig.DEFAULT_ARM_LOOK_UP_Z_SHIFT);
    }

    private static void addDouble(ConfigCategory category, ConfigEntryBuilder entries, String key, net.minecraftforge.common.ForgeConfigSpec.DoubleValue value, double min, double max, double defaultValue) {
        category.addEntry(entries.startDoubleField(Component.translatable("fpm_x_compat.config." + key), value.get())
                .setDefaultValue(defaultValue)
                .setMin(min)
                .setMax(max)
                .setSaveConsumer(value::set)
                .build());
    }

    private static List<String> getIgnoredAccessorySlots() {
        return new ArrayList<>(ForgeClientConfig.IGNORED_ACCESSORY_SLOTS.get());
    }
}
