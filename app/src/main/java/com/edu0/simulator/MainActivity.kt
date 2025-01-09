package com.edu0.simulator

import android.content.*
import android.os.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.view.*
import com.edu0.simulator.databinding.*
import com.edu0.simulator.model.*

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.fallBtn.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java).apply {
                putExtra("movement", defaultVFall.toBundle())
            })
        }
        binding.launchBtn.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java).apply {
                putExtra("movement", defaultVLaunch.toBundle())
            })
        }
        binding.launchWithH0Btn.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java).apply {
                putExtra("movement", defaultVLaunchFromHeight.toBundle())
            })
        }
        binding.infoBtn.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }
}