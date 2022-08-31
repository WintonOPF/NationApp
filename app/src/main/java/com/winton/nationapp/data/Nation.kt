package com.winton.nationapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nation (
    val name: String,
    val isDone: Boolean,
    @PrimaryKey val id: Int? = null
)