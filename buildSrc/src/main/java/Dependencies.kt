/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */
object Versions {
    const val vSdkMin = 16
    const val vSdkTarget = 27
    const val vSdkCompile = 27
    const val vVersionCode = 1
    const val vVersionName = "1.0"
    const val vBuildTool = "27.0.3"
    const val vKotlin = "1.2.50"

    const val vSupportLib = "27.1.1"
    const val vRxjava2 = "2.1.10"
    const val vRxAndroid = "2.0.2"
}

object Deps {
    val depSupportAppCompat = "com.android.support:appcompat-v7:${Versions.vSupportLib}"
    val depSupportFragment = "com.android.support:support-fragment:${Versions.vSupportLib}"
    val depSupportAnnotation = "com.android.support:support-annotations:${Versions.vSupportLib}"
    val depSupportDesign = "com.android.support:design:${Versions.vSupportLib}"
    val depSupportCardView = "com.android.support:cardview-v7:${Versions.vSupportLib}"
    val depConstraintLayout = "com.android.support.constraint:constraint-layout:1.0.2"

    val depKotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.vKotlin}"

    val depConductor = "com.bluelinelabs:conductor:2.1.4"
    val depSparkButton = "com.github.varunest:sparkbutton:1.0.5"

    val depRxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.vRxAndroid}"
    val depRxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.vRxjava2}"
    val depRxRelays = "com.jakewharton.rxrelay2:rxrelay:2.0.0"

    val depDaggerCompiler = "com.google.dagger:dagger-compiler:2.15"
    val depDagger = "com.google.dagger:dagger:2.15"


    val depJavax = "javax.inject:javax.inject:1"
    val depJavaPoet = "com.squareup:javapoet:1.10.0"
    val depAutoCommon = "com.google.auto:auto-common:0.10"
    val depAutoService = "com.google.auto.service:auto-service:1.0-rc2"

    val depTestJunit = "junit:junit:4.12"
    val depTestTruth = "com.google.truth:truth:0.40"
    val depTestMokito = "org.mockito:mockito-core:2.17.0"
    val depTestRunner = "com.android.support.test:runner:1.0.1"
    val depTestMokitoAndroid = "org.mockito:mockito-android:2.17.0"
    val depTestCompileTesting = "com.google.testing.compile:compile-testing:0.15"
    val depTestEspressoCore = "com.android.support.test.espresso:espresso-core:3.0.1"
}
