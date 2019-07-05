package ko.alex.androidmonetisationtutorial.util

import android.app.Activity
import com.android.billingclient.api.*

class BillingAgent (val activity: Activity, val callback: BillingCallback): PurchasesUpdatedListener {

    private var billingClient = BillingClient.newBuilder(activity).setListener(this).enablePendingPurchases().build()
    private val productsSKUList = listOf("country_view")
    private val productsList = arrayListOf<SkuDetails>()

    init{
        billingClient.startConnection(object: BillingClientStateListener{
            override fun onBillingServiceDisconnected() {

            }

            override fun onBillingSetupFinished(billingResult: BillingResult?) {
                if(billingResult?.responseCode == BillingClient.BillingResponseCode.OK){
                    getAvailableProducts()
                }
            }

        })
    }

    fun onDestroy(){
        billingClient.endConnection()
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {
        //check whether user has purchased product
        checkProduct(billingResult, purchases)
    }

    fun checkProduct(billingResult: BillingResult?, purchases: MutableList<Purchase>?){
        //if there is a purchase, consume it
        purchases?.let{
            var token: String? = null
            if(billingResult?.responseCode == BillingClient.BillingResponseCode.OK &&
                    purchases.size > 0) {
                token = purchases.get(0).purchaseToken
            } else if (billingResult?.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){
                val purchasesList = billingClient.queryPurchases(BillingClient.SkuType.INAPP).purchasesList
                if(purchasesList.size > 0){
                    token = purchasesList[0].purchaseToken
                }
            }

            token?.let{
                val params = ConsumeParams
                    .newBuilder()
                    .setPurchaseToken(token)
                    .setDeveloperPayload("Token consumed")
                    .build()
                billingClient.consumeAsync(params){billingResult, purchaseToken ->
                    if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                        callback.onTokenConsumed()
                    }
                }
            }

        }
    }

    fun getAvailableProducts(){
        if(billingClient.isReady){
            val params = SkuDetailsParams
                .newBuilder()
                .setSkusList(productsSKUList)
                .setType(BillingClient.SkuType.INAPP)
                .build()
            billingClient.querySkuDetailsAsync(params){billingResult, skuDetailsList ->
                if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                    productsList.clear()
                    productsList.addAll(skuDetailsList)
                }
            }
        }
    }

    fun purchaseView(){
        if(productsList.size > 0){
            val billingFlowParams = BillingFlowParams
                .newBuilder()
                .setSkuDetails(productsList[0])
                .build()
            billingClient.launchBillingFlow(activity, billingFlowParams)
        }
    }

}