package com.kazim.catretrofitjetpack

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kazim.catretrofitjetpack.model.CatModelItem
import com.kazim.catretrofitjetpack.service.CatAPI
import com.kazim.catretrofitjetpack.ui.theme.CatRetrofitJetPackTheme
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatRetrofitJetPackTheme {
                // A surface container using the 'background' color from the theme
                 MainScreen()
            }
        }
    }
}
//https://api.thecatapi.com/v1/breeds?attach_breed=2a7ba349-a4ac-4ab6-9e8f-d79901153f3d
@Composable
fun MainScreen(){
    val catModelList = remember {
        mutableStateListOf<CatModelItem>()
    }
    val BASE_URL ="https://api.thecatapi.com/"

    val retrofit =Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatAPI::class.java)
    val call =retrofit.getData()

    call.enqueue(object:Callback<List<CatModelItem>>{
        override fun onResponse(
            call: Call<List<CatModelItem>>,
            response: Response<List<CatModelItem>>
        ) {
            if (response.isSuccessful){
                response.body()?.let {
                   catModelList.addAll(it)
                }
            }

        }

        override fun onFailure(call: Call<List<CatModelItem>>, t: Throwable) {
            t.printStackTrace()
         }

    })


    Scaffold(topBar={ AppBar()}) {
        CatList(cats = catModelList)
        
    }
}
@Composable
fun AppBar(){
    TopAppBar(contentPadding = PaddingValues(10.dp),) {
        Text(text = "Retrofit Compose", fontSize = 26.sp)
    }
}

@Composable
fun CatList(cats:List<CatModelItem>){
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(cats){cat->
            CatRow(cat = cat)

        }

    }

}

@Composable
fun CatRow(cat :CatModelItem){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.surface)) {

        Text(text = cat.name, style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(2.dp), fontWeight = FontWeight.Bold,)

        Text(text = cat.description,modifier = Modifier.padding(2.dp), color = Color.Blue)
        
    }
    

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CatRetrofitJetPackTheme {
        MainScreen()
    }
}