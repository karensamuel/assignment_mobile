package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var dao: FitnessDao
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    private val items = mutableListOf<String>()
    private val storedActivities = mutableListOf<FitnessEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)
        dao = db.activityDao()

        val nameInput = findViewById<EditText>(R.id.activityName)
        val durationInput = findViewById<EditText>(R.id.duration)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)

        val addBtn = findViewById<Button>(R.id.addBtn)
        val filterBtn = findViewById<Button>(R.id.filterBtn)
        val showAllBtn = findViewById<Button>(R.id.showAllBtn)
        listView = findViewById(R.id.activityList)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        loadAllActivities()

        addBtn.setOnClickListener {
            val name = nameInput.text.toString()
            val duration = durationInput.text.toString().toIntOrNull() ?: return@setOnClickListener

            val date = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"

            lifecycleScope.launch {
                dao.insertActivity(FitnessEntity(activityName = name, duration = duration, date = date))
                loadAllActivities()
            }
        }

        filterBtn.setOnClickListener {
            val date = "${datePicker.year}-${datePicker.month + 1}-${datePicker.dayOfMonth}"
            lifecycleScope.launch {
                val filtered = dao.getActivitiesByDate(date)
                displayActivities(filtered)
            }
        }

        showAllBtn.setOnClickListener {
            loadAllActivities()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selected = storedActivities[position]
            val intent = Intent(this, SummaryActivity::class.java)

            val bundle = Bundle()
            bundle.putString("date", selected.date)
            bundle.putInt("duration", selected.duration)

            intent.putExtras(bundle)

            Log.d("MainActivity", "Date: ${selected.date}, Duration: ${selected.duration}")

            startActivity(intent)

        }
    }

    private fun loadAllActivities() {
        lifecycleScope.launch {
            val list = dao.getAllActivities()
            displayActivities(list)
        }
    }

    private fun displayActivities(list: List<FitnessEntity>) {
        items.clear()
        storedActivities.clear()
        storedActivities.addAll(list)

        list.forEach {
            items.add("${it.activityName} - ${it.duration} min (${it.date})")
        }

        adapter.notifyDataSetChanged()
    }
}
