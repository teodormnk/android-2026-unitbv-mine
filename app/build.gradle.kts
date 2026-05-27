import java.util.Properties

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.compose)
	id("com.google.gms.google-services")
	id("com.google.devtools.ksp")
}

android {
	namespace = "cst.unitbvfmi2026"
	compileSdk {
		version = release(36) {
			minorApiLevel = 1
		}
	}

	defaultConfig {
		applicationId = "cst.unitbvfmi2026"
		minSdk = 26
		targetSdk = 36
		versionCode = 1
		versionName = "1.0.2"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		//load the values from .properties file
		val keystoreFile = project.rootProject.file("local.properties")
		val properties = Properties()
		properties.load(keystoreFile.inputStream())

		//return empty key in case something goes wrong
		val apiKey = properties.getProperty("reqres_key") ?: ""
		buildConfigField(
			type = "String",
			name = "reqres_key",
			value = apiKey
		)
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	buildFeatures {
		compose = true
		buildConfig = true //face un fisier singur cu propr proiect, propr precum: ver aplic, ver code, env in care rulam + config lui (debug / release)
	}
}

dependencies {
	implementation(libs.androidx.room.runtime)
	ksp(libs.androidx.room.compiler)
	implementation(libs.androidx.room.ktx)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.activity)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.compose.ui.test.junit4)
	debugImplementation(libs.androidx.compose.ui.tooling)
	debugImplementation(libs.androidx.compose.ui.test.manifest)
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.analytics)
	implementation(libs.firebase.auth)
	implementation(libs.retrofit)
	implementation(libs.retrofit.converter.gson)
	implementation(libs.okhttp.logging.interceptor)
	implementation(libs.kotlinx.coroutines.android)
	implementation(libs.coil.compose)
	implementation(libs.androidx.datastore.preferences)
}