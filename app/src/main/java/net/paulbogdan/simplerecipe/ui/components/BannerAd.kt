package net.paulbogdan.simplerecipe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import net.paulbogdan.simplerecipe.BuildConfig
import net.paulbogdan.simplerecipe.ui.theme.SimpleTheme

@Composable
fun BannerAd() {

    val currentWidth = LocalConfiguration.current.screenWidthDp
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = SimpleTheme.colors.pageBackground, shape = RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .sizeIn(minHeight = 60.dp)

    ) {
        CircularProgressIndicator(color = SimpleTheme.colors.green)
        AndroidView(factory = {
            AdView(it).apply {
                setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        currentWidth - 32
                    )
                )
                adUnitId = BuildConfig.BANNER_ID
                loadAd(AdRequest.Builder().build())
            }
        })
    }
}