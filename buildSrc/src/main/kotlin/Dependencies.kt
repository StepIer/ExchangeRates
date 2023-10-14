object Dependencies {

    object Compose {
        private const val version = "1.5.3"
        const val compilerVersion = version

        const val activity = "androidx.activity:activity-compose:1.8.0"
        const val ui = "androidx.compose.ui:ui:$version"
        const val graphics = "androidx.compose.ui:ui-graphics:$version"
        const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val material3 = "androidx.compose.material3:material3:1.1.2"
        const val navigation = "androidx.navigation:navigation-compose:2.7.4"

        const val debugUiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val debugUiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.12.0"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
    }

    object AndroidTest {
        const val ext = "androidx.test.ext:junit:1.1.5"
        const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
        const val ui = "androidx.compose.ui:ui-test-junit4:1.5.3"
    }
}