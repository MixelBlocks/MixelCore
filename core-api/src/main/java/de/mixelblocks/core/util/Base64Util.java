package de.mixelblocks.core.util;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

/**
 * Static access to all methods ( you cannot instantiate an object )
 *
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class Base64Util {

    private Base64Util() {} // prevent instantiation

    /**
     * Turn a byte array into a base 64 string
     * @param bytes
     * @return base64String
     */
    public static String toBase64String(byte[] bytes) {
        try {
            return Base64.getEncoder().encodeToString(bytes);
        } catch(Exception exception) {}
        return "" + bytes.length;
    }

    /**
     * Turn a string into a base 64 string
     * @param string
     * @return base64String
     */
    public static String toBase64String(String string) {
        return toBase64String(string.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Get a byte array from base64'd string
     * @param base64
     * @return bytes
     */
    public static byte[] base64StringToByteArray(String base64) {
        try {
            return Base64.getDecoder().decode(base64);
        } catch(Exception exception) {}
        return new byte[0];
    }

    /**
     * Get a string from base64'd string
     * @param base64
     * @return string
     */
    public static String base64ToString(String base64) {
        try {
            return new String(base64StringToByteArray(base64));
        } catch(Exception exception) {}
        return "" + base64.length();
    }

    /**
     * You can use this helper class to turn an inventory into a base64 string
     */
    public static class InventorySerializer {
        /**
         * Serialize an Inventory
         * @param inventory
         * @param fallbackInventory
         * @return base64String
         */
        public static String serialize(Inventory inventory, InventoryType fallbackInventory) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream objectStream = new BukkitObjectOutputStream(outputStream);
                objectStream.writeObject(inventory.getType());
                for (int i = 0; i < inventory.getSize(); i++) {
                    objectStream.writeObject(inventory.getItem(i));
                }
                objectStream.close();
                return toBase64String(outputStream.toByteArray());
            } catch (Exception ex) {
                try {
                    return InventorySerializer
                            .serialize(Bukkit.getServer().createInventory(null, fallbackInventory), fallbackInventory);
                } catch (Exception exx) {
                    throw new Error("Could not serialize an inventory!", exx);
                }
            }
        }
        /**
         * Get the Inventory back from a serialized inventory
         * @param base64SerializedInventory
         * @param fallbackInventory
         * @return inventory
         */
        public static Inventory deserialize(String base64SerializedInventory, InventoryType fallbackInventory) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(base64StringToByteArray(base64SerializedInventory));
                BukkitObjectInputStream objectStream = new BukkitObjectInputStream(inputStream);
                InventoryType type = (InventoryType) objectStream.readObject();
                Inventory inventory = Bukkit.getServer().createInventory(null, type);
                for (int i = 0; i < inventory.getSize(); i++) {
                    inventory.setItem(i, (ItemStack) objectStream.readObject());
                }
                objectStream.close();
                return inventory;
            } catch (Exception ex) {
                return Bukkit.getServer().createInventory(null, fallbackInventory);
            }
        }

    }

    /**
     * You can use this helper class to turn a Collection of PotionEffects into a base64 string
     */
    public static class PotionEffectsSerializer {
        /**
         * Serialize a Collection of PotionEffects
         * @param playerEffects
         * @return base64String
         */
        public static String serialize(Collection<PotionEffect> playerEffects) {
            try {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                BukkitObjectOutputStream objectStream = new BukkitObjectOutputStream(outputStream);
                objectStream.writeObject(playerEffects);
                objectStream.close();
                return toBase64String(outputStream.toByteArray());
            } catch (Exception ex) {
                throw new Error("Could not serialize a PotionEffect!", ex);
            }
        }
        /**
         * Get the Collection of PotionEffects back from a serialized object
         * @param serializedEffects
         * @return playerEffects
         */
        public static Collection<PotionEffect> deserialize(String serializedEffects) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(base64StringToByteArray(serializedEffects));
                BukkitObjectInputStream objectStream = new BukkitObjectInputStream(inputStream);
                Collection<PotionEffect> playerEffects = (Collection<PotionEffect>) objectStream.readObject();
                objectStream.close();
                return playerEffects;
            } catch (Exception ex) {
                return null;
            }
        }
    }

}
