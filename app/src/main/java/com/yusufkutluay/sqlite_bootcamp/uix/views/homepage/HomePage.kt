package com.yusufkutluay.sqlite_bootcamp.uix.views

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.yusufkutluay.sqlite_bootcamp.R
import com.yusufkutluay.sqlite_bootcamp.ui.theme.HomePageColor
import com.yusufkutluay.sqlite_bootcamp.ui.theme.TopBarColor
import com.yusufkutluay.sqlite_bootcamp.ui.theme.TopBarTitle
import com.yusufkutluay.sqlite_bootcamp.ui.theme.lobster
import com.yusufkutluay.sqlite_bootcamp.ui.theme.moderustic
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.DetailPageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.HomePageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.NotesPageViewModel
import com.yusufkutluay.sqlite_bootcamp.uix.views.detailpage.DetailPage
import com.yusufkutluay.sqlite_bootcamp.uix.views.notespage.NotesPage


class HomePage(var homePageViewModel: HomePageViewModel,var detailPageViewModel: DetailPageViewModel,var notesPageViewModel: NotesPageViewModel) : Screen {

    @RequiresApi(Build.VERSION_CODES.R)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val list = homePageViewModel.noteList.observeAsState(listOf())
        val showSearchEditText = remember { mutableStateOf(false) }
        val searchText = remember { mutableStateOf("") }
        val menuMore = remember { mutableStateOf(false) }
        val isShowSelectedCard = remember { mutableStateOf<Int?>(null) }
        val openAlertDialog = remember { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = true) {
            homePageViewModel.notesLoad()
            isShowSelectedCard.value = null
            menuMore.value = false
        }

        @Composable
        fun statusBar(){
            val view = LocalView.current
            val activity = (view.context as Activity)
            activity.window.statusBarColor = Color(0xFF062D37).toArgb()
            activity.window.navigationBarColor = HomePageColor.toArgb()
        }
        

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        if (showSearchEditText.value){
                            TextField(
                                value = searchText.value,
                                onValueChange = {
                                    searchText.value = it
                                    homePageViewModel.search(it)
                                },
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.White
                                ),
                                placeholder = {
                                    Text(text = "Notlarında ara...", fontSize = 18.sp, color = Color.Gray)
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent

                                )

                            )
                        }else{
                            Text(text = "Not'la", fontSize = 30.sp, fontFamily = lobster)
                            homePageViewModel.notesLoad()
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = TopBarColor,
                        titleContentColor = TopBarTitle
                    ),
                    actions = {
                        if (showSearchEditText.value){
                            IconButton(onClick = {
                                searchText.value = ""
                                showSearchEditText.value = false
                            }) {
                                Icon(Icons.Filled.Close , contentDescription = "", tint = Color.White)
                            }
                        }else{
                            IconButton(onClick = { showSearchEditText.value = true }) {
                                Icon(Icons.Filled.Search , contentDescription = "", tint = Color.White)
                            }
                        }
                    }

                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.size(65.dp),
                    onClick = { navigator.push(NotesPage(notesPageViewModel)) },
                    content = { Icon(painter = painterResource(id = R.drawable.icon_pencil), contentDescription = "", tint = TopBarColor, modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 15.dp))},
                    containerColor = TopBarTitle
                )
            }


        ) { paddingValues ->


            statusBar() // status bar rengi değişimi

            if (openAlertDialog.value){
                AlertDialog(
                    onDismissRequest = { openAlertDialog.value },
                    text = { Text(text = "Bu not silinsin mi?")},
                    confirmButton = {
                        OutlinedButton(onClick = {
                            homePageViewModel.delete(isShowSelectedCard.value!!)
                            isShowSelectedCard.value = null
                            openAlertDialog.value = false
                        }) {
                            Text(text = "Sil")
                        }
                    },
                    dismissButton = {
                        OutlinedButton(onClick = { openAlertDialog.value = false }) {
                            Text(text = "İptal")
                        }
                    }
                )
            }

            /**
             * liste boş olup olmadığı kontrol edilir ve ona göre veriler çekilerek
             * görünüm sağlanır
             */
            if (list.value.isNotEmpty()){
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 2.dp, end = 2.dp, top = 10.dp)
                        .background(HomePageColor)

                ) {
                    items(
                        count = list.value.count(),
                        itemContent = {
                            val notes = list.value[it]
                            if (isShowSelectedCard.value == notes.id){

                                Box(modifier = Modifier.wrapContentSize()){
                                    DropdownMenu(expanded = (menuMore.value), onDismissRequest = { menuMore.value = false }) {
                                        DropdownMenuItem(
                                            text = {
                                                Row (
                                                    modifier = Modifier.wrapContentSize(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ){
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Icon(Icons.Filled.Delete, contentDescription = "")
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Text(text = "Sil")
                                                }
                                            },
                                            onClick = {
                                                openAlertDialog.value = !openAlertDialog.value
                                            })
                                        DropdownMenuItem(
                                            text = {
                                                Row (
                                                    modifier = Modifier.wrapContentSize(),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.Center
                                                ){
                                                    Checkbox(
                                                        checked = notes.todoOk.toBoolean(),
                                                        onCheckedChange = {
                                                            notes.todoOk = it.toString()
                                                            homePageViewModel.update(notes.id,notes.title,notes.notes,notes.colorPage,notes.textColor,notes.textSize,notes.currentDate,notes.todoOk)
                                                            homePageViewModel.updateSortedNotes()
                                                            menuMore.value = false
                                                        }
                                                    )

                                                    println("${notes.id} + ${notes.todoOk}")
                                                    Text(text = "Onayla")
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                }
                                            },
                                            onClick = {

                                            })
                                    }
                                }
                            }
                            ElevatedCard(
                                modifier = Modifier
                                    .padding(all = 5.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(notes.colorPage.toColorInt())
                                ),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .combinedClickable(
                                            onClick = {
                                                navigator.push(
                                                    DetailPage(
                                                        detailPageViewModel = detailPageViewModel,
                                                        notes
                                                    )
                                                )
                                            },
                                            onLongClick = {
                                                isShowSelectedCard.value = notes.id
                                                menuMore.value = true
                                            }
                                        )
                                ) {

                                    Column(modifier = Modifier.padding(12.dp)) {
                                        if (notes.title != ""){
                                            Text(
                                                text = notes.title,
                                                color = Color(notes.textColor.toColorInt()),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                fontFamily = moderustic,
                                                fontSize = 16.sp,
                                                textDecoration = if (notes.todoOk.toBoolean()) TextDecoration.LineThrough else TextDecoration.None
                                            )
                                        }
                                        if (notes.notes != ""){
                                            Text(
                                                text = notes.notes,
                                                color = Color(notes.textColor.toColorInt()),
                                                maxLines = 7,
                                                overflow = TextOverflow.Ellipsis,
                                                fontFamily = moderustic,
                                                fontSize = 14.sp,
                                                textDecoration = if (notes.todoOk.toBoolean()) TextDecoration.LineThrough else TextDecoration.None
                                            )
                                        }
                                        if (notes.title == "" && notes.notes == ""){
                                            Spacer(modifier = Modifier.height(50.dp))
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(text = notes.currentDate, color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    }

                                }

                            }

                        }
                    )

                }
            }else{

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(HomePageColor)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_image),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

            }


        }
    }


}






