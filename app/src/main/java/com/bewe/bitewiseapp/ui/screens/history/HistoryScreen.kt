package com.bewe.bitewiseapp.ui.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.bewe.bitewiseapp.R
import com.bewe.bitewiseapp.ViewModelFactory
import com.bewe.bitewiseapp.data.UiState
import com.bewe.bitewiseapp.data.remote.model.ProductsItem
import com.bewe.bitewiseapp.di.Injection
import com.bewe.bitewiseapp.ui.screens.home.HomeContent
import com.bewe.bitewiseapp.ui.theme.BiteWiseAppTheme
import com.bewe.bitewiseapp.ui.theme.Orange

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = viewModel(
        factory = ViewModelFactory(repository = Injection.provideRepository())
    ),
) {
    when (val uiState = viewModel.result) {
        is UiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                viewModel.getAllProducts()
                Text("Loading...")
            }
        }

        is UiState.Success -> {
            val data = uiState.data
            HistoryContent(
                itemsList = data.products
            )
        }

        is UiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text("error")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryContent(
    modifier: Modifier = Modifier,
    itemsList: List<ProductsItem>,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Your History",
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 34.dp, end = 34.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                items(itemsList) {
                    HistoryItem(
                        data = it
                    )
                }
            }
        }
    }

}

@Composable
fun HistoryItem(
    data: ProductsItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(48.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            AsyncImage(
                model = data.images[0],
                contentDescription = "Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

//            Image(
//                painter = painterResource(id = R.drawable.sample_picture),
//                contentDescription = "Image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxWidth()
//            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Orange),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = data.title,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    BiteWiseAppTheme {
        HistoryScreen()
    }
}