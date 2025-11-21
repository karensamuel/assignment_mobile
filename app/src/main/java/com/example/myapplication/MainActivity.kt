package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ListView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {



    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


            val btn = findViewById<Button>(R.id.button)
            val editText = findViewById<EditText>(R.id.editTextText)
        btn.setOnClickListener {
            openGoogle()
        }







    }


private  fun openGoogle(){
    val intent = Intent(Intent.ACTION_VIEW, "https://www.google.com".toUri())
    startActivity(intent)
}

}