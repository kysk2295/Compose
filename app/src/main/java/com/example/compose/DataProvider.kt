package com.example.compose

data class RandomUser(
    val name: String ="고윤서",
    val recentmsg:String="안녕하세요 책 좀 빌릴 수 있을까요?",
    val ProfileImg:String="https://randomuser.me/api/portraits/women/72.jpg",
    val timestamp:String="오전 4:26"

)
object DataProvider {
    val userList=List<RandomUser>(20){RandomUser()}
}