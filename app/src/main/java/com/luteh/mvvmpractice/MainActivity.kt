package com.luteh.mvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders



class MainActivity : AppCompatActivity() {

    private var noteViewModel: NoteViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel!!.getAllNoteDatas().observe(this, object : Observer<List<Note>>{
            override fun onChanged(t: List<Note>?) {
                //update RecyclerView
                Toast.makeText(this@MainActivity, "onChanged", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
