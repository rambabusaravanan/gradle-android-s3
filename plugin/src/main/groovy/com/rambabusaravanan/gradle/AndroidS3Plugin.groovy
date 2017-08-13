package com.rambabusaravanan.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.PropertyState


class AndroidS3Extension {
    final PropertyState<String> bucketName
    final PropertyState<String> keyPrefix
    final PropertyState<String> uploadPath

    AndroidS3Extension(Project target) {
        this.bucketName = target.property(String)
        this.keyPrefix = target.property(String)
        this.uploadPath = target.property(String)

        setBucketName('')
        setKeyPrefix(target.android.defaultConfig.applicationId + '/' + target.android.defaultConfig.versionName)
        setUploadPath("$target.buildDir/outputs/apk")
    }

    void setBucketName(String bucketName) { this.bucketName.set(bucketName) }

    void setKeyPrefix(String keyPrefix) { this.keyPrefix.set(keyPrefix) }

    void setUploadPath(String uploadPath) { this.uploadPath.set(uploadPath) }
}

class AndroidS3Plugin implements Plugin<Project> {

    void apply(Project target) {
        def extension = target.extensions.create("s3", AndroidS3Extension, target)
        target.tasks.create('uploadApk', UploadTask) {
            bucketName = extension.bucketName
            keyPrefix = extension.keyPrefix
            uploadPath = extension.uploadPath
        }
    }

}

