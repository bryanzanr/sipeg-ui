package id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
//import android.databinding.DataBindingUtil
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
import android.widget.BaseAdapter
//import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R
//import id.ac.ui.cs.mobileprogramming.bryanza.employmee.databinding.EmployeeDataBinding
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee
import java.lang.NullPointerException

class AdapterEmployee(private val activity: Activity, private val listPerson: List<ModelEmployee>) : BaseAdapter() {

    override fun getCount(): Int {
        return listPerson.size
    }

    override fun getItem(position: Int): Any {
        return listPerson[position]
    }

    override fun getItemId(position: Int): Long {
        return listPerson[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var convertView = convertView
//        //to be implemented
//        val binding: EmployeeDataBinding?
//        if (convertView == null) {
//            convertView = LayoutInflater.from(activity).inflate(R.layout.employee_data, null)
//            binding = DataBindingUtil.bind(convertView!!)
//            convertView.tag = binding
//        } else {
//            binding = convertView.tag as EmployeeDataBinding
//        }
//        binding!!.emp = listPerson[position]
//        return binding.root
        return null!!
    }
}