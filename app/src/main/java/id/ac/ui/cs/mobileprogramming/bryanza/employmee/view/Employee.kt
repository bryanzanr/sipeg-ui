package id.ac.ui.cs.mobileprogramming.bryanza.employmee.view

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R

import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.fragment_employee.*
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.net.wifi.WifiManager
import android.os.Handler
import android.provider.ContactsContract
import android.provider.Telephony
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.R.id.*
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter.AdapterEmployee
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter.AdapterView
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.adapter.EmployeeAdapter
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.data.DataInstance
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.data.DataInterface
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.utilities.ContactReceiver
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.utilities.WifiLock
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.viewmodel.DataView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.header_navigation.*
import kotlinx.android.synthetic.main.main_employee.*
import kotlinx.android.synthetic.main.profile_employee.*
import kotlinx.android.synthetic.main.submit_upload.*
import java.io.FileNotFoundException
import java.util.ArrayList


class Employee : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var contactReceiver: ContactReceiver? = null

    lateinit var mAdView : AdView

    lateinit var dao : DataInterface

    lateinit var dataView : DataView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        dao = DataInstance.getInstance(this).DataInterface()

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-2667938700445321~3491994048")

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        setSupportActionBar(toolbar)

//        fab.setOnClickListener { view ->
        fab.setOnClickListener{
            Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object :PermissionListener{
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        val photo = Intent("android.media.action.IMAGE_CAPTURE")
                        startActivityForResult(photo, 1)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        Toast.makeText(this@Employee, R.string.app_name, Toast.LENGTH_LONG).show()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        Toast.makeText(this@Employee, R.string.action_settings, Toast.LENGTH_LONG).show()
                    }
                })
                .check();
        }
        profile_picture.setOnClickListener{
            openImage()
        }

        don.setOnClickListener{
            val wifiLock = WifiLock()
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            wifiLock.getCurrentSsid(this@Employee, resources.getString(R.string.network1),
                resources.getString(R.string.network2), resources.getString(R.string.wifi),
                manager, resources.getString(R.string.down), resources.getString(R.string.finish), resources.getString(R.string.failed),
                resources.getString(R.string.pass))
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
//        Log.e("email", email)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val email = intent.getStringExtra("email")
        textView.setText(email)
        imageView.setOnClickListener{
            openImage()
        }
        val id = item.itemId

        if (id == R.id.nav_manage) {
            startActivity(Intent(this@Employee, NotificationActivity::class.java))
            //            rv = findViewById<View>(R.id.recycler) as RecyclerView
//
//            val hello = findViewById<View>(R.id.hello) as TextView
            //            ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
//            val arraylist = ArrayList<String>()
//            val wifi = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

            //            SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, arraylist,
            //                    R.layout.row, new String[] { "key" }, new int[] { R.id.list_value });

//            hello.text = resources.getText(R.string.listen)
        } else if (id == R.id.nav_slideshow) {
            startActivity(Intent(this@Employee, NotificationActivity::class.java))
            //            au = AdapterUtility(this, arraylist)
//            rv.setLayoutManager(LinearLayoutManager(this))
//            rv.setAdapter(au)
//            val dividerItemDecoration = DividerItemDecoration(
//                rv.getContext(),
//                resources.configuration.orientation
//            )
//            rv.addItemDecoration(dividerItemDecoration)
        } else if (id == R.id.nav_share) {
            startActivity(Intent(this@Employee, NotificationActivity::class.java))
//            finderService = FinderService(wifi, arraylist, results, size)
//            registerReceiver(finderService, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        }else if (id == R.id.nav_gallery) {
            val trigger = resources.getString(R.string.trigger)
            contactReceiver = ContactReceiver(sample_text)
            val contactFilter = IntentFilter()
            contactFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
            registerReceiver(contactReceiver, contactFilter)
        }else{
//            val intentFilter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
//            val contactReceiver = object : ContactReceiver(){
//                override fun onReceive(contxt: Context?, intent: Intent?) {
//
//                }
//            }
            //                            @Override
//                            public void doSometbing() {
//                                if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
//                                    for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
//                                        String messageBody = smsMessage.getMessageBody();
//                                        Toast.makeText(getApplicationContext(), " = ",
//                                                Toast.LENGTH_LONG).show();
//                                        if (messageBody.equalsIgnoreCase(trigger)){
//                                            arraylist.clear();
//                                            wifi.startScan();
//                                        }
//                                    }
//                                }
//                            }
//                        })
            //            registerReceiver(receiverService, intentFilter);
            //            registerReceiver(new BroadcastReceiver() {
            //                @Override
            //                public void onReceive(Context context, Intent intent) {
            //                }
            //            }, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
            startActivity(Intent(this@Employee, LoginActivity::class.java))
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                sample_text.visibility = View.VISIBLE
                dat.visibility = View.VISIBLE
                sub.visibility = View.GONE
                not.visibility = View.GONE
                sample_text.setText(stringFromJNI())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                sample_text.visibility = View.GONE
                dat.visibility = View.GONE
                not.visibility = View.GONE
                sub.visibility = View.VISIBLE
                tv_dialog_fab_menu_1.setOnClickListener{
                    startActivity(Intent(this@Employee, SubmitActivity::class.java))
                }
                tv_dialog_fab_menu_2.setOnClickListener{
                    startActivity(Intent(this@Employee, SubmitActivity::class.java))
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                bar_nav.visibility = View.GONE
                var listEmployee = dao.getAll()
                val listAdapter = AdapterView()
                rv_notification.adapter = listAdapter

                dataView = ViewModelProviders.of(this).get(DataView::class.java)
                dataView.getAllNotes().observe(this, object : Observer<List<ModelEmployee>> {
                    override fun onChanged(t: List<ModelEmployee>?) {
                        listAdapter.submitList(t);
                    }
                });

                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        p0: RecyclerView,
                        p1: RecyclerView.ViewHolder,
                        p2: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                        dataView.delete(listAdapter.getNoteAt(p0.getAdapterPosition()));
                        Toast.makeText(this@Employee, "Note deleted", Toast.LENGTH_SHORT).show();
                    }
                }).attachToRecyclerView(rv_notification);

                listAdapter.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                    override fun onItemClick(note: ModelEmployee?) {
                        intent = Intent(this@Employee, DetailActivity::class.java);
                        intent.putExtra(DetailActivity.EXTRA_ID, note?.id);
//                        Toast.makeText(this@Employee, "" + note?.id, Toast.LENGTH_LONG).show()
                        intent.putExtra(DetailActivity.EXTRA_TITLE, note?.nim);
                        intent.putExtra(DetailActivity.EXTRA_DESCRIPTION, note?.gender);
                        intent.putExtra(DetailActivity.EXTRA_PRIORITY, note?.name);
                        startActivityForResult(intent, 2);
                    }
                });
                sample_text.visibility = View.GONE
                dat.visibility = View.GONE
                sub.visibility = View.GONE
                not.visibility = View.VISIBLE
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun openImage(){
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val targetUri = data!!.data
            profile_picture.visibility = View.GONE
            imageView.setVisibility(View.GONE)
            profile_progress.visibility = View.VISIBLE
            image_progress.setVisibility(View.VISIBLE)
            Handler().postDelayed({
                try {
                    profile_progress.visibility = View.GONE
                    image_progress.setVisibility(View.GONE)
                    profile_picture.visibility = View.VISIBLE
                    imageView.setVisibility(View.VISIBLE)
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(targetUri))
                    profile_picture.setImageBitmap(bitmap)
                    imageView.setImageBitmap(bitmap)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }, 5000)
        }else if (requestCode == 1 && resultCode == RESULT_OK) {
            val title = data?.getStringExtra(DetailActivity.EXTRA_TITLE);
            val description = data?.getStringExtra(DetailActivity.EXTRA_DESCRIPTION);

            val note = ModelEmployee(title!!, description!!, title!!);
            dataView.insert(note);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            val id = data?.getLongExtra(DetailActivity.EXTRA_ID, -1L);

            if (id == -1L) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            val title = data?.getStringExtra(DetailActivity.EXTRA_TITLE);
            val description = data?.getStringExtra(DetailActivity.EXTRA_DESCRIPTION);

            val note = ModelEmployee(title!!, description!!, title!!);
            note.id = id!!
            dataView.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_employee, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {

                var listEmployee = dao.getAll()
                if (listEmployee.isEmpty()) {
                   val employee = ModelEmployee(resources.getString(R.string.nama), resources.getString(R.string.nip)
                       , resources.getString(R.string.gender))
                    dao.insert(employee)
//                    Toast.makeText(this@Employee, R.string.app_name, Toast.LENGTH_LONG).show()
//                    listEmployee = dao.getAll()
//                    Toast.makeText(this@Employee, listEmployee.size.toString(), Toast.LENGTH_LONG).show()
                }
//                showListView(listEmployee)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (contactReceiver != null){
            unregisterReceiver(contactReceiver)
        }
    }

    fun showListView(listEmployee: List <ModelEmployee>){
        var listView = emp
        val listAdapter = AdapterEmployee(this,listEmployee)
        listView.adapter = listAdapter
//        var recyclerView = emp
//        var recyclerAdapter = AdapterEmployee(this, listEmployee)
//        recyclerView.adapter = recyclerAdapter
    }

    fun bindingIndividual(){
//        binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
//        val user = User("Test", "User")
//        binding.setUser(user)
//        /* atau dapat menggunakan */
//        binding.setVariable(BR.user, user)
    }

//    override fun onOptionsItemSelected(item: MenuItem): ??? {
//        when (item.itemId) {
//            R.id.delete_all_notes -> {
//                noteViewModel.deleteAllNotes()
//                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
//                return true
//            }
//            else -> return super.onOptionsItemSelected(item)
//        }
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
