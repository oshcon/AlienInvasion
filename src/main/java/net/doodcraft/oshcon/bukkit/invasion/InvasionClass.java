package net.doodcraft.oshcon.bukkit.invasion;

public enum InvasionClass {

    ENGINEER,
    MEDIC,
    CIVILIAN,
    SCAVENGER,
    SENTRY,
    SURVIVALIST;

    public static InvasionClass getInvasionClass(String string) {
        if (isInvasionClass(string)) {
            return InvasionClass.valueOf(string.toUpperCase());
        }

        return CIVILIAN;
    }

    public static Boolean isInvasionClass(String string) {
        for (InvasionClass value : InvasionClass.values()) {
            if (value.toString().equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    public static String getName(InvasionClass invasionClass) {
        if (!isInvasionClass(invasionClass.toString())) {
            return "NULL";
        }

        if (invasionClass.equals(InvasionClass.CIVILIAN)) {
            return "Civilian";
        }

        if (invasionClass.equals(InvasionClass.ENGINEER)) {
            return "Engineer";
        }

        if (invasionClass.equals(InvasionClass.SENTRY)) {
            return "Sentry";
        }

        if (invasionClass.equals(InvasionClass.SURVIVALIST)) {
            return "Survivalist";
        }

        if (invasionClass.equals(InvasionClass.SCAVENGER)) {
            return "Scavenger";
        }

        if (invasionClass.equals(InvasionClass.MEDIC)) {
            return "Medic";
        }

        return "NULL";
    }

    public static String getPrefix(InvasionClass invasionClass) {
        if (!isInvasionClass(invasionClass.toString())) {
            return "NULL";
        }

        if (invasionClass.equals(InvasionClass.CIVILIAN)) {
            return "§8";
        }

        if (invasionClass.equals(InvasionClass.ENGINEER)) {
            return "§6";
        }

        if (invasionClass.equals(InvasionClass.SENTRY)) {
            return "§5";
        }

        if (invasionClass.equals(InvasionClass.SURVIVALIST)) {
            return "§3";
        }

        if (invasionClass.equals(InvasionClass.SCAVENGER)) {
            return "§b";
        }

        if (invasionClass.equals(InvasionClass.MEDIC)) {
            return "§c";
        }

        return "NULL";
    }
}