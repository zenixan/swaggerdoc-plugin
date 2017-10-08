**swaggerdoc-plugin** is a JavaDoc plugin to generate a Swagger resource listing.

## Requirements

* Maven 3.0 or later

## Setup

Add the following plugin configuration to the `plugins` block in the POM:
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-javadoc-plugin</artifactId>
    <version>2.10.4</version>

    <configuration>
        <doclet>org.eu.fuzzy.swagger.javadoc.SwaggerDoclet</doclet>
        <docletArtifact>
            <groupId>org.eu.fuzzy</groupId>
            <artifactId>swaggerdoc-plugin</artifactId>
            <version>0.1.0</version>
        </docletArtifact>
        <useStandardDocletOptions>false</useStandardDocletOptions>
    </configuration>
</plugin>
```

## Settings

TODO

## License

This project is licensed under the MIT License.

