package com.example.tubes2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tubes2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, homeFragment).commit()
    }
}