import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget


plugins {
    kotlin("multiplatform") version "2.4.20"

}

group = "net.shadowmaster435"
version = "1.0"
val parsinatorVersion: String = providers.gradleProperty("parsinatorVersion").get()

repositories {

    mavenCentral()
    flatDir {
        dir(file("libs"))
    }
}



tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    compilerOptions {

        // For custom or experimental CLI flags (like -X flags)
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}
kotlin {
    jvm()
    val hostOs = System.getProperty("os.name")
    targets.withType<KotlinNativeTarget>().configureEach {
        for (compilation in compilations) {
            println(compilation.name)
        }
        compilations.getByName("main") {
            cinterops {
                val llvm by creating {
                    definitionFile.set(project.file("src/nativeMain/cinterop/llvm.def"))
                    includeDirs("libs")
                }
            }
        }
        binaries {
            sharedLib {
                baseName = when {
                    hostOs.startsWith("Windows") -> "libnative"
                    hostOs == "Linux" || hostOs == "Mac OS X" -> "native"
                    else -> throw GradleException("Unsupported host OS: $hostOs")
                }
            }
        }
    }
    when {
        hostOs == "Mac OS X" -> macosArm64("nativeMac")
        hostOs == "Linux" -> linuxX64("nativeLinux")
        hostOs.startsWith("Windows") -> mingwX64("nativeWindows")
        else -> throw GradleException("Unsupported host OS: $hostOs")
    }
    sourceSets {
        commonMain.dependencies {
            implementation(kotlin("stdlib"))
            implementation("net.shadowmaster43:parsinator-metadata:1.0")
            implementation(kotlin("reflect"))
            implementation("org.ow2.asm:asm:9.7")
            implementation("org.ow2.asm:asm-util:9.7")
            implementation("org.ow2.asm:asm-tree:9.7")
        }
        nativeMain.dependencies {
            implementation(kotlin("stdlib"))
            implementation(files("libs/parsinator-linux-$parsinatorVersion.klib"))
            implementation("org.jetbrains.kotlinx:kotlinx-io-okio:0.9.1")
            implementation(files("libs/llvm.klib"))

        }
        jvmMain.dependencies {
            implementation(kotlin("stdlib"))
            implementation(files("libs/parsinator-jvm-$parsinatorVersion.jar"))
        }
    }
}

tasks.wrapper {
    gradleVersion = "9.8.0"
    distributionType = Wrapper.DistributionType.ALL
}

