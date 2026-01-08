package com.wyk.buildsrc

import org.gradle.api.Project
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

fun String.escaped() = "\"$this\""

/** @see <a href="https://stackoverflow.com/a/60474096/9846834">Source code</a>*/
fun getProperty(project: Project, key: String, file: String = "local.properties"): String {
    val properties = Properties()
    val localProperties = project.rootProject.file(file)
    if (!localProperties.isFile) error("File a $file not found")

    InputStreamReader(
        FileInputStream(localProperties),
        Charsets.UTF_8
    ).use(properties::load)

    return properties.getProperty(key)
}