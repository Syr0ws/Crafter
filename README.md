# Crafter

![link](https://img.shields.io/badge/API-Spigot-blue) ![link](https://img.shields.io/badge/Version-1.17+-yellow)

Crafter is a set of reusable **utilities** to accelerate the development of **Minecraft projects** using the [Spigot](https://hub.spigotmc.org/javadocs/spigot/) API.

## Utilities

Crafter actually contains utilities for the following use cases:
- **Message handling**
  - Sending messages (string, list, text components)
  - Colors (color codes, hexadecimal) parsing
  - Placeholders parsing
  - TextComponent building (using code or from YAML files)
- **File handling**
  - Copying plugin's resources to external paths without logs
- **Other utilities**
  - Observers

## Setup

Crafter can be included in your project by using a dependency manager like **Maven** or **Gradle**.

**Maven**
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.syr0ws</groupId>
    <artifactId>Crafter</artifactId>
    <version>{VERSION}</version>
    <scope>compile</scope>
</dependency>
```

**Gradle**
```groovy

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.syr0ws:Crafter:{VERSION}'
}
```
