# Crafter

![link](https://img.shields.io/badge/API-Spigot-blue) ![link](https://img.shields.io/badge/Version-1.17+-yellow)

Crafter is a set of reusable **utilities** to accelerate the development of **Minecraft projects** using the [Spigot](https://hub.spigotmc.org/javadocs/spigot/) API.

## Utilities

Crafter actually contains utilities for the following use cases:
- **Message handling**
  - Sending messages (string, list, text component, action bar)
  - Colors (color codes, hexadecimal) parsing
  - Placeholders parsing
  - TextComponent building (using code or from YAML files)
- **File handling**
  - Plugin's resources copy to external paths without logs
- **Database handling**
  - Retrieve database connection settings through YAML configuration files 
  - Database connection pooling (HikariCP)
  - Automatic database schemes migration (Flyway, Single script)
  - Automatic database setup (creation, connection, migration)
  - Supported drivers: SQLite, MySQL, MariaDB, PostgreSQL
- **Configuration handling**
  - Reusable methods to retrieve specific objects from a `ConfigurationSection`
  - Generic and reusable exceptions
- **Other utilities**
  - Observers
  - Validation
  - Finding the direction to a target location
  - Promise to handle async operations
  - SQL script runner
  - Business operations utilities
  - Minecraft / NMS version detection

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

To minimize the jar and relocate the sources, you can use `maven-shade-plugin`:
```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <version>3.6.0</version>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
          <configuration>
            <minimizeJar>true</minimizeJar>
            <createDependencyReducedPom>false</createDependencyReducedPom>
            <relocations>
              <relocation>
                <pattern>com.github.syr0ws.crafter</pattern>
                <shadedPattern>your.package.util</shadedPattern>
              </relocation>
            </relocations>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
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
To minimize the jar and relocate the sources, you can use the `shadow` plugin:
```groovy
plugins {
  id 'com.github.johnrengelman.shadow' version '8.3.5'
}

shadowJar {
  minimize()
  relocate 'com.github.syr0ws.crafter', 'your.package.util'
}

tasks.build {
  dependsOn shadowJar
}
```
