package display.namoz.vaqtlari

import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.azan.Azan
import com.azan.Method
import com.azan.Time
import com.azan.astrologicalCalc.Location
import com.azan.astrologicalCalc.SimpleDate
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import display.namoz.vaqtlari.databinding.ActivityMainBinding
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var timeTextView: TextView
    private lateinit var dateTextView: TextView
    lateinit var prayerTime: PrayerTimeModel
    var isColonVisible: Boolean = false
    lateinit var root: ActivityMainBinding
    lateinit var dataStorage: DataClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root = ActivityMainBinding.inflate(layoutInflater)
        setContentView(root.root)
        dataStorage = DataClass(this)
        getTimes()
        isColonVisible = true
        loadColors()
//        testPrint()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        loadFonts()

        root.increaseBtn.setOnClickListener {
            dataStorage.increaseFont()
            loadFonts()

        }

        root.decreaseBtn.setOnClickListener {
            dataStorage.decreaseFont()
            loadFonts()
        }


        root.increaseTitleBtn.setOnClickListener {
            dataStorage.increaseFontOfName()
            loadFonts()

        }

        root.decreaseTitleBtn.setOnClickListener {
            dataStorage.decreaseFontOfName()
            loadFonts()
        }

        root.fajrTimeTv.setOnClickListener(
            timeClickedLIstener(
                root.fajrTimeTv,
                dataStorage.FAJR_MINUTE_OF_DAY
            )
        )
        root.sunriseTimeTv.setOnClickListener(
            timeClickedLIstener(
                root.sunriseTimeTv,
                dataStorage.SUNRISE_MINUTE_OF_DAY
            )
        )
        root.zuhrTimeTv.setOnClickListener(
            timeClickedLIstener(
                root.zuhrTimeTv,
                dataStorage.ZUHR_MINUTE_OF_DAY
            )
        )
        root.asrTimeTv.setOnClickListener(
            timeClickedLIstener(
                root.asrTimeTv,
                dataStorage.ASR_MINUTE_OF_DAY
            )
        )
        root.magribTimeTv.setOnClickListener(
            timeClickedLIstener(
                root.magribTimeTv,
                dataStorage.MAGRIB_MINUTE_OF_DAY
            )
        )
        root.ishaTimeTv.setOnClickListener(
            timeClickedLIstener(
                root.ishaTimeTv,
                dataStorage.ISHA_MINUTE_OF_DAY
            )
        )

        root.fajrTitle.setOnClickListener(titleColorPicker(dataStorage.FAJR_MINUTE_OF_DAY))
        root.sunriseTitle.setOnClickListener(titleColorPicker(dataStorage.SUNRISE_MINUTE_OF_DAY))
        root.zuhrTitle.setOnClickListener(titleColorPicker(dataStorage.ZUHR_MINUTE_OF_DAY))
        root.asrTitle.setOnClickListener(titleColorPicker(dataStorage.ASR_MINUTE_OF_DAY))
        root.magribTitle.setOnClickListener(titleColorPicker(dataStorage.MAGRIB_MINUTE_OF_DAY))
        root.ishaTitle.setOnClickListener(titleColorPicker(dataStorage.ISHA_MINUTE_OF_DAY))

        root.dateTxt.setOnClickListener(titleColorPicker(dataStorage.DATE))
        root.hourText.setOnClickListener(titleColorPicker(dataStorage.TIME))
        root.conomText.setOnClickListener(titleColorPicker(dataStorage.TIME))
        root.minuteText.setOnClickListener(titleColorPicker(dataStorage.TIME))


        dateTextView = findViewById(R.id.date_txt)

        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                updateTime()
                handler.postDelayed(this, 1000L)
            }
        }

        handler.postDelayed(runnable, calculateInitialDelay())


    }

    private fun getColors(): Array<String> {
        return arrayOf(
            "#FF0000",
            "#E37373",
            "#EF6291",
            "#B968C7",
            "#9475CC",
            "#7985CA",
            "#0037FF",
            "#65B4F5",
            "#50C1F6",
            "#4FCFE0",
            "#4DB5AB",
            "#80C683",
            "#ADD480",
            "#DBE673",
            "#FEF175",
            "#FED44F",
            "#FEB64E",
            "#FF8965",
            "#A0877F",
            "#DFDFDF",
            "#8FA3AD"
        )
    }

    private fun loadFonts() {
        root.fajrTimeTv.textSize = dataStorage.getFontSize().toFloat()
        root.sunriseTimeTv.textSize = dataStorage.getFontSize().toFloat()
        root.zuhrTimeTv.textSize = dataStorage.getFontSize().toFloat()
        root.asrTimeTv.textSize = dataStorage.getFontSize().toFloat()
        root.magribTimeTv.textSize = dataStorage.getFontSize().toFloat()
        root.ishaTimeTv.textSize = dataStorage.getFontSize().toFloat()

        root.fajrTitle.textSize = dataStorage.getNameFontSize().toFloat()
        root.sunriseTitle.textSize = dataStorage.getNameFontSize().toFloat()
        root.zuhrTitle.textSize = dataStorage.getNameFontSize().toFloat()
        root.asrTitle.textSize = dataStorage.getNameFontSize().toFloat()
        root.magribTitle.textSize = dataStorage.getNameFontSize().toFloat()
        root.ishaTitle.textSize = dataStorage.getNameFontSize().toFloat()

        root.hourText.textSize = dataStorage.getNameFontSize().toFloat() + 5
        root.minuteText.textSize = dataStorage.getNameFontSize().toFloat() + 5
        root.conomText.textSize = dataStorage.getNameFontSize().toFloat() + 5
        root.dateTxt.textSize = dataStorage.getNameFontSize().toFloat() + 5
    }

    private fun loadColors() {
        val d = dataStorage
        root.fajrTitle.setTextColor(Color.parseColor(d.getTitleColor(d.FAJR_MINUTE_OF_DAY)))
        root.sunriseTitle.setTextColor(Color.parseColor(d.getTitleColor(d.SUNRISE_MINUTE_OF_DAY)))
        root.zuhrTitle.setTextColor(Color.parseColor(d.getTitleColor(d.ZUHR_MINUTE_OF_DAY)))
        root.asrTitle.setTextColor(Color.parseColor(d.getTitleColor(d.ASR_MINUTE_OF_DAY)))
        root.magribTitle.setTextColor(Color.parseColor(d.getTitleColor(d.MAGRIB_MINUTE_OF_DAY)))
        root.ishaTitle.setTextColor(Color.parseColor(d.getTitleColor(d.ISHA_MINUTE_OF_DAY)))

        root.dateTxt.setTextColor(Color.parseColor(d.getTitleColor(d.DATE)))
        root.hourText.setTextColor(Color.parseColor(d.getTitleColor(d.TIME)))
        root.conomText.setTextColor(Color.parseColor(d.getTitleColor(d.TIME)))
        root.minuteText.setTextColor(Color.parseColor(d.getTitleColor(d.TIME)))
//
    }

    fun titleColorPicker(timeKey: String): View.OnClickListener {
        val listener = View.OnClickListener {

            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Rang Tanlang")
                .setColors(getColors())
                .setColorShape(ColorShape.CIRCLE)
                .setColorSwatch(ColorSwatch._300)
                .setDefaultColor(dataStorage.getTitleColor(timeKey))
                .setColorListener { color, colorHex ->
                    dataStorage.setTitleColor(timeKey, colorHex)
                    loadColors()
                }

                .show()

        }
        return listener
    }

    private fun timeClickedLIstener(timeTv: TextView, timeKey: String): View.OnClickListener? {
        val listener = View.OnClickListener {
            val d = Dialog(this)
            d.setContentView(R.layout.selector_dialog)
            d.findViewById<ImageView>(R.id.edit_time_id).setOnClickListener {
                val timePickerDialog = TimePickerDialog(
                    this,
                    setTimeOfPrayer(timeKey),
                    dataStorage.getTime(dataStorage.FAJR_MINUTE_OF_DAY) / 60,
                    dataStorage.getTime(dataStorage.FAJR_MINUTE_OF_DAY) % 60,
                    true
                )
                timePickerDialog.show()
                d.dismiss()
            }
            d.findViewById<ImageView>(R.id.edit_color_id).setOnClickListener {

                MaterialColorPickerDialog
                    .Builder(this)
                    .setTitle("Rang Tanlang")
                    .setColors(getColors())
                    .setColorShape(ColorShape.CIRCLE)
                    .setColorSwatch(ColorSwatch._300)
                    .setDefaultColor(dataStorage.getColor(timeKey))
                    .setColorListener { color, colorHex ->
                        dataStorage.setColor(timeKey, colorHex)
                    }
                    .show()
                d.dismiss()

            }

            d.show()


        }

        return listener
    }

    private fun setTimeOfPrayer(prayer_moment_name: String): TimePickerDialog.OnTimeSetListener? {

        val tP = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dataStorage.setTime(prayer_moment_name, hourOfDay * 60 + minute)

        }

        return tP
    }

    private fun testPrint() {
        val today = SimpleDate(GregorianCalendar())
        val location = Location(41.0344646, 71.8629413, 5.0, 0)
        val azan = Azan(location, Method.KARACHI_HANAF)
        val prayerTimes = azan.getPrayerTimes(today)
        val imsaak = azan.getImsaak(today)
        Log.d("", "----------------results------------------------")
        Log.d("", ("date ---> " + today.day + " / " + today.month + " / " + today.year))
        Log.d("imsaak --->", "$imsaak")
        Log.d("", "Fajr ---> " + prayerTimes.fajr())
        Log.d("sunrise --->", prayerTimes.shuruq().toString())
        Log.d("Zuhr --->", prayerTimes.thuhr().toString())
        Log.d("Asr --->", prayerTimes.assr().toString())
        Log.d("Maghrib --->", prayerTimes.maghrib().toString())
        Log.d("ISHA  --->", prayerTimes.ishaa().toString())
        Log.d("", "----------------------------------------")
    }

    fun getTimes() {
        val d = dataStorage

        val fajrInt = d.getTime(d.FAJR_MINUTE_OF_DAY)
        val sunriseInt = d.getTime(d.SUNRISE_MINUTE_OF_DAY)
        val zuhrInt = d.getTime(d.ZUHR_MINUTE_OF_DAY)
        val asrInt = d.getTime(d.ASR_MINUTE_OF_DAY)
        val magribInt = d.getTime(d.MAGRIB_MINUTE_OF_DAY)
        val ishaInt = d.getTime(d.ISHA_MINUTE_OF_DAY)

        prayerTime = PrayerTimeModel(fajrInt, sunriseInt, zuhrInt, asrInt, magribInt, ishaInt)
        refreshMonitorTimes()
    }


    private fun getCurrentTime(): Int {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)
        return currentHour * 60 + currentMinute
    }

    private fun refreshMonitorTimes() {
        val t = prayerTime

        val farjStr = Time(t.fajr / 60, t.fajr % 60, 0, false)
        val sunriseStr = Time(t.sunrise / 60, t.sunrise % 60, 0, false)
        val zuhrStr = Time(t.zuhr / 60, t.zuhr % 60, 0, false)
        val asrStr = Time(t.asr / 60, t.asr % 60, 0, false)
        val magribStr = Time(t.magrib / 60, t.magrib % 60, 0, false)
        val ishaStr = Time(t.isha / 60, t.isha % 60, 0, false)
        val times = arrayOf(
            prayerTime.fajr,
            prayerTime.sunrise,
            prayerTime.zuhr,
            prayerTime.asr,
            prayerTime.magrib,
            prayerTime.isha,
        )

        val currentTime = getCurrentTime()
        var index = 0
        for (i in times.indices) {
            if (times[i] > currentTime) {
                index = i
                break
            }
        }
        index--
        val activePrayerTime = when (index) {
            0 -> farjStr
            1 -> sunriseStr
            2 -> zuhrStr
            3 -> asrStr
            4 -> magribStr
            5 -> ishaStr
            else -> ishaStr
        }

        root.fajrTimeTv.text = farjStr.toString().dropLast(3)
        root.sunriseTimeTv.text = sunriseStr.toString().dropLast(3)
        root.zuhrTimeTv.text = zuhrStr.toString().dropLast(3)
        root.asrTimeTv.text = asrStr.toString().dropLast(3)
        root.magribTimeTv.text = magribStr.toString().dropLast(3)
        root.ishaTimeTv.text = ishaStr.toString().dropLast(3)

        resetTextViewColors()

        when (activePrayerTime) {
            farjStr -> {
                root.fajrTimeTv.setTextColor(Color.RED)
                root.fajrTimeTv.setTypeface(null, Typeface.BOLD)
                root.fajrTitle.setTypeface(null, Typeface.BOLD)

            }
            sunriseStr -> {
                root.sunriseTimeTv.setTextColor(Color.RED)
                root.sunriseTimeTv.setTypeface(null, Typeface.BOLD)
                root.sunriseTitle.setTypeface(null, Typeface.BOLD)
            }
            zuhrStr -> {
                root.zuhrTimeTv.setTextColor(Color.RED)
                root.zuhrTimeTv.setTypeface(null, Typeface.BOLD)
                root.zuhrTitle.setTypeface(null, Typeface.BOLD)

            }
            asrStr -> {
                root.asrTimeTv.setTextColor(Color.RED)
                root.asrTimeTv.setTypeface(null, Typeface.BOLD)
                root.asrTitle.setTypeface(null, Typeface.BOLD)
            }
            magribStr -> {
                root.magribTimeTv.setTextColor(Color.RED)
                root.magribTimeTv.setTypeface(null, Typeface.BOLD)
                root.magribTitle.setTypeface(null, Typeface.BOLD)

            }
            ishaStr -> {
                root.ishaTimeTv.setTextColor(Color.RED)
                root.ishaTitle.setTypeface(null, Typeface.BOLD)

            }
        }
    }

    private fun resetTextViewColors() {
        root.fajrTimeTv.setTextColor(Color.parseColor(dataStorage.getColor(dataStorage.FAJR_MINUTE_OF_DAY)))
        root.fajrTimeTv.setTypeface(null, Typeface.NORMAL)
        root.fajrTitle.setTypeface(null, Typeface.NORMAL)

        root.sunriseTimeTv.setTextColor(Color.parseColor(dataStorage.getColor(dataStorage.SUNRISE_MINUTE_OF_DAY)))
        root.sunriseTimeTv.setTypeface(null, Typeface.NORMAL)
        root.sunriseTitle.setTypeface(null, Typeface.NORMAL)

        root.zuhrTimeTv.setTextColor(Color.parseColor(dataStorage.getColor(dataStorage.ZUHR_MINUTE_OF_DAY)))
        root.zuhrTimeTv.setTypeface(null, Typeface.NORMAL)
        root.zuhrTitle.setTypeface(null, Typeface.NORMAL)

        root.asrTimeTv.setTextColor(Color.parseColor(dataStorage.getColor(dataStorage.ASR_MINUTE_OF_DAY)))
        root.asrTimeTv.setTypeface(null, Typeface.NORMAL)
        root.asrTitle.setTypeface(null, Typeface.NORMAL)

        root.magribTimeTv.setTextColor(Color.parseColor(dataStorage.getColor(dataStorage.MAGRIB_MINUTE_OF_DAY)))
        root.magribTimeTv.setTypeface(null, Typeface.NORMAL)
        root.magribTitle.setTypeface(null, Typeface.NORMAL)

        root.ishaTimeTv.setTextColor(Color.parseColor(dataStorage.getColor(dataStorage.ISHA_MINUTE_OF_DAY)))
        root.ishaTimeTv.setTypeface(null, Typeface.NORMAL)
        root.ishaTitle.setTypeface(null, Typeface.NORMAL)


    }


    private fun calculateInitialDelay(): Long {
        val currentTimeStamp = System.currentTimeMillis()
        return 1000 - currentTimeStamp % 1000
    }

    private fun updateTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        val hourStr = if (hour < 10) "0$hour" else hour.toString()
        val minuteStr = if (minute < 10) "0$minute" else minute.toString()
        val secondStr = if (second < 10) "0$second" else second.toString()

        val dayStr = if (day < 10) "0$day" else day.toString()
        val monthStr = if (month < 10) "0$month" else month.toString()
        val yearStr = year.toString()

    if (isColonVisible) {
        root.conomText.visibility = View.VISIBLE
        } else {
        root.conomText.visibility = View.INVISIBLE

    }
        isColonVisible = !isColonVisible

        val dateStr = "$dayStr.$monthStr.$yearStr"
root.hourText.text = hourStr
root.minuteText.text = minuteStr
        dateTextView.text = "$dateStr"
        getTimes()

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}

data class PrayerTimeModel(
    val fajr: Int,
    val sunrise: Int,
    val zuhr: Int,
    val asr: Int,
    val magrib: Int,
    val isha: Int
)
