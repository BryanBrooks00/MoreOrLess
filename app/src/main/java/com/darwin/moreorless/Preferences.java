package com.darwin.moreorless;
import android.content.Context;
import android.preference.PreferenceManager;

public class Preferences {

    private static final String PREF_RECORD = "lastRecord";
    private static final String PREF_MONEY = "lastMoney";
    private static final String PREF_OLD_RECORD = "oldRecord";
    private static final String PREF_OLD_MONEY = "oldMoney";
    private static final String PREF_IS_BOUGHT = "isBought";
    private static final String PREF_COEFFICIENT = "coefficient";
    private static final String PREF_BONUS = "bonus";

    public static String getRecord(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_RECORD, "0");
    }

    public static void setRecord(Context context, String lastResult) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_RECORD, lastResult)
                .apply();
    }

    public static String getMoney(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_MONEY, "0");
    }

    public static void setMoney(Context context, String lastMoney) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_MONEY, lastMoney)
                .apply();
    }

    public static String getOldRecord(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_OLD_RECORD, "0");
    }

    public static void setOldRecord(Context context, String oldResult) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_OLD_RECORD, oldResult)
                .apply();
    }

    public static String getOldMoney(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_OLD_MONEY, "0");

    }
    public static void setOldMoney(Context context, String oldMoney) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_OLD_MONEY, oldMoney)
                .apply();
    }

    public static String getIsBought(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_IS_BOUGHT, "no");
    }

    public static void setIsBought(Context context, String isBought) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_IS_BOUGHT, isBought)
                .apply();
    }

    public static String getCoefficient(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_COEFFICIENT, "5");
    }

    public static void setCoefficient(Context context, String coefficient) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_COEFFICIENT, coefficient)
                .apply();
    }

    public static String getBonus(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_BONUS, "no");
    }

    public static void setBonus(Context context, String bonus) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_BONUS, bonus)
                .apply();
    }

    public static void clearPrefs(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .clear()
                .apply();
    }
}