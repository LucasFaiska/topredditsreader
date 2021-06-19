package com.lfaiska.topredditsreader

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lfaiska.topredditsreader.presentation.scenes.splash.SplashFragment
import com.lfaiska.topredditsreader.presentation.scenes.splash.SplashFragmentDirections
import com.lfaiska.topredditsreader.presentation.scenes.splash.SplashViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions.assertHasDrawable
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep

@RunWith(AndroidJUnit4::class)
@LargeTest
class SplashTest {

    private lateinit var scenario: FragmentScenario<SplashFragment>
    private val mockNavController = mockk<NavController>()

    @Before
    fun setup() {
        scenario = launchFragmentInContainer()

        every {
            mockNavController.navigate(SplashFragmentDirections.navigateToPosts())
        } returns Unit
    }

    @Test
    fun testSplashSceneShowingCorrectDrawable() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        assertHasDrawable(R.id.splash_image, R.drawable.ic_reddit_logo)
    }

    @Test
    fun testNavigationToPostListSceneAfterDelayTime() {
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        sleep(SplashViewModel.SPLASH_NAVIGATION_DELAY)

        verify {
            mockNavController.navigate(SplashFragmentDirections.navigateToPosts())
        }
    }
}