package com.example.shoppinglist

import androidx.annotation.DrawableRes

data class Category(
  @DrawableRes val resId: Int = -1,
  val title: String = "",
  val id: Int = -1
)

object Utils {
  val category = listOf(
    Category(title = "None", resId = R.drawable.icons8_forbidden_32, id = 0),
    Category(title = "Drinks", resId = R.drawable.icons8_drink_50, id = 1),
    Category(title = "Vegetables", resId = R.drawable.icons8_vegetable_50, id = 2),
    Category(title = "Fruits", resId = R.drawable.icons8_fruit_50, id = 3),
    Category(title = "Cleaning", resId = R.drawable.icons8_cleaning_50, id = 4),
    Category(title = "Electronic", resId = R.drawable.icons8_electronic_50, id = 5),

  )
}