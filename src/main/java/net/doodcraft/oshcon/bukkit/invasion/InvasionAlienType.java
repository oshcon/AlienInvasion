package net.doodcraft.oshcon.bukkit.invasion;

public enum InvasionAlienType {

    NONALIEN,
    NORMAL_MELEE, // Zombie
    NORMAL_RANGED, // Skeleton
    EXPLODER; // Creeper

    public static InvasionAlienType getAlienType(String string) {

        if (isAlienType(string)) {
            return InvasionAlienType.valueOf(string.toUpperCase());
        }

        return NONALIEN;
    }

    public static Boolean isAlienType(String string) {

        for (InvasionAlienType value : InvasionAlienType.values()) {
            if (value.toString().equalsIgnoreCase(string)) {
                return true;
            }
        }

        return false;
    }

    public static String getName(InvasionAlienType alienType) {

        if (!isAlienType(alienType.toString()) || alienType.equals(InvasionAlienType.NONALIEN)) {
            return "Non Alien";
        }

        if (alienType.equals(InvasionAlienType.NORMAL_MELEE)) {
            return "Normal Melee";
        }

        if (alienType.equals(InvasionAlienType.NORMAL_RANGED)) {
            return "Normal Ranged";
        }

        if (alienType.equals(InvasionAlienType.EXPLODER)) {
            return "Exploder";
        }

        return "Non Alien";
    }
}