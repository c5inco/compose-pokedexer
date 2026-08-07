package des.c5inco.pokedexer.ui.common

import androidx.compose.ui.graphics.BlendMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

class MeshGradientTest {
    @Test
    fun optionsDefaultsPreserveExistingMeshBehavior() {
        val options = MeshGradientOptions()

        assertEquals(BlendMode.DstIn, options.blendMode)
        assertFalse(options.showPoints)
        assertEquals(listOf(0, 1, 2), options.modifyIndices(listOf(0, 1, 2)))
    }

    @Test
    fun triangleIndicesPreserveExistingVertexOrder() {
        assertEquals(
            "0,3,4,0,1,4,1,4,5,1,2,5".toIntList(),
            buildTriangleIndices(columns = 3, rows = 2),
        )
    }

    @Test
    fun triangleIndicesPreserveRowMajorBlockOrder() {
        assertEquals(
            "0,3,4,0,1,4,1,4,5,1,2,5,3,6,7,3,4,7,4,7,8,4,5,8".toIntList(),
            buildTriangleIndices(columns = 3, rows = 3),
        )
    }

    private fun String.toIntList(): List<Int> = split(',').map(String::toInt)
}
