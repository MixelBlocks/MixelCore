package de.mixelblocks.core.item;

import de.mixelblocks.core.util.ChatUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @since 26.01.2022
 * @author LuciferMorningstarDev
 */
public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder displayName(String displayName) {
        itemStack.getItemMeta().displayName(ChatUtil.MixelSerializer.ampersandHEX.deserialize(displayName));
        return this;
    }

    public ItemBuilder name(String displayName) {
        itemStack.getItemMeta().displayName(ChatUtil.MixelSerializer.ampersandHEX.deserialize(displayName));
        return this;
    }

    public ItemBuilder unbreakable(boolean canBreak) {
        itemStack.getItemMeta().setUnbreakable(canBreak);
        return this;
    }

    public ItemBuilder customModelData(int modelId) {
        itemStack.getItemMeta().setCustomModelData(modelId);
        return this;
    }

    public ItemBuilder itemFlag(ItemFlag flag) {
        itemStack.getItemMeta().getItemFlags().add(flag);
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag ... flags) {
        for(ItemFlag flag : flags) itemStack.getItemMeta().getItemFlags().add(flag);
        return this;
    }

    public ItemBuilder enchantment(EnchantmentBuilder enchantment) {
        itemStack.getItemMeta().addEnchant(enchantment.getType(), enchantment.getLevel(), enchantment.isIgnoreLevelRestrictions());
        return this;
    }

    public ItemBuilder enchantments(EnchantmentBuilder ... enchantments) {
        for(EnchantmentBuilder enchantment : enchantments) itemStack.getItemMeta().addEnchant(enchantment.getType(), enchantment.getLevel(), enchantment.isIgnoreLevelRestrictions());
        return this;
    }

    public ItemBuilder lore(String lore) {
        List<Component> lores = new ArrayList<>();
        lores.add(ChatUtil.MixelSerializer.ampersandHEX.deserialize(lore));
        itemStack.getItemMeta().lore(lores);
        return this;
    }

    public ItemBuilder lore(String ... loreLines) {
        List<Component> lores = new ArrayList<>();
        for(String line: loreLines) lores.add(ChatUtil.MixelSerializer.ampersandHEX.deserialize(line));
        itemStack.getItemMeta().lore(lores);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public static class EnchantmentBuilder {

        private Enchantment type;
        private int level;
        private boolean ignoreLevelRestrictions = false;

        public EnchantmentBuilder(Enchantment enchantment, int level) {
            this.type = type;
            this.level = level;
        }

        public EnchantmentBuilder(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
            this.type = type;
            this.level = level;
            this.ignoreLevelRestrictions = ignoreLevelRestrictions;
        }

        public Enchantment getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }

        public boolean isIgnoreLevelRestrictions() {
            return ignoreLevelRestrictions;
        }

        public static EnchantmentBuilder build(Enchantment enchantment, int level) {
            return new EnchantmentBuilder(enchantment, level);
        }

        public static EnchantmentBuilder build(Enchantment enchantment, int level, boolean ignoreLevelRestrictions) {
            return new EnchantmentBuilder(enchantment, level, ignoreLevelRestrictions);
        }
    }
}
