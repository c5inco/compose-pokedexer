package des.c5inco.pokedexer

import des.c5inco.pokedexer.data.cleanupDescriptionText
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UtilTest {
        @Test
        fun `cleanupDescriptionText replaces newline characters with spaces`() {
            val input = "This is a\nmultiline string."
            val expected = "This is a multiline string."
            val actual = cleanupDescriptionText(input)
            assertEquals(expected, actual)
        }

        @Test
        fun `cleanupDescriptionText replaces form feed characters with spaces`() {
            val input = "This string has a\u000cform feed."
            val expected = "This string has a form feed."
            val actual = cleanupDescriptionText(input)
            assertEquals(expected, actual)
        }

        @Test
        fun `cleanupDescriptionText replaces POKéMON with pokemon`() {
            val input = "This is a POKéMON."
            val expected = "This is a pokemon."
            val actual = cleanupDescriptionText(input)
            assertEquals(expected, actual)
        }

        @Test
        fun `cleanupDescriptionText handles multiple replacements`() {
            val input = "This is a\nPOKéMON with\u000ca form feed."
            val expected = "This is a pokemon with a form feed."
            val actual = cleanupDescriptionText(input)
            assertEquals(expected, actual)
        }

        @Test
        fun `cleanupDescriptionText handles empty string`() {
            val input = ""
            val expected = ""
            val actual = cleanupDescriptionText(input)
            assertEquals(expected, actual)
        }

        @Test
        fun `cleanupDescriptionText handles string with no special characters`() {
            val input = "This is a normal string."
            val expected = "This is a normal string."
            val actual = cleanupDescriptionText(input)
            assertEquals(expected, actual)
        }
}