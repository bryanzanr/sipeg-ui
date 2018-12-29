package id.ac.ui.cs.mobileprogramming.bryanza.employmee.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable

//Annotation Parcelize for make the data class parcelable
//@Parcelize
//Annotation Entity to declare that this class is a table with name = students
@Entity(tableName = "employee")
data class ModelEmployee(@ColumnInfo(name = "name")var name: String = "",
                         @ColumnInfo(name = "nip")var nim: String = "",
                         @ColumnInfo(name = "gender")var gender: String = "",
                         @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0)
//    : Parcelable
//collumn use @CollumnInfo Annotation
//You can aslo declare the primary key by adding @PrimaryKey Annotation