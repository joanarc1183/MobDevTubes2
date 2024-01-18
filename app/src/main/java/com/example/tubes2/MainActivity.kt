package com.example.tubes2

import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tubes2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        supportActionBar?.hide()
        val startFragment = StartFragment()

        val connMgr = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val nc = connMgr.getNetworkCapabilities(connMgr.activeNetwork)

        if (nc != null) {
            //
            supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, startFragment).commit()
        } else {
            // No connection
//            this.setResult("No network connection available.")
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show()
        }
    }
}