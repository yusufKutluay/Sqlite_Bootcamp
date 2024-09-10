package com.yusufkutluay.sqlite_bootcamp.uix.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.yusufkutluay.sqlite_bootcamp.data.entity.Notes
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.DetailPageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.HomePageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.NotesPageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.views.detailpage.DetailPage
import com.yusufkutluay.sqlite_bootcamp.uix.views.notespage.NotesPage

/*
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun NavComponent(
    homePageViewModel: HomePageViewModel,
    notesPageViewModel: NotesPageViewModel,
    detailPageViewModel: DetailPageViewModel
){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "HomePage"){
        composable("HomePage"){ HomePage(homePageViewModel,navController) }
        composable("NotesPage"){ NotesPage(notesPageViewModel,navController) }
        composable(
            "DetailPage/{notes}",
            arguments = listOf(
                navArgument("notes"){type = NavType.StringType}
            )
        ){
            val json = it.arguments?.getString("notes")
            val objectNote = Gson().fromJson(json,Notes::class.java) // json nesneye döner
            DetailPage(detailPageViewModel)
        }


    }

}

 */

