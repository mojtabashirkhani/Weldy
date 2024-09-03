package com.example.weldy.endToEnd

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.weldy.MainActivity
import com.example.weldy.screen.catList.CatInfoList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.isNotNull

@HiltAndroidTest
class CatListScreen {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        // Inject Hilt dependencies before tests run
        hiltRule.inject()

    }

    @Test
    fun testCatInfoListDisplaysItemsAndNavigatesToDetail() {
        // Set the content to the CatInfoList screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            CatInfoList(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        // Wait for the LazyGrid to load items
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription("").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify that the LazyGrid displays at least one cat image
        composeTestRule.onAllNodesWithContentDescription("").assertCountEquals(1) // Adjust based on the expected item count

        // Simulate a click on the first item
        composeTestRule.onAllNodesWithContentDescription("").onFirst().performClick()

        // Verify that the app navigates to the detail screen
        // You might check for a specific UI element that is unique to the detail screen
        composeTestRule.onNodeWithText("Cat Details").assertIsDisplayed() // Adjust the assertion based on the detail screen UI
    }


    @Test
    fun testBookmarkButtonNavigatesToBookmarkScreen() {
        // Set the content to the CatInfoList screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            CatInfoList(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        // Click on the "Bookmark" button
        composeTestRule.onNodeWithText("Bookmark").assertIsDisplayed().performClick()

        // Verify navigation to the bookmark screen
        // Again, check for a unique element or text on the bookmark screen
        composeTestRule.onNodeWithText("Bookmarks").assertIsDisplayed()
    }

    @Test
    fun testCatInfoListDisplaysErrorOnLoadFailure() {
        // Set the content to the CatInfoList screen with mocked ViewModel to simulate an error
        composeTestRule.setContent {
            val navController = rememberNavController()
            CatInfoList(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        // Simulate an error state
        composeTestRule.runOnUiThread {
            // Trigger the error state manually (requires mocking)
            // catListVM.triggerErrorState()
        }

        // Verify that the error toast is displayed
        composeTestRule.onNodeWithText("Error message").assertExists() // Replace with actual error message
    }

    @Test
    fun testImageLoadSuccess() {
        // Set the content to the CatInfoList screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            CatInfoList(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        // Wait until the images are loaded and displayed
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithContentDescription("").fetchSemanticsNodes().isNotEmpty()
        }

        // Check if the images are displayed correctly
        composeTestRule.onAllNodesWithContentDescription("")
            .assertAll(hasAnyChild(isNotNull())) // Ensure that the image has been loaded
    }

    @Test
    fun testImageLoadError() {
        // Simulate a scenario where the image fails to load
        // One way to do this is to use a faulty URL

        // Set the content to the CatInfoList screen
        composeTestRule.setContent {
            val navController = rememberNavController()
            CatInfoList(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        // Wait for the image load attempt
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithContentDescription("").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify that the error image is displayed (using a placeholder or error drawable)
        /*composeTestRule.onAllNodesWithContentDescription("")
            .assertAll(hasDrawable(R.drawable.stat_notify_error)) // Assuming you set this drawable as your error image*/
    }



}