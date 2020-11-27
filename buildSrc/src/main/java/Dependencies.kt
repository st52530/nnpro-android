object Dependencies {

    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"

    // Adapter delegate.
    const val adapterDelegates =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:${Versions.adapterDelegates}"
    const val adapterDelegatesLayoutContainer =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:${Versions.adapterDelegates}"

    const val androidTestJunit = "androidx.test.ext:junit:${Versions.androidTestJunit}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"

    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    const val dexter = "com.karumi:dexter:${Versions.dexter}"

    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"

    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"

    const val httpLoggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"

    const val jUnit = "junit:junit:${Versions.jUnit}"

    // Koin.
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinCore = "org.koin:koin-core:${Versions.koin}"
    const val koinFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    const val koinGradle = "org.koin:koin-gradle-plugin:${Versions.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    // Kotlin Coroutines.
    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesPlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.kotlinCoroutines}"
    const val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"

    // Kotlin.
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val kotlinStandardLibrary = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    const val ktxCore = "androidx.core:core-ktx:${Versions.ktxCore}"

    const val materialComponents =
        "com.google.android.material:material:${Versions.materialComponents}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    // Navigation.
    const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val swipyRefresh = "com.github.omadahealth:swipy:${Versions.swipyRefresh}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
}