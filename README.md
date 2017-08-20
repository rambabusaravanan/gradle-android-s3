# Android S3 Uploader

Upload the apk of all the build variants to S3 and maintain all flavours of every version.

Plugin Url : [https://plugins.gradle.org/plugin/com.rambabusaravanan.android-s3](https://plugins.gradle.org/plugin/com.rambabusaravanan.android-s3)

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
    classpath "gradle.plugin.com.rambabusaravanan.gradle:android-s3:1.0.2"
  }
}

```
## Step 2

Add the following to app's `build.gradle` below `android` block or to the bottom most

```
apply plugin: "com.rambabusaravanan.android-s3"

s3 {
    accessKey = 'AKIA****************'
    secretKey = 'TPSi************************************'
    bucketName = "apkbuilder"

    keyPath = "path/to/key"                 // (Optional) Default: "com.packagename/versionname"
    uploadPath = "app/build/outputs/apk"    // (Optional) Default: "$target.buildDir/outputs/apk"
}

// (Optional but recommended)
uploadApk {
    dependsOn assemble
}
```

## Execution

```
$ ./gradlew uploadApk  # or $ ./gradlew uApk
```

## Sample Log 

```
andro@thiyagab:~/workspace/gradle-plugin/android-example$ ./gradlew uploadApk
...
...
...
> Task :app:uploadApk
Upload src : /home/andro/workspace/gradle-plugin/android-example/app/build/outputs/apk
Upload des : s3://apkbuilder/com.rambabusaravanan.gradlepluginconsumer/1.0
Upload finished ..

BUILD SUCCESSFUL

Total time: 6.548 secs
```
