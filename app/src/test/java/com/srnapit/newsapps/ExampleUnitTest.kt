package com.srnapit.newsapps
import com.srnapit.newsapps.data.repository.NewsRepository
import com.srnapit.newsapps.data.model.Article
import com.srnapit.newsapps.data.model.NewsResponse
import com.srnapit.newsapps.ui.viewmodel.NewsUIState
import com.srnapit.newsapps.ui.viewmodel.NewsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    private lateinit var viewModel: NewsViewModel
    private lateinit var repository: NewsRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(NewsRepository::class.java)
        viewModel = NewsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun `when repository returns success, uiState is Success`() = runTest {
        // Arrange
        val articles = listOf(
            Article(
                title = "Test Article",
                description = "Description",
                url = "",
                author = "",
                publishedAt = "",
                content = "",
                urlToImage = null
            )
        )
        val mockResponse = NewsResponse(
            status = "ok", articles = articles,
            totalResults = articles.count()
        )
        `when`(repository.fetchTopHeadlines()).thenReturn(mockResponse)

        // Act
        viewModel.loadNewsHeadlines()
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is NewsUIState.Success)
    }

    @Test
    fun `when repository throws exception, uiState is Error`() = runTest {
        // Arrange
        `when`(repository.fetchTopHeadlines()).thenThrow(RuntimeException("Network error"))

        // Act
        viewModel.loadNewsHeadlines()
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertTrue(state is NewsUIState.Error)
    }
}