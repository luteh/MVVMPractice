package com.luteh.mvvmpractice.ui.main

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import com.luteh.mvvmpractice.ui.addeditnote.AddEditNoteActivity
import com.luteh.mvvmpractice.R
import com.luteh.mvvmpractice.data.model.db.Note


class MainActivity : AppCompatActivity(), MainNavigator {

    private val TAG = "MainActivity"

    private val ADD_NOTE_REQUEST = 1
    private val EDIT_NOTE_REQUEST = 2

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onInit()
    }

    override fun onInit() {
        initRecyclerView()
        initViewModel()
        onClickComponent()
    }

    override fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        adapter = NoteAdapter()
        recycler_view.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))
                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recycler_view)


    }

    override fun initViewModel() {
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNoteDatas().observe(this, Observer<List<Note>> { t ->
            //update RecyclerView
            adapter.submitList(t)
        })
    }

    override fun onClickComponent() {
        button_add_note.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivityForResult(intent, ADD_NOTE_REQUEST)
        }

        adapter.setOnItemClickListener(object :
            NoteAdapter.OnItemClickListener {
            override fun onItemClick(note: Note) {
                val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.id)
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.title)
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.description)
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.priority)
                startActivityForResult(intent, EDIT_NOTE_REQUEST)
            }
        })
    }

    override fun onAddNoteResult(data: Intent) {
        val title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
        val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
        val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

        val note = Note(title, description, priority)
        noteViewModel.insert(note)

        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
    }

    override fun onEditNoteResult(data: Intent) {
        val id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)

        if (id == -1) {
            Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
            return
        }

        val title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
        val description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
        val priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

        val note = Note(title, description, priority)
        note.id = id
        noteViewModel.update(note)

        Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            onAddNoteResult(data!!)
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            onEditNoteResult(data!!)

        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }
}
