/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-11
 */
object Versions {
    val vSdkMin = 16
    val vSdkTarget = 26
    val vSdkCompile = 26
    val vVersionCode = 1
    val vVersionName = "1.0"

    val vSupportLib = "26.1.0"
    val vRxjava2 = "2.1.10"
    val vRxAndroid = "2.0.2"
}

object Deps {
    val depSupportLibrary = "com.android.support:appcompat-v7:${Versions.vSupportLib}"
    val depRxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.vRxjava2}"
    val depRxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.vRxAndroid}"
    val depRxRelays = "com.jakewharton.rxrelay2:rxrelay:2.0.0"
    val depJavax = "javax.inject:javax.inject:1"

    val depTestJunit = "junit:junit:4.12"
    val depTestRunner = "com.android.support.test:runner:0.5"
    val depTestEspressoCore = "com.android.support.test.espresso:espresso-core:2.2.2"
    val depTestMokito = "org.mockito:mockito-core:2.7.22"
}
