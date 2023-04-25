package de.cgi.android.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.cgi.R
import de.cgi.android.ui.theme.LocalColor

@Composable
fun DrawerHeader(modifier: Modifier, userName: String?) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopStart,
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 10.dp)
                .fillMaxWidth()
                .background(
                    LocalColor.current.darkGrey
                )
        ) {
            Text(
                text = "CGI",
                fontSize = 40.sp,
                color = LocalColor.current.cgiRed,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(R.drawable.no_profile_picutre),
                contentDescription = "Profile picture",

                modifier = Modifier
                    .size(64.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = userName ?: "", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    Box(modifier) {
        LazyColumn() {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .background(
                            if (item.selected) {
                                LocalColor.current.actionSecondary
                            } else {
                                LocalColor.current.lightGrey
                            }
                        )
                        .padding(16.dp)
                ) {
                    Icon(imageVector = item.icon, contentDescription = item.contentDescription)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.title,
                        modifier = Modifier.weight(1f),
                        style = itemTextStyle
                    )
                }
            }
        }
    }


}