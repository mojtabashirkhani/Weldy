package com.example.weldy.endToEnd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.weldy.MainActivity
import com.example.weldy.data.remote.model.CatResponse
import com.example.weldy.domain.model.Cat
import com.example.weldy.screen.catDetail.CatDetail
import com.example.weldy.screen.catDetail.CatDetailVM
import com.google.gson.Gson
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@HiltAndroidTest
class CatDetailScreenTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()


    // Mock ViewModel
    @BindValue
    @JvmField
    val catDetailVM: CatDetailVM = mock(CatDetailVM::class.java)

    @Before
    fun setup() {
        // Inject Hilt dependencies before tests run
        hiltRule.inject()

        runBlocking {
//            whenever(catDetailVM.insertCatToFavourite(any())).thenReturn(Unit)
        }
    }


    @Test
    fun catDetail_DisplaysImageAndAddToFavouriteButton_WhenIsVisible() {
        // Prepare test data
        val testCat = CatResponse(
            id = "1",
            url = "https://example.com/cat1.jpg",
            width = 300,
            height = 400
        )
        val catItemJson = Gson().toJson(testCat)
        val isVisible = true

        // Launch the Composable
        composeTestRule.setContent {
            // Provide necessary dependencies and navigation
            val navController = rememberNavController()
            CatDetail(
                item = catItemJson,
                isVisible = isVisible
            )
        }

        // Verify the screen is displayed
        composeTestRule.onNodeWithTag("CatDetailScreen").assertIsDisplayed()

        // Verify the "Add to Favourite" button is displayed
        composeTestRule.onNodeWithTag("AddToFavouriteButton").assertIsDisplayed()

        // Verify the image is displayed
        composeTestRule.onNodeWithTag("CatImage").assertIsDisplayed()

        // Perform click on "Add to Favourite" button
        composeTestRule.onNodeWithTag("AddToFavouriteButton").performClick()

        // Verify that insertCatToFavourite was called with correct data
     /*   verify(catDetailVM, times(1)).insertCatToFavourite(
            Cat(
                id = testCat.id,
                url = testCat.url,
                width = testCat.width,
                height = testCat.height
            )
        )*/
    }

    @Test
    fun catDetail_DoesNotShowAddToFavouriteButton_WhenIsVisibleIsFalse() {
        // Prepare test data
        val testCat = CatResponse(
            id = "2",
            url = "https://example.com/cat2.jpg",
            width = 350,
            height = 450
        )
        val catItemJson = Gson().toJson(testCat)
        val isVisible = false

        // Launch the Composable
        composeTestRule.setContent {
            // Provide necessary dependencies and navigation
            val navController = rememberNavController()
            CatDetail(
                item = catItemJson,
                isVisible = isVisible
            )
        }

        // Verify the screen is displayed
        composeTestRule.onNodeWithTag("CatDetailScreen").assertIsDisplayed()

        // Verify the "Add to Favourite" button is not displayed
        composeTestRule.onNodeWithTag("AddToFavouriteButton").assertDoesNotExist()

        // Verify the image is displayed
        composeTestRule.onNodeWithTag("CatImage").assertIsDisplayed()
    }

    @Test
    fun catDetail_DisplaysErrorImage_WhenImageFailsToLoad() {
        // Prepare test data with invalid URL to simulate image load failure
        val testCat = CatResponse(
            id = "3",
            url = "https://invalid-url.com/cat3.jpg",
            width = 400,
            height = 500
        )
        val catItemJson = Gson().toJson(testCat)
        val isVisible = true

        // Launch the Composable
        composeTestRule.setContent {
            // Provide necessary dependencies and navigation
            val navController = rememberNavController()
            CatDetail(
                item = catItemJson,
                isVisible = isVisible
            )
        }

        // Verify the error image is displayed
        // Assuming the error image has contentDescription "Error Image"
        composeTestRule.onNodeWithContentDescription("Error Image").assertIsDisplayed()
    }
}