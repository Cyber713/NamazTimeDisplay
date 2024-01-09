package display.namoz.vaqtlari

import android.content.Context
import android.content.SharedPreferences
import java.util.prefs.Preferences

class DataClass(val context: Context) {
    companion object {

    }

    val FAJR_MINUTE_OF_DAY = "FAJR_MINUTE_OF_DAY"
    val SUNRISE_MINUTE_OF_DAY = "SUNRISE_MINUTE_OF_DAY"
    val ZUHR_MINUTE_OF_DAY = "ZUHR_MINUTE_OF_DAY"
    val ASR_MINUTE_OF_DAY = "ASR_MINUTE_OF_DAY"
    val MAGRIB_MINUTE_OF_DAY = "MAGRIB_MINUTE_OF_DAY"
    val ISHA_MINUTE_OF_DAY = "ISHA_MINUTE_OF_DAY"

    val NAMAZ_TIMES_DATABASE = "NAMAZ_TIMES_DATABASE"

    val NAMAZ_TIMES_COLORS = "NAMAZ_TIMES_COLORS"

    val NAMAZ_TITLES_COLORS = "NAMAZ_TITLES_COLORS"

    val FONT_SIZE = "FONT_SIZE"

    val NAME_FONT_SIZE = "NAME_FONT_SIZE"


    val DATE = "DATE"
    val TIME = "TIME"

    fun setTime(key: String, minuteOfDay: Int) {
        val sharedPref = context.getSharedPreferences(NAMAZ_TIMES_DATABASE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt(key, minuteOfDay)
        editor.apply()
    }

    fun getTime(key: String): Int {
        val sharedPref = context.getSharedPreferences(NAMAZ_TIMES_DATABASE, Context.MODE_PRIVATE)
        return sharedPref.getInt(key, 0)
    }

    fun setColor(key: String, colorHex: String) {
        val sharedPref = context.getSharedPreferences(NAMAZ_TIMES_COLORS, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, colorHex)
        editor.apply()
    }

    fun getColor(key: String): String {
        val sharedPref = context.getSharedPreferences(NAMAZ_TIMES_COLORS, Context.MODE_PRIVATE)
        return sharedPref.getString(key, "#FFFFFF")!!
    }

    fun setTitleColor(key: String, colorHex: String) {
        val sharedPref = context.getSharedPreferences(NAMAZ_TITLES_COLORS, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, colorHex)
        editor.apply()
    }

    fun getTitleColor(key: String): String {
        val sharedPref = context.getSharedPreferences(NAMAZ_TITLES_COLORS, Context.MODE_PRIVATE)
        return sharedPref.getString(key, "#FFFFFF")!!
    }

    fun getFontSize(): Int {
        val sharedPref = context.getSharedPreferences(FONT_SIZE, Context.MODE_PRIVATE)
        return sharedPref.getInt(FONT_SIZE, 35)
    }

    fun increaseFont() {
        val sharedPref = context.getSharedPreferences(FONT_SIZE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (getFontSize()+1 <=200){
        editor.putInt(FONT_SIZE, getFontSize()+1)
        editor.apply()
    }}
    fun decreaseFont() {
        val sharedPref = context.getSharedPreferences(FONT_SIZE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (getFontSize()-1 >=30){
        editor.putInt(FONT_SIZE, getFontSize()-1)
        editor.apply()
    }}

    fun getNameFontSize(): Int {
        val sharedPref = context.getSharedPreferences(NAME_FONT_SIZE, Context.MODE_PRIVATE)
        return sharedPref.getInt(NAME_FONT_SIZE, 35)
    }

    fun increaseFontOfName() {
        val sharedPref = context.getSharedPreferences(NAME_FONT_SIZE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (getNameFontSize()+1 <=200){
        editor.putInt(NAME_FONT_SIZE, getNameFontSize()+1)
        editor.apply()
    }}
    fun decreaseFontOfName() {
        val sharedPref = context.getSharedPreferences(NAME_FONT_SIZE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        if (getNameFontSize()-1 >=30){
        editor.putInt(NAME_FONT_SIZE, getNameFontSize()-1)
        editor.apply()
    }}

}