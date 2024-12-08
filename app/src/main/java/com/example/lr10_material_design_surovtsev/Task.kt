package com.example.lr10_material_design_surovtsev
import java.io.Serializable

data class Task(
    val id: String,
    val title: String,
    val description: String
): Serializable