package febri.uray.bedboy.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {
    fun getYesterdayTimestamp(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -1) // Mengurangi 1 hari dari tanggal sekarang
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    fun getTodayTimestamp(): String {
        val calendar = Calendar.getInstance() // Mendapatkan waktu sekarang
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}