LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := opusenc
LOCAL_CFLAGS += -ffast-math -fsigned-char -DHAVE_CONFIG_H -DRANDOM_PREFIX=libopusenc -DOUTSIDE_SPEEX
ifeq ($(TARGET_ARCH),arm)
	LOCAL_CFLAGS += -march=armv6 -marm -mfloat-abi=softfp -mfpu=vfp
endif
LOCAL_C_INCLUDES += $(LOCAL_PATH)/include $(LOCAL_PATH)/../libopus/include

LOCAL_SRC_FILES := \
	src/ogg_packer.c \
	src/opus_header.c \
	src/opusenc.c \
	src/picture.c \
	src/resample.c \
	src/unicode_support.c

LOCAL_SHARED_LIBRARIES := opus
include $(BUILD_SHARED_LIBRARY)
