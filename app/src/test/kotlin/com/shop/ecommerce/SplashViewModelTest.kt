package com.shop.ecommerce

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.shop.ecommerce.ui.splash.SplashViewContract
import com.shop.ecommerce.ui.splash.SplashViewModel
import io.mockk.coEvery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SplashViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SplashViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialize should send Effect Completed after 4000 ms`() = runTest {
        val effectJob = launch {
            val effect = viewModel.effect.first()
            assertEquals(SplashViewContract.Effect.Completed, effect)
        }

        testDispatcher.scheduler.advanceTimeBy(4000)
        effectJob.join()
    }
}
