package com.yusufkutluay.sqlite_bootcamp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import org.jetbrains.annotations.NotNull

@VersionedParcelize
@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @NotNull var id:Int,
    @ColumnInfo(name = "title") @NotNull var title:String,
    @ColumnInfo(name = "notes") @NotNull var notes:String,
    @ColumnInfo(name = "colorPage") @NotNull var colorPage:String,
    @ColumnInfo(name = "textColor") @NotNull var textColor:String,
    @ColumnInfo(name = "textSize") @NotNull var textSize:Int,
    @ColumnInfo(name = "currentDate") @NotNull var currentDate:String,
    @ColumnInfo(name = "todoOk") @NotNull var todoOk:String
) {
}