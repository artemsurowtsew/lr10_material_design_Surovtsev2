package com.example.lr10_material_design_surovtsev

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lr10_material_design_surovtsev.databinding.ActivityAddTaskBinding
import com.google.android.material.snackbar.Snackbar


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val title = binding.taskTitleEditText.text.toString()
            val description = binding.taskDescriptionEditText.text.toString()

            if (title.isNotBlank() && description.isNotBlank()) {
                val task = Task(System.currentTimeMillis().toString(), title, description)
                val resultIntent = Intent().apply {
                    putExtra("new_task", task)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Snackbar.make(binding.root, "Заповніть всі поля!", Snackbar.LENGTH_SHORT).show()
            }
        }

    }
}
