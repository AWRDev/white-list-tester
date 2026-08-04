package com.awrdev.white_list_tester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.awrdev.white_list_tester.ui.home.HomeScreen
import com.awrdev.white_list_tester.ui.test_list.TestListScreen
import com.awrdev.white_list_tester.ui.theme.WhitelisttesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            WhitelisttesterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationGraph(
                        navController = navController,
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(
        navController: NavHostController,
        modifier: Modifier,
    ) {
        NavHost(navController, startDestination = "Home", modifier = modifier) {
            composable("Home") {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    action = {id ->  navController.navigate("Page/$id") })
            }
            composable("Page/{id}", arguments = listOf(navArgument("id"){type = NavType.IntType})) {
                entry ->
                val pageId = entry.arguments?.getInt("id")
                TestListScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    listToCheck = pageId!!,
                    back = {
                        navController.navigate("Home")
                    }
                )
            }
        }
    }
}

