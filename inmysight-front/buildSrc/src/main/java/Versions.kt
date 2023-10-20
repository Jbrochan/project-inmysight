//version constants for the Kotlin DSL dependencies
object Versions {
    // AndroidX
    const val CORE_KTX_VERSION = "1.7.0"
    const val APPCOMPAT_VERSION = "1.5.1"
    const val RECYCLERVIEW_VERSION = "1.1.0"
    const val CONSTRAINTLAYOUT_VERSION = "2.1.4"

    // Test
    const val JUNIT_VERSION = "4.13.2"
    const val JUNIT_EXT_VERSION = "1.1.3"
    const val ESPRESSO_CORE_VERSION = "3.4.0"

    // Google
    const val MATERIAL_VERSION = "1.6.1"

    // Firebase
    const val FIREBASE_BOM_VERSION = "31.0.2"
    const val FIREBASE_FIRESTORE_VERSION = "24.4.0"
}

object AndroidXDeps {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX_VERSION}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT_VERSION}"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLERVIEW_VERSION}"
    const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINTLAYOUT_VERSION}"
}

object TestDeps {
    const val JUNIT = "junit:junit:${Versions.JUNIT_VERSION}"
    const val JUNIT_EXT = "androidx.test.ext:junit:${Versions.JUNIT_EXT_VERSION}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE_VERSION}"
}

object GoogleDeps {
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL_VERSION}"
}

object Firebase {
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM_VERSION}"
    const val FIREBASE_FIRESTORE = "com.google.firebase:firebase-firestore-ktx:${Versions.FIREBASE_FIRESTORE_VERSION}"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
}