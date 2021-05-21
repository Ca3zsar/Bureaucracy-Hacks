package com.example.navbar

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.navbar.ui.feedback.FeedbackFragment
import com.example.navbar.ui.generareMap.MapFragmentTraseu
import com.example.navbar.ui.userProfile.userProfileFragment
import com.google.android.gms.common.util.CollectionUtils.setOf
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), Comunicator {

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /*val retService = registerRetrofitInstance.getRetrofitInstance().create(registerSimpleApi::class.java)
        val responseLiveData: LiveData<Response<Albums>> = liveData {
            val response = retService.getPost() // am facut get la date
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumsList: MutableListIterator<registerPost>? = it.body()?.listIterator()
            if(albumsList!=null){
                while(albumsList.hasNext()){
                    val albumsItem: registerPost = albumsList.next()
                    val result: String = " " + "email: ${albumsItem.email}"+"\n" +
                            " " + "username: ${albumsItem.username}"+"\n" +
                            " " + "name: ${albumsItem.name}"+"\n" +
                            " " + "surname: ${albumsItem.surname}"+"\n" +
                            " " + "password: ${albumsItem.password}"+"\n\n\n"
                    textView4.append(result)
                }
            }
        })*/

        //val fab: FloatingActionButton = findViewById(R.id.fab)
        //fab.setOnClickListener { view ->
        //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show()
        //}
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_search, R.id.nav_traseu, R.id.nav_userProfile, R.id.nav_contact, R.id.nav_feedback, R.id.nav_login, R.id.nav_register), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        /**
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.searchView)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
        /**
        searchView.clearFocus()
        searchView.setQuery("", false)
        searchItem.collapseActionView()
        Toast.makeText(this@MainActivity, "Looking for $query", Toast.LENGTH_LONG).show()
        */

        return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
        //Toast.makeText(this@MainActivity, "Looking for $newText", Toast.LENGTH_LONG).show()
        return false
        }
        })
         */


        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun passDataCom(
        editTextInput: String,
        editTextInput2: String,
        editTextInput3: String
    ) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)
        bundle.putString("message2", editTextInput2)
        bundle.putString("message3", editTextInput3)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = userProfileFragment()
        fragmentB.arguments = bundle

        transaction.replace(R.id.linearLayout, fragmentB)
        transaction.commit()
    }

    override fun passDataActe(idInstitutie: Int, arrayActe: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putInt("idInstitutie", idInstitutie)
        bundle.putStringArrayList("arrayActe", arrayActe)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = MapFragmentTraseu()
        fragmentB.arguments = bundle

        transaction.replace(R.id.linearLayout, fragmentB)
        transaction.commit()
    }

    override fun passDataProcess(process: String) {
        val bundle = Bundle()
        bundle.putString("process", process)

        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = FeedbackFragment()
        fragmentB.arguments = bundle

        transaction.replace(R.id.linearLayout, fragmentB)
        transaction.commit()
    }

}