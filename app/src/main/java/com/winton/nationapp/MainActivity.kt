package com.winton.nationapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.winton.nation.ui.theme.MVVMNationAppTheme
import com.winton.nationapp.ui.add_edit_nation.AddEditNationScreen
import com.winton.nationapp.ui.nation_list.NationListScreen
import com.winton.nationapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMNationAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.NATION_LIST
                ) {
                    composable(Routes.NATION_LIST) {
                        NationListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_NATION + "?nationId={nationId}",
                        arguments = listOf(
                            navArgument(name = "nationId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditNationScreen(
                            onPopBackStack = { navController.popBackStack() },
                            showToast = {showToasted(it)}
                        )
                    }
                }
            }
        }
    }
    private fun showToasted(msg:String) = Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}