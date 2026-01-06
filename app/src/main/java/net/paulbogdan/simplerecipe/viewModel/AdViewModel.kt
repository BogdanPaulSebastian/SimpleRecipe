package net.paulbogdan.simplerecipe.viewModel


import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import net.paulbogdan.simplerecipe.BuildConfig
import net.paulbogdan.simplerecipe.SimpleRecipe
import javax.inject.Inject

@HiltViewModel
class AdViewModel @Inject constructor(
    private val application: SimpleRecipe,
) : BaseViewModel() {

    var interstitialAd: InterstitialAd? = null

    fun loadAd() {
        notifyIsLoading()
        val adUnitID = BuildConfig.INTERSTITIAL_ID
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(application.applicationContext, adUnitID, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(err: LoadAdError) {
                    super.onAdFailedToLoad(err)
                    Log.d("INTERSTITIAL LOAD ERROR", err.message)
                    interstitialAd = null
                    notifyFinishedLoading()
                    this@AdViewModel.hasError = true
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    interstitialAd = ad
                    notifyFinishedLoading()
                }
            })
    }

    fun showAd(activity: Activity) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    interstitialAd = null
                    loadAd()
                }

                override fun onAdFailedToShowFullScreenContent(err: AdError) {
                    super.onAdFailedToShowFullScreenContent(err)
                    interstitialAd = null
                    Log.d("INTERSTITIAL SHOW FAIL", err.message)
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }
            }

            interstitialAd?.show(activity)
        } else {
            loadAd()
        }
    }

}