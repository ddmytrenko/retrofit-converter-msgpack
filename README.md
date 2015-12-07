# retrofit-converter-msgpack

A small library which helps you to use [MessagePack](http://msgpack.org/) binary serialization
format with a popular type-safe HTTP client [Retrofit](https://square.github.io/retrofit/).

## Download

You can download latest [JAR]() manually or consider to use another Maven-compatible
dependency management tools.

### Gradle

***NOTE***: Add JCenter repository first:

```groovy
    repositories {
        jcenter()
    }
```

```groovy
    compile "org.ddmytrenko.android:retrofit-converter-msgpack:2.6.12-beta2"
```

### Maven

```xml
    <dependency>
        <groupId>org.ddmytrenko.android</groupId>
        <artifactId>retrofit-converter-msgpack</artifactId>
        <version>2.6.12-beta2</version>
    </dependency>
```

This library requires at minimum Java 7 or Android SDK 15.

## Changelog

#### 2.6.12-beta2

First version of the converter based on Retrofit 2.0.0-beta2 and MsgPack 0.6.12.
