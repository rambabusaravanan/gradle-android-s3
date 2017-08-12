package com.rambabusaravanan.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin

class AndroidS3Plugin implements Plugin<Project> {

    void apply(Project target) {
        target.task('hello') {
            doLast {
                println "This is 'hello' task from 'AndroidS3Plugin' plugin"
            }
        }
    }

}