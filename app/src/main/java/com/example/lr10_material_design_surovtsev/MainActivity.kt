package com.example.lr10_material_design_surovtsev

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var navView: NavigationView
    private lateinit var addTaskLauncher: ActivityResultLauncher<Intent>
    private lateinit var taskDetailLauncher: ActivityResultLauncher<Intent>

    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        drawerLayout = findViewById(R.id.drawerLayout)
        fab = findViewById(R.id.fab)
        recyclerView = findViewById(R.id.recyclerView)
        navView = findViewById(R.id.navView)

        // Set up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Register ActivityResultLauncher for adding task
        addTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val newTask = result.data?.getSerializableExtra("new_task") as? Task
                newTask?.let {
                    taskList.add(it)
                    recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }

        // Register ActivityResultLauncher for task detail (e.g., for deleting task)
        taskDetailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val deletedTaskId = result.data?.getStringExtra("deletedTaskId")
                deletedTaskId?.let {
                    // Remove task from list
                    taskList.removeAll { task -> task.id == it }
                    recyclerView.adapter?.notifyDataSetChanged()  // Notify adapter to update UI
                }
            }
        }

        // Set up FloatingActionButton
        fab.setOnClickListener {
            // Open AddTaskActivity when FAB is clicked
            val intent = Intent(this, AddTaskActivity::class.java)
            addTaskLauncher.launch(intent)
        }

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TaskAdapter(taskList) { task ->
            // Handle task item click
            val intent = Intent(this, TaskDetailActivity::class.java).apply {
                putExtra("task", task)  // Pass task to detail activity
            }
            taskDetailLauncher.launch(intent)
        }

        // Set up Navigation Drawer
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_all_tasks -> {
                    Toast.makeText(this, "All Tasks", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_completed_tasks -> {
                    Toast.makeText(this, "Completed Tasks", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_pending_tasks -> {
                    Toast.makeText(this, "Pending Tasks", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Open the navigation drawer if the home button is clicked
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    // Handle options menu item selection
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
