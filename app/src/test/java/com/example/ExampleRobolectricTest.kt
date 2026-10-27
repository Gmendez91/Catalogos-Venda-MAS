package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.BrandTokens
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals(BrandTokens.BRAND_NAME_PRIMARY, appName)
    }

    @Test
    fun `verify brand constants`() {
        assertEquals("Vende Más", BrandTokens.BRAND_NAME_PRIMARY)
        assertEquals("V+C", BrandTokens.LOGO_SYMBOL)
        assertEquals("Tus productos, mejor presentados", BrandTokens.BRAND_SLOGAN)
    }
}
