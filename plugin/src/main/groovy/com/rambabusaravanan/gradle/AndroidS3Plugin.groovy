package com.rambabusaravanan.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidS3Extension {
    String bucketName, keyPrefix, uploadPath; 
}

class AndroidS3Plugin implements Plugin<Project> {

    void apply(Project target) {
        target.extensions.create("s3", AndroidS3Extension)
        target.tasks.create('uploadApk', UploadTask)
    }

}

