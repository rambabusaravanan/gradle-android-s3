# Android S3 Plugin

Upload the apk of all the build variants to S3 and maintain all flavours of every version.

Plugin Url : https://plugins.gradle.org/plugin/com.github.rambabusaravanan.android-s3

## Step 1

Add the following to project's root `build.gradle` 

```
buildscript {
    repositories {
        maven {
            url "https://plugins.gradle.org/m2/"
        }
    }
    dependencies {
        classpath "gradle.plugin.com.rambabusaravanan.gradle:android-s3:1.0.0"
    }
}
```
## Step 2

Add the following to app's `build.gradle` below `android` block or to the bottom most

```
apply plugin: 'com.github.rambabusaravanan.android-s3'

s3 {
    bucketName = "apkbuilder"
    keyPath = "path/to/key"                 // (Optional) Default: "com.packagename/versionname"
    uploadPath = "app/build/outputs/apk"    // (Optional) Default: "$target.buildDir/outputs/apk"
}

uploadApk {
    dependsOn assemble
}
```

## Important Notes

Currently this works on gradle 4.1 and needs to be down compiled.
Make sure you have gradle version 4.1 or above and trigger the upload by the command `$ gradle uploadApk`. This will not work if run by the default gradle wrapper by android studio which is v2.3.3

## Sample Log 

```
andro@thiyagab:~/workspace/gradle-plugin/android-example$ gradle --version

------------------------------------------------------------
Gradle 4.1
------------------------------------------------------------

Build time:   2017-08-07 14:38:48 UTC
Revision:     941559e020f6c357ebb08d5c67acdb858a3defc2

Groovy:       2.4.11
Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
JVM:          1.8.0_131 (Oracle Corporation 25.131-b11)
OS:           Linux 4.4.0-75-generic amd64

andro@thiyagab:~/workspace/gradle-plugin/android-example$ gradle uploadApk

> Configure project :app
NDK is missing a "platforms" directory.
If you are using NDK, verify the ndk.dir is set to a valid NDK directory.  It is currently set to /home/andro/Android/Sdk/ndk-bundle.
If you are not using NDK, unset the NDK variable from ANDROID_NDK_HOME or local.properties to remove this warning.

The setTestClassesDir(File) method has been deprecated and is scheduled to be removed in Gradle 5.0. Please use the setTestClassesDirs(FileCollection) method instead.
The getTestClassesDir() method has been deprecated and is scheduled to be removed in Gradle 5.0. Please use the getTestClassesDirs() method instead.
The ConfigurableReport.setDestination(Object) method has been deprecated and is scheduled to be removed in Gradle 5.0. Please use the method ConfigurableReport.setDestination(File) instead.

> Task :app:uploadApk
Upload src : /home/andro/workspace/gradle-plugin/android-example/app/build/outputs/apk
Upload des : s3://apkbuilder/com.rambabusaravanan.gradlepluginconsumer/1.0
Upload finished ..


BUILD SUCCESSFUL in 26s
53 actionable tasks: 53 executed
```
