package com.rambabusaravanan.gradle

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.transfer.MultipleFileUpload
import com.amazonaws.services.s3.transfer.TransferManager
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class UploadTask extends DefaultTask {
    String accessKey, secretKey;
    String bucketName, keyPrefix, uploadPath;

    void initialize() {
        accessKey = project.s3.accessKey;
        secretKey = project.s3.secretKey;
        bucketName = project.s3.bucketName;
        keyPrefix = project.s3.keyPrefix;
        uploadPath = project.s3.uploadPath;

        if (!bucketName?.trim())
            bucketName = ''
        if (!keyPrefix?.trim())
            keyPrefix = project.android.defaultConfig.applicationId + "/" + project.android.defaultConfig.versionName
        if (!uploadPath?.trim())
            uploadPath = "$project.buildDir/outputs/apk"
    }

    static BasicAWSCredentials getCredentials(String accessKey, String secretKey) {
        new BasicAWSCredentials(accessKey, secretKey);
    }

    static void upload(AWSCredentials credentials, String bucketName, String keyPrefix, String uploadPath) {
        TransferManager manager = new TransferManager(credentials);
        try {
            File file = new File(uploadPath);
            boolean recursive = file.isDirectory()
            println("Upload src : " + file.absolutePath)
            println("Upload des : s3://$bucketName/$keyPrefix")

            MultipleFileUpload upload = manager.uploadDirectory(bucketName, keyPrefix, file, recursive);
            upload.waitForCompletion()
            println "Upload finished .."
        } catch (AmazonServiceException e) {
            throw new GradleException("AMAZON SERVICE ERROR: " + e.getMessage())
        } catch (AmazonClientException e) {
            throw new GradleException("AMAZON CLIENT ERROR: " + e.getMessage())
        } catch (InterruptedException e) {
            throw new GradleException("TRANSFER INTERRUPTED: " + e.getMessage())
        }
        manager.shutdownNow();
    }

    @TaskAction
    void run() {
        initialize()
        AWSCredentials credentials = getCredentials(accessKey, secretKey);
        upload(credentials, bucketName, keyPrefix, uploadPath)
    }
}