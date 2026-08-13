package com.awrdev.white_list_tester

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.awrdev.white_list_tester.ui.home.HomeScreen
import com.awrdev.white_list_tester.ui.home.HomeViewModel
import com.awrdev.white_list_tester.ui.test_list.TestListScreen
import com.awrdev.white_list_tester.ui.theme.WhitelisttesterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Разрешение получено, запускаем логику (например, открываем камеру)
                Log.d("AWR", "Notifications are granted")
            } else {
                // Отказ. Объясните пользователю, почему функция недоступна
                Log.d("AWR", "Notifications are denied")
            }
        }

        enableEdgeToEdge()
        setContent {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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
            val homeViewModel = HomeViewModel()
            composable("Home") {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = homeViewModel,
                    selectListScreen = { id ->  navController.navigate("Page/$id") })
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

