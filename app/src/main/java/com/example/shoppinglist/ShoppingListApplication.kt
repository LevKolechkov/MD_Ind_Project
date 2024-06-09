package com.example.shoppinglist

import android.app.Application

class ShoppingListApplication: Application() {
  override fun onCreate() {
    super.onCreate()
    Graph.provide(this)
  }
}