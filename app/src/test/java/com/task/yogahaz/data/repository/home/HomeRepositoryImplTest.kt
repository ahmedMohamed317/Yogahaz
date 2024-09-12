import com.task.yogahaz.data.repository.home.HomeRepositoryImpl
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.repository.home.HomeRepository
import com.task.yogahaz.fakedatasource.FakeHomeApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class HomeRepositoryImplTest {

    private lateinit var homeApi: FakeHomeApi
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        homeApi = FakeHomeApi()
        homeRepository = HomeRepositoryImpl(homeApi)
    }

    @Test
    fun `test getPopularSellers returns expected data`() = runBlocking {

        // When
        val actualResponse = homeRepository.getPopularSellers()

        // Then
        assertEquals(200, actualResponse.responseCode)
        assertEquals("success", actualResponse.message)

    }

    @Test
    fun `test getTrendingSellers returns expected data`() = runBlocking {

        // When
        val actualResponse = homeRepository.getTrendingSellers()

        // Then
        assertEquals("success", actualResponse.message)
        assertEquals(200, actualResponse.responseCode)

    }

    @Test
    fun `test getCategories returns expected data`() = runBlocking {

        // When
        val actualResponse = homeRepository.getCategories()

        assertEquals("success", actualResponse.message)
        assertEquals(200, actualResponse.responseCode)
    }

    @Test
    fun `test addToFavorite returns expected data`() = runBlocking {
        // Given
        val requestBody = AddToFavoriteBody(1)

        // When
        val actualResponse = homeRepository.addToFavorite(requestBody)

        // Then
        assertEquals("success", actualResponse.message)
        assertEquals(200, actualResponse.responseCode)    }
}
