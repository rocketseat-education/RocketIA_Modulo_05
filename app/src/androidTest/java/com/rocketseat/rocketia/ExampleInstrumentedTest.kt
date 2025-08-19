package com.rocketseat.rocketia

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rocketseat.rocketia.ui.activity.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.manipulation.Ordering

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var appContext: Context

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun useAppContext() {
        assertEquals("com.rocketseat.rocketia", appContext.packageName)
    }

    @Test
    fun readAppNameFromResources() {
        val appName = appContext.getString(R.string.app_name)
        assertEquals("RocketIA", appName)
    }

    @Test
    fun launchMainActivity() {
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            assertNotNull(scenario)
        }
    }

    @Test
    fun writeAndReadSharedPreferences() {
        val prefs = appContext.getSharedPreferences("test_prefs", Context.MODE_PRIVATE)

        prefs.edit().putString("key", "hello").apply()
        val value = prefs.getString("key", null)

        assertEquals("hello", value)
    }
}