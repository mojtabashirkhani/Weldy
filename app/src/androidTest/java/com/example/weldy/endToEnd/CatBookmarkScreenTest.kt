package com.example.weldy.endToEnd

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.weldy.MainActivity
import com.example.weldy.screen.catBookmark.CatBookmarkList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class CatBookmarkScreenTest {

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
    fun testCatBookmarkListDisplaysItemsAndNavigatesToDetail() {
        // Set the content to the Composable under test
        composeTestRule.setContent {
            val navController = rememberNavController()
            CatBookmarkList(
                modifier = Modifier.fillMaxSize(),
                navController = navController
            )
        }

        // Wait for the LazyGrid to load items
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithContentDescription("Cat Image").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify that the LazyGrid displays at least one cat image
        composeTestRule.onAllNodesWithContentDescription("Cat Image")
            .assertCountEquals(1) // Adjust the count based on expected items

        // Simulate a click on the first item
        composeTestRule.onAllNodesWithContentDescription("Cat Image").onFirst().performClick()

        // Verify navigation by checking if the Detail screen was opened
        // This assumes the Detail screen has a unique element to check
        composeTestRule.onNodeWithText("Cat Details").assertIsDisplayed() // Adjust the assertion based on the Detail screen UI
    }


}