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

    public static double getDamageScale(InvasionClass invasionClass) {

        if (!isInvasionClass(invasionClass.toString())) {
            return 1.00;
        }

        if (invasionClass.equals(InvasionClass.CIVILIAN)) {
            return 2.00;
        }

        if (invasionClass.equals(InvasionClass.ENGINEER)) {
            return 1.40;
        }

        if (invasionClass.equals(InvasionClass.SENTRY)) {
            return 0.75;
        }

        if (invasionClass.equals(InvasionClass.SURVIVALIST)) {
            return 1.35;
        }

        if (invasionClass.equals(InvasionClass.SCAVENGER)) {
            return 1.20;
        }

        if (invasionClass.equals(InvasionClass.MEDIC)) {
            return 1.00;
        }

        return 1.00;
    }

    public static double getDamageDealScale(InvasionClass invasionClass) {

        if (!isInvasionClass(invasionClass.toString())) {
            return 1.00;
        }

        if (invasionClass.equals(InvasionClass.CIVILIAN)) {
            return 0.50;
        }

        if (invasionClass.equals(InvasionClass.ENGINEER)) {
            return 0.90;
        }

        if (invasionClass.equals(InvasionClass.SENTRY)) {
            return 1.55;
        }

        if (invasionClass.equals(InvasionClass.SURVIVALIST)) {
            return 1.2;
        }

        if (invasionClass.equals(InvasionClass.SCAVENGER)) {
            return 1.1;
        }

        if (invasionClass.equals(InvasionClass.MEDIC)) {
            return 0.85;
        }

        return 1.00;
    }

    public static float getWalkSpeedScale(InvasionClass invasionClass) {

        if (!isInvasionClass(invasionClass.toString())) {
            return 0.2F;
        }

        if (invasionClass.equals(InvasionClass.CIVILIAN)) {
            return 0.20F;
        }

        if (invasionClass.equals(InvasionClass.ENGINEER)) {
            return 0.18F;
        }

        if (invasionClass.equals(InvasionClass.SENTRY)) {
            return 0.17F;
        }

        if (invasionClass.equals(InvasionClass.SURVIVALIST)) {
            return 0.28F;
        }

        if (invasionClass.equals(InvasionClass.SCAVENGER)) {
            return 0.33F;
        }

        if (invasionClass.equals(InvasionClass.MEDIC)) {
            return 0.20F;
        }

        return 0.20F;
    }
}