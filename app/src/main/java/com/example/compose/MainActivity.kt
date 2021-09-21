package com.example.compose
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.theme.ComposeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                ContentView()
            }
        }
    }
}
@Composable
fun ContentView(){
    Surface(color = MaterialTheme.colors.background) {
        val navControll= rememberNavController()
        Scaffold(
            bottomBar = {MyBottomBar(navControll)}) {
            BottomBarMain(navControll)
        }
    }
}

@Composable
fun BottomBarMain(navControll: NavHostController) {
    NavHost(navControll, startDestination = Screen.Home.route ){
        composable(Screen.Chat.route){
            ChatScreen()
        }
        composable(Screen.Home.route){
            HomeScreen()
        }
        composable(Screen.Bookcase.route){
            BookcaseScreen()
        }
        composable(Screen.Profile.route){
            ProfileScreen()
        }
    }
}

@Composable
fun ChatScreen(){
    Scaffold(topBar = { MyAppBar()}) {
        RandomUserListView(randomUsers = DataProvider.userList)
    }

}

@Composable
fun HomeScreen(){
    Text(
        text = Screen.Home.title,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        fontSize = 20.sp
    )
}

@Composable
fun BookcaseScreen(){
    Text(
        text = Screen.Bookcase.title,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        fontSize = 20.sp
    )
}
@Composable
fun ProfileAppBar(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Image(painter = painterResource(id = R.drawable.ic_menu), contentDescription = null,
                contentScale = ContentScale.Fit)
            Text(text = "프로필",fontSize = 16.sp,fontWeight = FontWeight.Bold)
            Image(painter = painterResource(id = R.drawable.ic_edit), contentDescription = null,
                contentScale = ContentScale.Fit)


        }
    }

}
@Composable
fun ProfileScreen(){
    Scaffold(topBar ={ ProfileAppBar()} ) {
        Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        }
    }
}
@Composable
fun RandomUserListView(randomUsers:List<RandomUser>){
    LazyColumn(){
        items(randomUsers){
            //for문을 돌아 randomuser 클래스 하나를 넘겨준다
            RandomUserView(it)
        }
    }
}

@Composable
fun RandomUserView(randomuser:RandomUser){
    val typography:Typography= MaterialTheme.typography

    Column() {
        Row(modifier = Modifier
            .height(74.dp)
            .padding(20.dp, 10.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically){
            ProfileImg(imgUrl = randomuser.ProfileImg)
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 0.dp)){
                    Text(text = randomuser.name, style = typography.body2)
                    Text(text = randomuser.timestamp, style = typography.h2)
                }

                Text(text = randomuser.recentmsg, style = typography.h1
                    ,modifier = Modifier.padding(10.dp,2.dp))
            }
        }

        Divider(color = Color.LightGray, thickness = 0.5.dp,
        modifier = Modifier.padding(20.dp,0.dp))

    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileImg(imgUrl:String, modifier: Modifier=Modifier){
    val bitmap : MutableState<Bitmap?> = mutableStateOf(null)
    val imagemodifier=
        modifier
            .size(47.dp, 47.dp)
            .clip(RoundedCornerShape(10.dp))

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value=resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }

        })

        //비트맵이 있다면
//        if(bitmap != null)
//        {
//            Image(bitmap = bitmap!!.asImageBitmap(),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = imagemodifier)
//        }else{
//            Image(painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = imagemodifier)
//
//        }
        bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
            Image(bitmap = fetchedBitmap, contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = imagemodifier)
        } ?: Image(painter = painterResource(id = R.drawable.ic_baseline_account_circle_24),
            contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = imagemodifier)







}
@Composable
fun MyBottomBar(navControll: NavController) {
    val items = listOf(
        Screen.Chat,
        Screen.Home,
        Screen.Bookcase,
        Screen.Profile
    )

    BottomNavigation(elevation = 10.dp) {
        val navBackStackEntry by navControll.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.map {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon),
                        contentDescription = it.title
                    )
                },
                selected = currentRoute == it.route,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(alpha = 0.4f),
                onClick = {
                    navControll.navigate(it.route)
                }

            )

        }
    }



//    val selectedState = remember {
//        mutableStateOf("home")
//    }
//    BottomAppBar(
//        elevation = 10.dp
//    ) {
//        BottomNavigationItem(
//            icon = {
//                Icon(Icons.Filled.Home, "Home")
//            },
//            selected = selectedState.value=="home",
//            onClick = {
//                selectedState.value="home"
//            },
//            selectedContentColor = Color.White,
//            unselectedContentColor = Color.White.copy(alpha = 0.4f)
//        )
//
//        BottomNavigationItem(
//            icon = {
//                Icon(Icons.Filled.Menu, "Menu")
//            },
//            selected = selectedState.value=="menu",
//            onClick = {
//                selectedState.value="menu"
//            },
//            selectedContentColor = Color.White,
//            unselectedContentColor = Color.White.copy(alpha = 0.4f)
//        )
//
//
//    }
}

@Composable
fun MyAppBar() {
    TopAppBar(elevation = 10.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {
            Text(text = "채팅",fontSize = 16.sp,fontWeight = FontWeight.Bold)
            Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = null,
                contentScale = ContentScale.Fit)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        ContentView()
    }
}