package org.troikas.main

import org.troikas.main.utils.StringParser
import kotlin.test.Test
import kotlin.test.assertEquals

class StringParserTest {
    private val parser = StringParser()

    @Test
    fun testDietCoke() {
        val input = "CARBONATED WATER, ACIDITY REGULATOR (338), SWEETENERS (951,950), " +
                "PRESERVATIVE (211), CAFFEINE. CONTAINS PERMITTED NATURAL COLOUR (150d) " +
                "AND ADDED FLAVOURS (NATURAL FLAVOURING SUBSTANCÉS)."

        val expected = listOf(
            "carbonated water",
            "e338",
            "e951",
            "e950",
            "e211",
            "caffeine",
            "e150d",
            "added flavours"
        )

        val result = parser.execute(input)
        assertEquals(expected, result, "Carbonated water ingredient parsing failed")
    }

    @Test
    fun testBhuija() {
        val input = "Dew Bean Flour (Moth Flour) (45%), Edible Vegetable Oil " +
                "(Palmolein Oil, Cottonseed Oil), Gram Flour (9%), Ground Spices and " +
                "Condiments (Iodised Salt, Red Chilli, Ginger, Black Pepper, Cardamom, " +
                "Clove, Nutmeg)"

        val expected = listOf(
            "dew bean flour",
            "palmolein oil",
            "cottonseed oil",
            "gram flour",
            "iodised salt",
            "red chilli",
            "ginger",
            "black pepper",
            "cardamom",
            "clove",
            "nutmeg"
        )

        val result = parser.execute(input)
        assertEquals(expected, result, "Dew bean flour ingredient parsing failed")
    }
}
