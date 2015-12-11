# retrofit-converter-msgpack

A small library which helps you to use [MessagePack](http://msgpack.org/) binary serialization
format with a popular type-safe HTTP client [Retrofit](https://square.github.io/retrofit/).

## Download

You can download latest JAR manually or consider to use another Maven-compatible dependency
management tools.

<div style="text-align:center">
    <a href="">
        <img
            src="https://raw.githubusercontent.com/ddmytrenko/retrofit-converter-msgpack/master/readme/images/button-download-jar.png"
            />
    </a>
</div>

***NOTE***: Bintray repository is available [here](https://bintray.com/ddmytrenko/ddmytrenko-maven/retrofit-converter-msgpack).

### Gradle

***NOTE***: Add JCenter repository first:

```groovy
    repositories {
        jcenter()
    }
```

```groovy
    compile "org.bitbucket.ddmytrenko.android:retrofit-converter-msgpack:2.6.12-beta2"
```

### Maven

```xml
    <dependency>
        <groupId>org.bitbucket.ddmytrenko.android</groupId>
        <artifactId>retrofit-converter-msgpack</artifactId>
        <version>2.6.12-beta2</version>
    </dependency>
```

This library requires at minimum Java 7 or Android SDK 15.

## Tips

### ProGuard

In case you are using ProGuard you have to add the following rules for keeping Retrofit properly:

```proguard
    -dontwarn retrofit.**
    -keep class retrofit.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions
```

## Changelog

#### 2.6.12-beta2

First version of the converter based on Retrofit 2.0.0-beta2 and MsgPack 0.6.12.
