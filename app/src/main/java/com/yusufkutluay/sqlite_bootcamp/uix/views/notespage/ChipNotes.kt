package com.yusufkutluay.sqlite_bootcamp.uix.views.notespage

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Modal Bottom Sheet Kullanımı renk paletleri gösterimi
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetScreen(
    colorsHex: List<String>,
    selectedColor: (String) -> Unit,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    showBottomSheet: Boolean,
    colorPage: String,
    textSize : Int,
    textSizeReturn : (Int) -> Unit
) {
    val view = LocalView.current
    val activity = (view.context as Activity)
    activity.window.statusBarColor = Color(colorPage.toColorInt()).toArgb() // Durum çubuğu rengi
    activity.window.navigationBarColor = Color(colorPage.toColorInt()).toArgb() // Gezinme çubuğu rengi

    val textSizeChip = remember { mutableStateOf(16) }
    textSizeChip.value = textSize


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Text(
                text = "Metin Büyüklüğü",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider(
                modifier = Modifier
                    .width(250.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 30.dp, top = 10.dp),
                thickness = 1.dp
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray)
                        .clickable {
                            if (textSizeChip.value > 10) {
                                textSizeChip.value--
                            } else {
                                textSizeChip.value = 10
                            }
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "", modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 10.dp))
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray)
                        .clip(RoundedCornerShape(25.dp)), // Yuvarlak köşeler için,
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "${textSizeChip.value}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 25.sp,
                        color = Color.White
                    )
                    textSizeReturn(textSizeChip.value)
                }
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.LightGray)
                        .clickable {
                            if (textSizeChip.value < 50) {
                                textSizeChip.value++
                            } else {
                                textSizeChip.value = 50
                            }

                        }
                ){
                    Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "", modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 10.dp))

                }
            }
            Text(
                text = "Arka Plan Rengi",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 30.dp)
            )
            Divider(
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                thickness = 1.dp
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 3),
                modifier = Modifier
                    .padding(30.dp)
            ) {
                items(colorsHex.count()) { colorHex ->
                    val color = colorsHex[colorHex]
                    /**
                     * İç içe girmiş bir box tasarımı
                     * Üstteki Box rengin etrafını saracak şekilde gösterilmiştir
                     * Alttaki Box ise renk gösterimi için yapılmıştır
                     *
                     */
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .size(72.dp)
                            .clip(RoundedCornerShape(36.dp))
                            .background(Color.LightGray)
                            .clickable { selectedColor(color) },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(30.dp))
                                .background(Color(color.toColorInt()))
                        )
                    }
                }
            }
        }
    }
}

/**
 * Arka plan renginin koyu olup olmadığını kontrol eder
 * Ona göre metin rengi ayarlanır
 * (siyah arka plan beyaz yazı)
 */
fun isDarkColor(hex: String): Boolean {
    val color = Color(android.graphics.Color.parseColor(hex))
    val red = color.red
    val green = color.green
    val blue = color.blue
    // Renkleri koyuluk belirlemek için RGB bileşenlerinin ortalamasını alır
    return (0.2126 * red + 0.7152 * green + 0.0722 * blue) < 0.5
    // 0.5 ten küçükse renk koyudur
}

/**
 * Tarih (Date()) formatlanarak geri döndürülür ve kullanılır...
 * "dd/MM, HH:mm" -> gün/ay, saat:dakika
 * dd/MM/yyyyy -> gün/ay/yıl --- 08/09/2024
 */
@Composable
fun CurrentDate(date : (String) -> Unit){
    val currentDate = remember {
        val dateFormat = SimpleDateFormat("dd/MM, HH:mm", Locale.getDefault())
        dateFormat.format(Date())
    }
    date(currentDate)
}


