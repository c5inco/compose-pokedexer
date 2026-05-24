package des.c5inco.pokedexer.ui.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ArtworkModelTest {
    @Test
    fun artworkModelUsesBundledWebpWhenAssetExists() {
        val model = artworkModel(id = 3, assets = arrayOf("003.webp"))

        assertEquals("file:///android_asset/wc/003.webp", model)
    }

    @Test
    fun artworkModelUsesExternalPngWhenBundledWebpDoesNotExist() {
        val model = artworkModel(id = 25, assets = emptyArray())

        assertEquals("https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png", model)
    }

    @Test
    fun localArtworkExistsChecksTheExpectedAssetList() {
        val assetList = arrayOf("001.webp", "007.webp")

        assertTrue(localArtworkExists("wc/007.webp", assetList))
        assertFalse(localArtworkExists("wc/025.webp", assetList))
    }
}
