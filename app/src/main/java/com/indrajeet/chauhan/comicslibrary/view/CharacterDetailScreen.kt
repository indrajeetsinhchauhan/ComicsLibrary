package com.indrajeet.chauhan.comicslibrary.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.indrajeet.chauhan.comicslibrary.CharacterImage
import com.indrajeet.chauhan.comicslibrary.Destination
import com.indrajeet.chauhan.comicslibrary.comicsToString
import com.indrajeet.chauhan.comicslibrary.viewmodel.CollectionDbViewModel
import com.indrajeet.chauhan.comicslibrary.viewmodel.LibraryApiViewModel


@Composable
fun CharacterDetailScreen(
    lvm: LibraryApiViewModel,
    cvm: CollectionDbViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val character = lvm.characterDetails.value
    val collection by cvm.collection.collectAsState()
    val inCollection = collection.map { it.apiId }.contains(character?.id)

    if (character == null) {
        navController.navigate(Destination.Library.route) {
            popUpTo(Destination.Library.route)
            launchSingleTop = true
        }
    }

    LaunchedEffect(key1 = Unit) {
        cvm.setCurrentCharacterId(character?.id!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .padding(bottom = paddingValues.calculateBottomPadding())
            .verticalScroll(
                rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val imageUrl = character?.thumbnail?.path + "." + character?.thumbnail?.extension
        val title = character?.name ?: "No name"
        val comics =
            character?.comics?.items?.mapNotNull { it.name }?.comicsToString() ?: "No comics"
        val description = character?.description ?: "No description"

        CharacterImage(
            url = imageUrl, modifier = Modifier
                .padding(4.dp)
                .width(200.dp)
        )

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(4.dp)
        )

        Text(
            text = comics,
            fontStyle = FontStyle.Italic,
            fontSize = 12.sp,
            modifier = Modifier.padding(4.dp)
        )

        Divider(
            color = Color.LightGray,
            modifier = Modifier.padding(start = 10.dp, top = 4.dp, end = 10.dp, bottom = 4.dp)
        )

        Text(text = description, fontSize = 16.sp, modifier = Modifier.padding(8.dp))

        Button(onClick = {
            if (!inCollection && character != null)
                cvm.addCharacter(character)
        }, modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)) {
            if (!inCollection) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(text = "Add to collection")
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Text(text = "Added")
                }
            }
        }
    }
}