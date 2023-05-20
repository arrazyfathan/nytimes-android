/**
 * Created by Ar Razy Fathan Rabbani on 09/05/23.
 */

object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Version.coreKtx}"}
    val appCompat by lazy { "androidx.appcompat:appcompat:${Version.appCompat}"}
    val material by lazy { "com.google.android.material:material:${Version.material}"}
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"}
    val jUnit by lazy { "junit:junit:${Version.jUnit}"}
    val androidJunit by lazy { "androidx.test.ext:junit:${Version.jUnit}"}
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Version.espresso}"}
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Version.retrofit}"}
    val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Version.retrofit}"}
    val retrofitAdapterRxJava2 by lazy { "com.squareup.retrofit2:adapter-rxjava2:${Version.retrofit}"}
    val logginInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Version.loggingInterceptor}"}
    val glide by lazy { "com.github.bumptech.glide:glide:${Version.glide}"}
    val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Version.navigationComponent}"}
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Version.navigationComponent}"}
    val room by lazy { "androidx.room:room-runtime:${Version.room}"}
    val roomCompiler by lazy { "androidx.room:room-compiler:${Version.room}"}
    val roomKtx by lazy { "androidx.room:room-ktx:${Version.room}"}
    val hilt by lazy { "com.google.dagger:hilt-android:${Version.hilt}"}
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Version.hilt}"}
    val activityKtx by lazy { "androidx.activity:activity-ktx:${Version.activityKtx}"}
    val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"}
    val lifecycleExtension by lazy { "androidx.lifecycle:lifecycle-extensions:${Version.lifecycleExt}"}
    val viewModelKtx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModelKtx}"}
    val liveDataKtx by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Version.liveDataKtx}"}
    val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleComponent}"}
    val coroutinesCore by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"}
    val coroutinesAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"}
    val lottie by lazy { "com.airbnb.android:lottie:${Version.lottie}"}
    val timeAgo by lazy { "com.github.marlonlom:timeago:${Version.timeAgo}"}
    val swipeRefreshLayout by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefreshLayout}"}
    val firebaseBom by lazy { "com.google.firebase:firebase-bom:${Version.firebaseBom}"}
    val crashlytics by lazy { "com.google.firebase:firebase-crashlytics-ktx"}
    val analytics by lazy { "com.google.firebase:firebase-analytics-ktx"}
    val stickyView by lazy { "com.github.amarjain07:StickyScrollView:${Version.stickyScrollView}"}
    val coil by lazy { "io.coil-kt:coil:${Version.coil}"}
    val androidBrowser by lazy { "androidx.browser:browser:${Version.androidBrowser}"}
    val timber by lazy { "com.jakewharton.timber:timber:${Version.timber}"}
}