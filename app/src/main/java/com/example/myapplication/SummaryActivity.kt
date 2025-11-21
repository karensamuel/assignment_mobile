package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SummaryActivity : AppCompatActivity() {

    private lateinit var dao: FitnessDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.summary_activity)

        val date = intent.getStringExtra("date") ?: return

        val dateText = findViewById<TextView>(R.id.dateText)
        val totalText = findViewById<TextView>(R.id.totalText)

        dao = AppDatabase.getDatabase(this).activityDao()

        dateText.text = "Date: $date"

        lifecycleScope.launch {
            val total = dao.getTotalDuration(date) ?: 0
            totalText.text = "Total duration: $total min"
        }
    }
}
