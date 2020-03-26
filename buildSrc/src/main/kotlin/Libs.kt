object Libs {
    private const val kotlinVersion = "1.3.70"
    private const val kotlinCoroutinesVersion = "1.3.4"
    private const val sqlDelightVersion = "1.2.2"

    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion"
    const val kotlinCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion"

    const val sqlDelightJvmDriver = "com.squareup.sqldelight:sqlite-driver:$sqlDelightVersion"
    const val sqlDelightCoroutineExt = "com.squareup.sqldelight:coroutines-extensions-jvm:$sqlDelightVersion"

    const val junit = "org.junit.jupiter:junit-jupiter:5.6.0"
}
