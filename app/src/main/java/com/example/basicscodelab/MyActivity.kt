package com.example.basicscodelab

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp(Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {  //app主入口
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
    //组合外储存的变量，控制初始界面显示与否，rememberSaveable可在各种状况保存状态
    Surface(modifier) { //modifier每次调用恢复默认设置，提高调用的重复性
        if (shouldShowOnboarding) { //初始运行为真时只显示一次
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false }) //传递形参为函数，功能在点击时改变状态为false
        } else {
            Greetings() //调用功能函数
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
   // names: List<String> = listOf("World", "Compose") //List列表变量
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) //设置垂直内间距
    {
        items(items = names) { name -> //items属性设为列表names,name为每项变量
        //for (name in names) { //轮询列表变量
            Greeting(name = name)  //重复调用的函数
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) } //=每次执行都赋值，by不用每次执行
    //val extraPadding by animateDpAsState(
    //    if (expanded) 48.dp else 0.dp,
    //        animationSpec = spring(
    //            dampingRatio = Spring.DampingRatioMediumBouncy,
    //            stiffness = Spring.StiffnessLow
     //       )
   // )
    // val extraPadding = if (expanded.value) 48.dp else 0.dp //设为函数计算结果的变量
    //Surface(
    //    color = MaterialTheme.colorScheme.primary, //包围组合项的背景色
    //    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp) //垂直、水平内间距
    //) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(  //在Row中实现弹性变化
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(modifier = Modifier
            .weight(1f)//weight让元素填满所有可用空间,会推开其他没有权重的元素
            .padding(12.dp)
                //.padding(bottom = extraPadding.coerceAtLeast(0.dp)) //设置弹簧效果
                //.padding(bottom = extraPadding) //设置改变下间距，可观察元素弹性
        ) {
            Text(text = "Hello, ")
            Text(text = name, style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold)
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) { //图标按钮
            Icon(   //Filled.ExpandLess Filled.ExpandMore 三角箭头向上，向下
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                        stringResource(R.string.show_less)
                    } else {
                        stringResource(R.string.show_more)
                    }
            )
        }
            //ElevatedButton( //remember会记住各个不同组合位置的变量
           //     onClick = { expanded = !expanded }//用by 不用value
                //onClick = { expanded.value = !expanded.value }
           // ) {
            //    Text(if (expanded) "Show less" else "Show more")
            //}
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit, //无形参不返回函数
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, //列的垂直居中
        horizontalAlignment = Alignment.CenterHorizontally //列的水平居中
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTheme {
        OnboardingScreen(onContinueClicked = {}) // Do nothing on click.
    }
}


@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodelabTheme {
        MyApp(Modifier.fillMaxSize())
    }
}