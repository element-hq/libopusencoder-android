# OpusEncoder

Android wrapper for [libopusenc](https://opus-codec.org/docs/opus-tools/opusenc.html), used to create Ogg files in with Opus frames.

## How to integrate it in your application

Just add the following gradle dependency:

```groovy
implementation "im.vector:opusencoder:$version"
```

## How to build

The project is prepared to be built by using:

```
./gradlew assembleRelease 
```

If you want to provide prebuilt libraries for libopus and libopusenc, you just need to add them to `opusencoder/src/main/cpp/opus/prebuilt_libs` under folders for each supported architecture and setting `opusencoder/build.gradle` as:

```groovy
externalNativeBuild {
    cmake {
        arguments "-DUSE_PREBUILTS=true"
    }
}
```

This is disabled by default, meaning both libopus and libopusenc will be built from source.
