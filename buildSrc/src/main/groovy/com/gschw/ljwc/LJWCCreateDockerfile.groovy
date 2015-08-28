package com.gschw.ljwc

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import com.bmuschko.gradle.docker.tasks.image.Dockerfile
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

/**
 * Created by hadoop on 8/27/15.
 */
class LJWCCreateDockerfile extends Dockerfile {
    @TaskAction
    void process() {
        destFile = project.file("$buildDir/docker/Dockerfile")
        from 'gschw/busybox-java'
        maintainer 'aguzhva'
        addFile "${project.name}-${project.version}.tar", "/"
        entryPoint "/${project.name}-${project.version}/bin/${project.name}","server", "/${project.name}-${project.version}/config/application.yaml"
    }
}

class GreetingTask extends DefaultTask {
    @TaskAction
    def greet() {
        println 'hello from GreetingTask'
    }
}
