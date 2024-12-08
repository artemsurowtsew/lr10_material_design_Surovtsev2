package com.example.lr10_material_design_surovtsev

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lr10_material_design_surovtsev.databinding.ActivityTaskDetailBinding
import com.google.android.material.snackbar.Snackbar

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Отримуємо передане завдання
        val task = intent.getSerializableExtra("task") as? Task
        task?.let {
            binding.titleTextView.text = it.title
            binding.descriptionTextView.text = it.description
        }

        // Налаштування кнопки "Видалити"
        binding.deleteButton.setOnClickListener {
            task?.let {
                val resultIntent = Intent()
                resultIntent.putExtra("deletedTaskId", it.id)
                setResult(Activity.RESULT_OK, resultIntent)
                Snackbar.make(binding.root, "Завдання видалено", Snackbar.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
