package id.ac.ui.cs.mobileprogramming.bryanza.employmee.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee

//declare it as a DAO
@Dao
interface DataInterface {
    @Query("SELECT * from employee")
    fun getAll(): List<ModelEmployee>

    @Query("SELECT * from employee")
    fun getLiveData(): LiveData<List<ModelEmployee>>

    @Insert(onConflict = REPLACE)
    fun insert(student: ModelEmployee)

    @Delete
    fun delete(student: ModelEmployee)

    @Query("DELETE FROM employee")
    fun deleteAll()

    @Query("UPDATE employee SET name =:employeeName, nip =:employeeNip, gender =:employeeGen WHERE id =:employeeId")
    fun update(employeeId: Long, employeeName: String, employeeNip: String, employeeGen: String)

    @Update
    fun updateModel(student: ModelEmployee)
}