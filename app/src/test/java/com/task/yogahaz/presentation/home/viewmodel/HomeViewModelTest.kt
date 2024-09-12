package com.task.yogahaz.presentation.home.viewmodel

import CategoriesResponseDto
import CategoryDataDto
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.task.yogahaz.data.dto.home.response.PopularSellersResponseDto
import com.task.yogahaz.data.dto.home.response.RestaurantDto
import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse
import com.task.yogahaz.domain.usecases.favorite.AddToFavoriteUseCase
import com.task.yogahaz.domain.usecases.home.GetCategoriesUseCase
import com.task.yogahaz.domain.usecases.home.GetPopularUseCase
import com.task.yogahaz.domain.usecases.home.GetTrendingUseCase
import com.task.yogahaz.fakerepository.FakeHomeRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var fakeHomeRepository: FakeHomeRepository
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var restaurantList :List<RestaurantDto>
    private lateinit var categoriesList :List<CategoryDataDto>

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        fakeHomeRepository = FakeHomeRepository()
        restaurantList = listOf(RestaurantDto(
            id = 1,
            name = "test",
            logo = "test.png",
            rate = "5",
            isFavorite = true ,
            distance = "10 km",
            token = "token") ,
            RestaurantDto(
                id = 2,
                name = "test2",
                logo = "test2.png",
                rate = "2",
                isFavorite = false ,
                distance = "2 km",
                token = "token2")
        )
        categoriesList = listOf(
            CategoryDataDto(
                id = 1,
                name = "testCategory",
                active = 1,
                nameAr = "تيست",
                nameEn = "test",
                image = "image.png"
            )
        )
        viewModel = HomeViewModel(
            getCategoriesUseCase = GetCategoriesUseCase(fakeHomeRepository),
            getTrendingUseCase = GetTrendingUseCase(fakeHomeRepository),
            getPopularSellerUseCase = GetPopularUseCase(fakeHomeRepository),
            addToFavoriteUseCase = AddToFavoriteUseCase(fakeHomeRepository)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addToFavorite returns success`() = runTest {
        // Given
        val fakeResponse = AddToFavoriteResponse(success = true,
            message = "Added to favorites", responseCode = 200)
        fakeHomeRepository.setFakeAddToFavoriteResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.addToFavorite(AddToFavoriteBody(userId = 1))

        // Then
        val initState = viewModel.addToFavoriteState.first()
        assertEquals(null, initState.addToFavoriteResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.addToFavoriteState.first()  // after the response returned the response should be success
        assertEquals(true, state.addToFavoriteResponse?.success)
        assertEquals("Added to favorites", state.addToFavoriteResponse?.message)
        assertEquals(200, state.addToFavoriteResponse?.responseCode)
        assertEquals("", state.error)


    }

    @Test
    fun `addToFavorite returns error`() = runTest {
        // Given
        fakeHomeRepository.setShouldReturnError(true)
        val fakeResponse = AddToFavoriteResponse(success = true,
            message = "Added to favorites", responseCode = 200)
        fakeHomeRepository.setFakeAddToFavoriteResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.addToFavorite(AddToFavoriteBody(userId = 1))

        // Then
        val initState = viewModel.addToFavoriteState.first()
        assertEquals(null, initState.addToFavoriteResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.addToFavoriteState.first()  // after the response returned the response should be success
        assertEquals(null, state.addToFavoriteResponse)
        assertEquals("Couldn't reach server. Check your internet connection.", state.error) // the exceptions message at the fake repo


    }

    @Test
    fun `getPopularSellers returns success`() = runTest {
        // Given
        val fakeResponse = PopularSellersResponseDto(success = true,
            message = "success",
            responseCode = 200,
            data = restaurantList)
        fakeHomeRepository.setFakePopularSellersResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.getPopularSellers()

        // Then
        val initState = viewModel.getPopularSellerState.first()
        assertEquals(null, initState.popularResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.getPopularSellerState.first()  // after the response returned the response should be success
        assertEquals(true, state.popularResponse?.success)
        assertEquals("success", state.popularResponse?.message)
        assertEquals(200, state.popularResponse?.responseCode)
        assertEquals("", state.error)
        assertEquals(2, state.popularResponse?.data?.size) // as there are two items in the restaurant list
        assertEquals(true, state.popularResponse?.data?.get(0)?.isFavorite )


    }

    @Test
    fun `getPopularSellers returns error`() = runTest {
        // Given
        fakeHomeRepository.setShouldReturnError(true)
        val fakeResponse = PopularSellersResponseDto(success = true,
            message = "success",
            responseCode = 200,
            data = restaurantList)
        fakeHomeRepository.setFakePopularSellersResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.getPopularSellers()

        // Then
        val initState = viewModel.getPopularSellerState.first()
        assertEquals(null, initState.popularResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.getPopularSellerState.first()  // after the response returned the response should be success
        assertEquals(null, state.popularResponse)
        assertEquals("Couldn't reach server. Check your internet connection.", state.error) // the exceptions message at the fake repo

    }




    @Test
    fun `getCategories returns success`() = runTest {
        // Given
        val fakeResponse = CategoriesResponseDto(
            success = true,
            message = "success",
            responseCode = 200,
            data = categoriesList
        )
        fakeHomeRepository.setFakeCategoriesResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.getCategories()

        // Then
        val initState = viewModel.getCategoriesState.first()
        assertEquals(null, initState.categoriesResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.getCategoriesState.first()  // after the response returned the response should be success
        assertEquals(true, state.categoriesResponse?.success)
        assertEquals("success", state.categoriesResponse?.message)
        assertEquals(200, state.categoriesResponse?.responseCode)
        assertEquals("", state.error)
        assertEquals(1, state.categoriesResponse?.data?.size) // as there are two items in the restaurant list
        assertEquals("testCategory", state.categoriesResponse?.data?.get(0)?.name )


    }

    @Test
    fun `getCategories returns error`() = runTest {
        // Given
        fakeHomeRepository.setShouldReturnError(true)
        val fakeResponse = CategoriesResponseDto(
            success = true,
            message = "success",
            responseCode = 200,
            data = categoriesList
        )
        fakeHomeRepository.setFakeCategoriesResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.getCategories()

        // Then
        val initState = viewModel.getCategoriesState.first()
        assertEquals(null, initState.categoriesResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.getCategoriesState.first()  // after the response returned the response should be success
        assertEquals(null, state.categoriesResponse)
        assertEquals("Couldn't reach server. Check your internet connection.", state.error) // the exceptions message at the fake repo

    }


    @Test
    fun `getTrendingSellers returns success`() = runTest {
        // Given
        val fakeResponse = PopularSellersResponseDto(success = true,
            message = "success",
            responseCode = 200,
            data = restaurantList)
        fakeHomeRepository.setFakeTrendingSellersResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.getTrendingSellers()

        // Then
        val initState = viewModel.getTrendingState.first()
        assertEquals(null, initState.trendingResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.getTrendingState.first()  // after the response returned the response should be success
        assertEquals(true, state.trendingResponse?.success)
        assertEquals("success", state.trendingResponse?.message)
        assertEquals(200, state.trendingResponse?.responseCode)
        assertEquals(2, state.trendingResponse?.data?.size) // as there are two items in the restaurant list
        assertEquals(false, state.trendingResponse?.data?.get(1)?.isFavorite )
        assertEquals("test2", state.trendingResponse?.data?.get(1)?.name ) // the name of the second item restaurant


    }

    @Test
    fun `getTrendingSellers returns error`() = runTest {
        // Given
        fakeHomeRepository.setShouldReturnError(true)
        val fakeResponse = PopularSellersResponseDto(success = true,
            message = "success",
            responseCode = 200,
            data = restaurantList)
        fakeHomeRepository.setFakeTrendingSellersResponse(fakeResponse)// adding fake data to the repository

        // When
        viewModel.getTrendingSellers()

        // Then
        val initState = viewModel.getTrendingState.first()
        assertEquals(null, initState.trendingResponse) // the initial state should be null and loading should be false
        assertEquals(false, initState.isLoading)

        // When
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.getTrendingState.first()  // after the response returned the response should be success
        assertEquals(null, state.trendingResponse)
        assertEquals("Couldn't reach server. Check your internet connection.", state.error) // the exceptions message at the fake repo


    }
}
