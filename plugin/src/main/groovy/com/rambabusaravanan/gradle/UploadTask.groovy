package com.rambabusaravanan.gradle

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.transfer.MultipleFileUpload
import com.amazonaws.services.s3.transfer.TransferManager
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.specs.Spec
import org.gradle.api.tasks.TaskAction

class UploadTask extends DefaultTask {
    String bucketName, keyPrefix, uploadPath;

    void initialize() {
        bucketName = project.s3.bucketName;
        keyPrefix = project.s3.keyPrefix;
        uploadPath = project.s3.uploadPath;

        if(!bucketName?.trim())
            bucketName = ''
        if(!keyPrefix?.trim())
            keyPrefix = project.android.defaultConfig.applicationId + "/" + project.android.defaultConfig.versionName
        if(!uploadPath?.trim())
            uploadPath = "$project.buildDir/outputs/apk"
    }

    static void upload(String bucketName, String keyPrefix, String uploadPath) {
        TransferManager manager = new TransferManager();
        try {
            File file = new File(uploadPath);
            boolean recursive = file.isDirectory()
            println("Upload src : " + file.absolutePath)
            println("Upload des : s3://$bucketName/$keyPrefix")

            MultipleFileUpload upload = manager.uploadDirectory(bucketName, keyPrefix, file, recursive);
            upload.waitForCompletion()
            println "Upload finished .."
        } catch (AmazonServiceException e) {
            System.err.println("Amazon service error" + e.getErrorMessage());
            System.exit(1);
        } catch (AmazonClientException e) {
            System.err.println("Amazon client error: " + e.getMessage());
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Transfer interrupted: " + e.getMessage());
            System.exit(1);
        }
        manager.shutdownNow();
    }

    @Override
    void onlyIf(Spec<? super Task> spec) {
        println(bucketName != null || bucketName.length > 0)
        bucketName != null || bucketName.length > 0
    }

    @TaskAction
    void run() {
        initialize()
        upload(bucketName, keyPrefix, uploadPath)
    }
}