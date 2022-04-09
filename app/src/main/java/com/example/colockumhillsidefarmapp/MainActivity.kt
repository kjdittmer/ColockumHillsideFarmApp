package com.example.colockumhillsidefarmapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.BuildConfig
import com.example.colockumhillsidefarmapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sdkKey = "a757a17793c83937a9151f045f75d3f9-us19"
        val isDebugBuild = BuildConfig.DEBUG
<<<<<<< Updated upstream
//        val configuration = MailchimpSdkConfiguration.Builder(context, sdkKey)
//            .isDebugModeEnabled(isDebugBuild)
//            .isAutoTaggingEnabled(true)
//            .build()
//        val mailchimpSdk = Mailchimp.initialize(configuration)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
=======
        //val configuration = MailchimpSdkConfiguration.Builder(context, sdkKey)
        //    .isDebugModeEnabled(isDebugBuild)
        //    .isAutoTaggingEnabled(true)
        //    .build()
        //val mailchimpSdk = Mailchimp.initialize(configuration)
        //binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
>>>>>>> Stashed changes

        setSupportActionBar(binding.appBarMain.toolbar)

        //binding.appBarMain.fab.setOnClickListener { view ->
        //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show()
        //}
        //functions for menu
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_about_us, R.id.nav_contact_us, R.id.nav_recipes, R.id.nav_store, R.id.nav_vendor
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        ShoppingCart.getInstance()



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_shopping_cart -> {
                val intent : Intent = Intent(this, ShoppingCartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_favorites -> {
                val intent : Intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_wishlist -> {
                val intent : Intent = Intent(this, WishlistActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}