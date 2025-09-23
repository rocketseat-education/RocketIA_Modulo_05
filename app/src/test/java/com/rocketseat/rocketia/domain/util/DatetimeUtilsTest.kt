package com.rocketseat.rocketia.domain.util

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.util.Locale

class DatetimeUtilsTest {

    @Test
    fun `GIVEN time in millis equals 0 WHEN format datetime is called THEN should return empty string`() {
        // GIVEN
        val dummyDatetimeInMillis = 0L

        // WHEN
        val result = dummyDatetimeInMillis.formatDatetime()

        // THEN
        assertEquals("", result)
    }

    @Test
    fun `GIVEN time in millis bigger than 0 WHEN format datetime is called THEN should return datetime string`() {
        // GIVEN
        val dummyLocale = Locale.forLanguageTag("pt-BR")
        val dummyDatetimeInMillis = 1756295438711L // 27-08-2025 08:50 UTC(GMT -03:00)
        val expectedDatetimeText = "27/08/2025 08:50"

        // WHEN
        val result = dummyDatetimeInMillis.formatDatetime(locale = dummyLocale)

        // THEN
        assertEquals(expectedDatetimeText, result)
    }

    @Test
    fun `GIVEN time in millis equals 0 WHEN format time is called THEN should return empty string`() {
        // GIVEN
        val dummyDatetimeInMillis = 0L

        // WHEN
        val result = dummyDatetimeInMillis.formatTime()

        // THEN
        assertEquals("", result)
    }

    @Test
    fun `GIVEN time in millis bigger than 0 WHEN format time is called THEN should return time string`() {
        // GIVEN
        val dummyLocale = Locale.forLanguageTag("pt-BR")
        val dummyDatetimeInMillis = 1756295438711L // 27-08-2025 08:50 UTC(GMT -03:00)
        val expectedTimeText = "08:50"

        // WHEN
        val result = dummyDatetimeInMillis.formatTime(locale = dummyLocale)

        // THEN
        assertEquals(expectedTimeText, result)
    }

}