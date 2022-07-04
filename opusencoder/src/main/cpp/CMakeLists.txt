# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.4.1)
set(CMAKE_CXX_STANDARD 14)
project(OpusEncoder CXX)

set(distribution_OPUS_DIR ${CMAKE_SOURCE_DIR}/opus)

set(LIBOPUS_DIR ${distribution_OPUS_DIR}/libopus)
set(LIBOPUSENC_DIR ${distribution_OPUS_DIR}/libopusenc)

if (USE_PREBUILTS)
    # Creates and names a library, sets it as either STATIC
    # or SHARED, and provides the relative paths to its source code.
    # You can define multiple libraries, and CMake builds them for you.
    # Gradle automatically packages shared libraries with your APK.
    message("Using prebuilt libraries instead of building from sources.")
    add_library(opus SHARED IMPORTED)
    set_target_properties(opus PROPERTIES IMPORTED_LOCATION
            ${distribution_OPUS_DIR}/prebuilt_libs/${ANDROID_ABI}/libopus.so)

    add_library(opusenc SHARED IMPORTED)
    set_target_properties(opusenc PROPERTIES IMPORTED_LOCATION
        ${distribution_OPUS_DIR}/prebuilt_libs/${ANDROID_ABI}/libopusenc.so)
else()
    # Builds libopus and libopusenc libraries as dependencies.
    add_subdirectory(${LIBOPUS_DIR})
    add_subdirectory(${LIBOPUSENC_DIR})
endif()

# Creates the library as a shared library, adds the needed sources.
add_library(opuscodec
        SHARED
        codec/CodecOggOpus.cpp
        opuscodec.cpp)

target_include_directories(opuscodec PRIVATE
        ${LIBOPUS_DIR}/include
        ${LIBOPUSENC_DIR}/include)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
find_library(log-lib log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
target_link_libraries(opuscodec
        # Links needed Android JNI dependencies.
        android
        # Links Opus libraries.
        opusenc
        opus
        # Links the target library to the log library included in the NDK.
        ${log-lib})
