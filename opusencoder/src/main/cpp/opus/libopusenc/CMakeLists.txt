# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.1)
project(libopusenc C)

# Creates the library, adds the needed sources.
add_library(opusenc
        src/ogg_packer.c
        src/opus_header.c
        src/opusenc.c
        src/picture.c
        src/resample.c
        src/unicode_support.c)

# Adds needed build flags.
target_compile_definitions(opusenc PRIVATE
        RANDOM_PREFIX=libopusenc
        OUTSIDE_SPEEX
        FLOATING_POINT
        PACKAGE_VERSION="${VERSION}"
        PACKAGE_NAME="libopusenc"
        OPE_BUILD)

# Adds includes
target_include_directories(
        opusenc
        PUBLIC ./include ../libopus/include
)
