package com.example.weldy.endToEnd

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.example.weldy.MainActivity
import com.example.weldy.R
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


    @Test
    fun asyncImage_DisplaysPlaceholderAndLoadsImageWithFillScale() {
        // Set up Coil to use a fake image loader
        composeTestRule.setContent {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data("https://example.com/image.png")
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                contentDescription = null, // contentDescription is set to null
                modifier = Modifier.size(
                    (LocalConfiguration.current.screenWidthDp * 0.3f).dp,
                    (LocalConfiguration.current.screenWidthDp * 0.4f).dp
                ),
                contentScale = ContentScale.FillBounds,
//                error = painterResource(id = R.drawable.stat_notify_error),
//                placeholder = painterResource(id = R.drawable.presence_away)
            )
        }

        // Check if the placeholder is displayed initially
        composeTestRule.onNodeWithTag("placeholderTag")
            .assertIsDisplayed()

        // Simulate loading an image
        composeTestRule.waitForIdle()

        // Check if the image is displayed after loading
        composeTestRule.onNodeWithTag("imageTag")
            .assertIsDisplayed()

        // You can add more assertions to check error handling or crossfade animations
    }


}