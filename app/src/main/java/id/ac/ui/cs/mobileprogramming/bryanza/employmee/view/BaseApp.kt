package id.ac.ui.cs.mobileprogramming.bryanza.employmee.view

import android.app.Application
import com.facebook.stetho.Stetho

class BaseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}