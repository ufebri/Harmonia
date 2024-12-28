package febri.uray.bedboy.core.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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

    fun formatDate(
        dateString: String?,
        inputPattern: String = "yyyy-MM-dd'T'HH:mm:ss'Z'",
        outputPattern: String = "EEEE, dd - MMMM - yyyy"
    ): String? {
        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

        val date = inputFormat.parse(dateString ?: getTodayTimestamp()) // Menghasilkan Date?
        return date?.let { outputFormat.format(it) } // Null-safe untuk format
    }
}