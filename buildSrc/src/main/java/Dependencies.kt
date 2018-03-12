/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */
object Versions {
    val vSdkMin = 16
    val vSdkTarget = 26
    val vSdkCompile = 26
    val vVersionCode = 1
    val vVersionName = "1.0"
    val vBuildTool = "26.0.3"

    val vSupportLib = "26.1.0"
    val vRxjava2 = "2.1.10"
    val vRxAndroid = "2.0.2"
}

object Deps {
    val depSupportAppCompat = "com.android.support:appcompat-v7:${Versions.vSupportLib}"
    val depSupportFragment = "com.android.support:support-fragment:${Versions.vSupportLib}"
    val depSupportAnnotation = "com.android.support:support-annotations:${Versions.vSupportLib}"
    val depSupportDesign = "com.android.support:design:${Versions.vSupportLib}"
    val depSupportCardView = "com.android.support:cardview-v7:${Versions.vSupportLib}"
    val depConstraintLayout = "com.android.support.constraint:constraint-layout:1.0.2"

    val depConductor = "com.bluelinelabs:conductor:2.1.3"
    val depSparkButton = "com.github.varunest:sparkbutton:1.0.5"

    val depRxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.vRxAndroid}"
    val depRxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.vRxjava2}"
    val depRxRelays = "com.jakewharton.rxrelay2:rxrelay:2.0.0"

    val depDaggerCompiler = "com.google.dagger:dagger-compiler:2.10"
    val depDagger = "com.google.dagger:dagger:2.10"


    val depJavax = "javax.inject:javax.inject:1"
    val depJavaPoet = "com.squareup:javapoet:1.8.0"
    val depAutoCommon = "com.google.auto:auto-common:0.8"
    val depAutoService = "com.google.auto.service:auto-service:1.0-rc2"

    val depTestJunit = "junit:junit:4.12"
    val depTestTruth = "com.google.truth:truth:0.35"
    val depTestMokito = "org.mockito:mockito-core:2.7.22"
    val depTestRunner = "com.android.support.test:runner:0.5"
    val depTestMokitoAndroid = "org.mockito:mockito-android:2.7.22"
    val depTestCompileTesting = "com.google.testing.compile:compile-testing:0.10"
    val depTestEspressoCore = "com.android.support.test.espresso:espresso-core:2.2.2"
}
