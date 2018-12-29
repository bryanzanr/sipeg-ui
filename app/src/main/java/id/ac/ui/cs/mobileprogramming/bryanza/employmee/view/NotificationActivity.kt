package id.ac.ui.cs.mobileprogramming.bryanza.employmee.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter.AdapterEmployee
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter.EmployeeAdapter
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.data.DataInstance
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.fragment_employee.*
import android.R.attr.orientation
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.GridLayout
import android.widget.Toast


class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        bar_nav.visibility = View.VISIBLE

        var dao = DataInstance.getInstance(this).DataInterface()
        var listEmployee = dao.getAll()
        showRecyclerView(listEmployee)

        iv_back.setOnClickListener{
            startActivity(Intent(this@NotificationActivity, Employee::class.java))
        }

    }

    fun showRecyclerView(listEmployee: List <ModelEmployee>){

        val listAdapter = EmployeeAdapter(this,listEmployee)
        rv_notification.adapter = listAdapter
//        var recyclerView = emp
//        var recyclerAdapter = AdapterEmployee(this, listEmployee)
//        recyclerView.adapter = recyclerAdapter
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        val orientation = newConfig?.orientation

//        Toast.makeText(this@NotificationActivity, "sebelum", Toast.LENGTH_LONG).show()

        when (orientation) {

            Configuration.ORIENTATION_LANDSCAPE -> {
//                Toast.makeText(this@NotificationActivity, "landscape", Toast.LENGTH_LONG).show()
                rv_notification.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            }
            Configuration.ORIENTATION_PORTRAIT -> {
//                Toast.makeText(this@NotificationActivity, "potrait", Toast.LENGTH_LONG).show()
                rv_notification.layoutManager = LinearLayoutManager(this@NotificationActivity)
            }
        }//to do something
    }

}