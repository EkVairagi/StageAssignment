package com.hello.stageassignment.data.local.converters

import androidx.room.TypeConverter
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConvertersTest {

    private val converters = Converters()

    @Test
    fun fromStringList_convertsListToString() {
        val list = listOf("one", "two", "three")
        val result = converters.fromStringList(list)
        assertEquals("one,two,three", result)
    }

    @Test
    fun toStringList_convertsStringToList() {
        val str = "one,two,three"
        val result = converters.toStringList(str)
        assertEquals(listOf("one", "two", "three"), result)
    }
}
