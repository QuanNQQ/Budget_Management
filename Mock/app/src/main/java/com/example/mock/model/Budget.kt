package com.example.mock.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mock.const.*
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = TABLE_NAME)
data class Budget(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val type: String,
    val label : String,
    val description : String,
    val amount : Double,
    val datetime : String,
): Parcelable