package com.example.shoppinglist.ui.home

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.widget.Space
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberDismissState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.Category
import com.example.shoppinglist.Utils
import com.example.shoppinglist.data.room.ItemsWithStoreAndList
import com.example.shoppinglist.data.room.models.Item
import com.example.shoppinglist.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
  onNavigate: (Int) -> Unit
) {
  val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
  val homeState = homeViewModel.state
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = { onNavigate.invoke(-1) }) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Add"
        )
      }
    }) {
    LazyColumn {
      item {
        LazyRow {
          items(Utils.category) { category: Category ->
            CategoryItem(
              iconRes = category.resId,
              title = category.title,
              selected = category == homeState.category
            ) {
              homeViewModel.onCategoryChange(category)
            }
            Spacer(modifier = Modifier.size(16.dp))
          }
        }
      }
      items(homeState.items) {
        val dismissState = rememberDismissState(
          confirmStateChange = { value ->
            if (value == DismissValue.DismissedToEnd){
              homeViewModel.deleteItem(it.item)
            }
            true
          }
        )
        SwipeToDismiss(
          state = dismissState,
          background = {
            Surface(modifier = Modifier.fillMaxWidth(), color = Color.Red){

            }
          },
          dismissContent = {
            ShoppingItems(
              item = it,
              isChecked = it.item.isChecked,
              onCheckedChange = homeViewModel::onItemChekedChange
            ) {
              onNavigate.invoke(it.item.id)
            }
          })

      }
    }
  }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun CategoryItem(
  @DrawableRes iconRes: Int,
  title: String,
  selected: Boolean,
  onItemClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
      .selectable(
        selected = selected,
        interactionSource = MutableInteractionSource(),
        indication = rememberRipple(),
        onClick = { onItemClick.invoke() }
      ),
    border = BorderStroke(
      1.dp,
      if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
      else MaterialTheme.colorScheme.onSurface
    ),
    shape = Shapes.large,
    backgroundColor = if (selected)
      MaterialTheme.colorScheme.primary.copy(.5f)
    else MaterialTheme.colorScheme.surface,
    contentColor = if (selected) MaterialTheme.colorScheme.onPrimary
    else MaterialTheme.colorScheme.onSurface,
  ) {
    Row(
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(8.dp)
    ) {
      Icon(
        painter = painterResource(id = iconRes),
        contentDescription = "icon",
        modifier = Modifier.size(24.dp)
      )
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        text = title,
        style = androidx.compose.material.MaterialTheme.typography.h6,
        fontWeight = FontWeight.Medium
      )
    }

  }
}

@Composable
fun ShoppingItems(
  item: ItemsWithStoreAndList,
  isChecked: Boolean,
  onCheckedChange: (Item, Boolean) -> Unit,
  onItemClick: () -> Unit
) {
  Card(modifier = Modifier
    .fillMaxWidth()
    .clickable {
      onItemClick.invoke()
    }
    .padding(8.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.padding(8.dp)) {
        Text(
          text = item.item.itemName,
          style = androidx.compose.material.MaterialTheme.typography.h6,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = item.store.storeName)
        Spacer(modifier = Modifier.size(4.dp))
        CompositionLocalProvider(
          LocalContentAlpha provides
                  ContentAlpha.disabled
        ) {
          Text(
            text = formatDate(item.item.date),
            style = androidx.compose.material.MaterialTheme.typography.subtitle1
          )
        }
      }
      Column(modifier = Modifier.padding(8.dp)) {
        Text(
          text = "Qty: ${item.item.qty}",
          style = androidx.compose.material.MaterialTheme.typography.h6,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(4.dp))
        Checkbox(
          checked = isChecked,
          onCheckedChange = {
            onCheckedChange.invoke(item.item, it)
          }
        )
      }
    }
  }
}

fun formatDate(date: Date): String =
  SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)