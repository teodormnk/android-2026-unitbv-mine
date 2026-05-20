package cst.unitbvfmi2026.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ARG_ID)
    var id: Long = 0,
    var city: String,
    var country: String
)
{
    companion object{
        const val ARG_ID = "id"
    }
}