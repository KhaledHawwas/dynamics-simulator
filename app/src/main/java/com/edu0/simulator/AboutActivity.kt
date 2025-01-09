package com.edu0.simulator

import android.content.*
import android.net.*
import android.os.*
import androidx.activity.*
import androidx.appcompat.app.*
import androidx.core.view.*
import com.edu0.simulator.databinding.*

class AboutActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.apply {
            tvGmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:khaledhawwas11@gmail.com")
                    putExtra(Intent.EXTRA_SUBJECT, "About App Inquiry")
                }
                startActivity(Intent.createChooser(intent, "Send Email"))
            }
            tvGitHub.setOnClickListener {
                openUrl("https://github.com/KhaledHawwas")
            }
            tvLinkedIn.setOnClickListener {
                openUrl("https://linkedin.com/in/KhaledHawwas11")
            }
        }
    }


    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}