package febri.uray.bedboy.core.util

import febri.uray.bedboy.core.util.DateUtil.formatDate
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DateUtilTest {

    @Test
    fun `getYesterdayTimestamp returns correct date`() {
        // Simulasikan waktu sekarang
        val calendar = Calendar.getInstance()
        // calendar.set(2024, Calendar.DECEMBER, 7, 12, 0, 0) // Contoh tanggal: 2024-12-07 12:00:00
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        // Hitung tanggal kemarin
        calendar.add(Calendar.DATE, -1)
        val expectedYesterday = formatter.format(calendar.time)

        // Panggil fungsi
        val actualYesterday = DateUtil.getYesterdayTimestamp()

        // Bandingkan hasil
        assertEquals(
            expectedYesterday.substring(0, 10),
            actualYesterday.substring(0, 10)
        ) // Hanya periksa tanggal
    }

    @Test
    fun `getTodayTimestamp returns correct date`() {
        // Simulasikan waktu sekarang
        val calendar = Calendar.getInstance()
        //calendar.set(2024, Calendar.DECEMBER, 7, 12, 0, 0) // Contoh tanggal: 2024-12-07 12:00:00
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        // Hitung tanggal hari ini
        val expectedToday = formatter.format(calendar.time)

        // Panggil fungsi
        val actualToday = DateUtil.getTodayTimestamp()

        // Bandingkan hasil
        assertEquals(
            expectedToday.substring(0, 10),
            actualToday.substring(0, 10)
        ) // Hanya periksa tanggal
    }

    @Test
    fun `formatDate returns correct formatted date for valid input`() {
        val inputDate = "2024-12-06T15:44:40Z"
        val expectedOutput = "Friday, 06 - December - 2024" // Sesuai Locale.getDefault()

        val actualOutput = formatDate(inputDate)
        assertEquals(expectedOutput, actualOutput)
    }
}