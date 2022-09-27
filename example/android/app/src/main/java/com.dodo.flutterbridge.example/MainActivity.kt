package com.dodo.flutterbridge.example

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.dodo.flutterbridge.FlutterBridge
import com.dodo.flutterbridge.example.databinding.ActivityMainBinding
import com.dodo.flutterbridge.function.FlutterFunction
import com.dodo.flutterbridge.function.FlutterHandler
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.flow.flowOf
import rx.Observable
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    companion object{
        var count = 0
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {

            val arguments = HashMap<String, Any>()
            arguments["data"] = "arguments form native"
            FlutterBridge.activity()
                .pageName("mainPage")
                .arguments(arguments)
                .navigate()

            val handler = FlutterHandler<Int>("flutterInvoke") {
                return@FlutterHandler flowOf(it.plus(1))
            }
//
//            handler.dispose()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}