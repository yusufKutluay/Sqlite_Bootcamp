package com.yusufkutluay.sqlite_bootcamp.uix.views.notespage

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.yusufkutluay.sqlite_bootcamp.R
import com.yusufkutluay.sqlite_bootcamp.ui.theme.TopBarColor
import com.yusufkutluay.sqlite_bootcamp.uix.viewmodel.NotesPageViewModel
import kotlinx.coroutines.launch

/**
 * Notes Page Sayfası kullanıcının başlık ve notunu girmesi için tasarlanmıştır
 * Kullanıcı kişileştirilmesi ile daha iyi bir deneyim sağlar
 */

class NotesPage(var notesPageViewModel: NotesPageViewModel) : Screen{

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    override fun Content() {
        val title = remember { mutableStateOf("") }  // room
        val notes = remember { mutableStateOf("") }  // room
        val colorPage = remember { mutableStateOf("#FFFFFF") } // room
        val textSize = remember { mutableStateOf(16) } // room
        val textColor = remember { mutableStateOf("#FFFFFF") } // room
        val menuMore = remember { mutableStateOf(false) }
        val colorsHex = notesPageViewModel.colorlist.value
        val sheetState = rememberModalBottomSheetState()
        val showBottomSheet = remember{ mutableStateOf(false) }
        val date = remember { mutableStateOf("") } // room
        val todoOk = remember { mutableStateOf("false") }
        val checkControl = remember { mutableStateOf(false) }

        val navigator = LocalNavigator.currentOrThrow // navigatör ile geçiş yapma (voyager)

        textColor.value = if (isDarkColor(colorPage.value)) "#FFFFFF" else "#000000"

        //ilk başladığında veriler yüklenir
        LaunchedEffect(key1 = true) {
            notesPageViewModel.colorsHex()
        }
        CurrentDate {
            date.value = it
        }

        // durum çubuğu ve navigationBar rengi değiştirme işlemi
        val view = LocalView.current
        val activity = (view.context as Activity)
        DisposableEffect(colorPage.value) {
            activity.window.statusBarColor = Color(colorPage.value.toColorInt()).toArgb() //durum çubuğu
            activity.window.navigationBarColor = Color(colorPage.value.toColorInt()).toArgb() // gezinme çubuğu


            /**
             * status bar simge renkleri değiştirme
             * colorPage.value göre renk ayarlanır ve siyah mı beyaz mı olduğu seçilir
             *
             */

            if (isDarkColor(colorPage.value)) {
                val controller = activity.window.insetsController
                controller?.setSystemBarsAppearance(
                    0, // Sıfır olarak ayarladığımızda ikonlar beyaz olur
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            }else{
                // SDK uyumluluklarına göre yazılmıştır
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val controller = activity.window.insetsController
                    controller?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // İkonlar siyah olur
                }
            }
            // Sayfa kapatıldığında eski renklere dön
            onDispose {
                activity.window.statusBarColor = TopBarColor.toArgb()
                activity.window.navigationBarColor = Color.White.toArgb()
            }
        }



        Scaffold {paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(colorPage.value.toColorInt()))
                    .imePadding() // klavye açıldığında padding ekler ve sayfa yukarı kayar
            ) {

                /**
                 * Modal BottomSheet çağırarak alttan bir card çıkar
                 * ve renk paletleri gözükür seçime göre colorpage a atanır
                 */
                ModalBottomSheetScreen(
                    colorsHex = colorsHex,
                    selectedColor = {selectedColor->
                        colorPage.value = selectedColor
                    },
                    onDismissRequest = { showBottomSheet.value = false },
                    sheetState = sheetState,
                    showBottomSheet = showBottomSheet.value,
                    colorPage = colorPage.value,
                    textSize = textSize.value,
                    textSizeReturn = { textSizeReturn ->
                        textSize.value = textSizeReturn
                    }
                )

                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp))

                Text(
                    text = date.value,  // tarih fonksiyonu çarğrılır -> room
                    color = Color(textColor.value.toColorInt()),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 15.dp))

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    // Kullanıcının başlık girmesi için textField oluşturulması
                    TextField(
                        value = title.value,
                        onValueChange = {
                            if (it.length <= 30) title.value = it

                        },
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = Color(textColor.value.toColorInt())
                        ),
                        placeholder = {  // yazı yazıldığında gider
                            Text(
                                text = "Başlık",
                                fontSize = 25.sp,
                                color = Color(textColor.value.toColorInt())
                            )},
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent, // sabitken arka plan rengi
                            unfocusedIndicatorColor = Color.Transparent, // sabitken alt çizgi rengi
                            focusedContainerColor = Color.Transparent, // odaklandığında arka plan rengi
                            focusedIndicatorColor = Color.Transparent // odaklandığında alt çzigi rengi
                        ),
                        modifier = Modifier.weight(70f)
                    )

                    Checkbox(
                        checked = todoOk.value.toBoolean(),
                        onCheckedChange = {todoOk.value = it.toString()},
                        modifier = Modifier.weight(10f)
                    )


                    /**
                     * CHECK BUTONU
                     */
                    IconButton(onClick = {
                        navigator.popUntilRoot()
                        println(colorPage.value)
                        notesPageViewModel.notesSave(title.value,notes.value, colorPage.value,textColor.value,textSize.value, date.value,todoOk.value)
                    },Modifier) {
                        Icon(
                            painter = painterResource(id = R.drawable.check),
                            contentDescription = "",
                            modifier = Modifier.size(35.dp,35.dp).weight(10f),
                            tint = Color(textColor.value.toColorInt())
                        )
                    }

                    /**
                     * BottomSheet Kullanımı DropDown
                     * Menü ile notları temizleme ve arka plan rengi gibi işlemler yer alır...
                     */
                    Box(modifier = Modifier.size(50.dp).weight(10f)){
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 5.dp)){
                            IconButton(onClick = { menuMore.value = !menuMore.value }) {
                                Icon(Icons.Filled.MoreVert, contentDescription = "", tint = Color(textColor.value.toColorInt())) // Icons.Filled.MoreVert menu iconu alınır
                            }
                            DropdownMenu(
                                expanded = menuMore.value,
                                onDismissRequest = { menuMore.value = false },
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Temizle") },
                                    onClick = {
                                        title.value = ""
                                        notes.value = ""
                                        menuMore.value = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text(text = "Tema") },
                                    onClick = {
                                        showBottomSheet.value = true
                                        menuMore.value = false
                                    }
                                )
                            }
                        }
                    }


                }


                // Kullanıcının yazdığı notları OutlinedTextField yöntemiyle alma
                OutlinedTextField(
                    // label yazısının çerçevenin üstüne yerleştirir
                    value = notes.value,
                    onValueChange = {
                        notes.value = it

                    },
                    textStyle = TextStyle(
                        fontSize = textSize.value.sp,
                        color = Color(textColor.value.toColorInt()),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 15.dp, end = 15.dp, bottom = 20.dp),
                    label = {
                        Text(
                            text = "Notunuzu yazın",
                            fontSize = 18.sp,
                            color = Color(textColor.value.toColorInt())
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Gray,
                    ),

                    )

            }
        }

    }


}




