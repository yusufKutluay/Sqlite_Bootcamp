package com.yusufkutluay.sqlite_bootcamp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.yusufkutluay.sqlite_bootcamp.ui.theme.Sqlite_BootcampTheme
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.DetailPageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.HomePageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.NotesPageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.views.HomePage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint  // ilk burdan başlayacak dememiz lazım hiltten sonra yoksa uygulama çöker
class MainActivity : ComponentActivity() {

    val notesPageViewModel : NotesPageViewModel by viewModels()
    val homePageViewModel : HomePageViewModel by viewModels()
    val detailPageViewModel : DetailPageViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sqlite_BootcampTheme {
                Navigator(screen = HomePage(homePageViewModel,detailPageViewModel,notesPageViewModel))
            }
        }
    }
}

