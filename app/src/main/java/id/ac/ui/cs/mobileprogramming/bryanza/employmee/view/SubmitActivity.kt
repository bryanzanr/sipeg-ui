package id.ac.ui.cs.mobileprogramming.bryanza.employmee.view

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.upload_submit.*

class SubmitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_submit)

        btn_submit.setOnClickListener{
            startActivity(Intent(this@SubmitActivity, Employee::class.java))
        }

        iv_back.setOnClickListener{
            startActivity(Intent(this@SubmitActivity, Employee::class.java))
        }
    }
}