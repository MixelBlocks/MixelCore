package de.mixelblocks.core.economy;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface EconomyClanData {

    /**
     * Add money (bank account) to clan economy data
     * @param amount
     * @param description ( The reason in dashboard displayed as reason for transaction )
     * @return success
     */
    boolean addBank(long amount, String description);

    /**
     * Remove money (bank account) to clan economy data
     * @param amount
     * @param description ( The reason in dashboard displayed as reason for transaction )
     * @return success
     */
    boolean removeBank(long amount, String description);

    /**
     * Set money (bank account) of clan economy data
     * @param amount
     * @param description ( The reason in dashboard displayed as reason for set things )
     * @return success
     */
    boolean setBank(long amount, String description);

    /**
     * resolve the available amount of clans (bank account)
     * @return amount
     */
    long getBank();

    /**
     * Add money to ManagedType ( bank or cash )
     * @param type
     * @param amount
     * @param reason
     * @return success
     */
    default boolean add(ManagedType type, long amount, String reason) {
        boolean success = false;
        switch(type) {
            case BANK:
                try {
                    return this.addBank(amount, reason);
                } catch(Exception e) { success = false; break;}
        }
        return success;
    }

    /**
     * Remove money from ManagedType ( bank or cash )
     * @param type
     * @param amount
     * @param reason
     * @return success
     */
    default boolean rm(ManagedType type, long amount, String reason) {
        switch(type) {
            case BANK:
                try {
                    return this.removeBank(amount, reason);
                } catch(Exception e) { break;}
        }
        return false;
    }

    /**
     * Set money of ManagedType ( bank only at the moment )
     * @param type
     * @param amount
     * @param reason
     * @return success
     */
    default boolean set(ManagedType type, long amount, String reason) {
        switch(type) {
            case BANK:
                try {
                    return this.setBank(amount, reason);
                } catch(Exception e) { break;}
        }
        return false;
    }

    /**
     * The type of account
     */
    enum ManagedType {
        /**
         * Use BANK for the bank account as type
         */
        BANK;
    }

}
