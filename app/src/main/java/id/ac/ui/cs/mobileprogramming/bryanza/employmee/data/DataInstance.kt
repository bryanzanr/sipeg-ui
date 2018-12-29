package id.ac.ui.cs.mobileprogramming.bryanza.employmee.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee

//This annotation to tell room what is the entity/table of the database
@Database(entities = arrayOf(ModelEmployee::class), version = 1, exportSchema = false)
abstract class DataInstance : RoomDatabase() {

    abstract fun DataInterface(): DataInterface
    companion object {
        private var INSTANCE: DataInstance? = null

        fun getInstance(context: Context): DataInstance {
            if (INSTANCE == null) {
                synchronized(DataInstance::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DataInstance::class.java, "employeedata.db").allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}