Skip to content
Product
Solutions
Open Source
Pricing
Search
Sign in
Sign up
dpa99c
/
cordova-plugin-hello-c
Public
forked from clement360/Cordova-Hello-JNI-Plugin
Code
Issues
1
Pull requests
Actions
Security
Insights
cordova-plugin-hello-c/src/android/jni/Android.mk
@dpa99c
dpa99c Fix build for cordova-android@8.1.0
…
Latest commit eb9bac4 on Jun 25, 2020
 History
 3 contributors
@dpa99c@angelskieglazki@clement360
55 lines (39 sloc)  1.25 KB
 

# Android Makefile

APP_PLATFORM := android-21
APP_ABI := armeabi-v7a arm64-v8a x86 x86_64

PATH_SEP := /

LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

#traverse all the directory and subdirectory
define walk
  $(wildcard $(1)) $(foreach e, $(wildcard $(1)$(PATH_SEP)*), $(call walk, $(e)))
endef

SRC_LIST :=
INCLUDE_LIST :=


################################
# prepare shared lib

LOCAL_MODULE := helloc

# JNI interface files
INCLUDE_LIST += $(LOCAL_PATH)
SRC_LIST += $(wildcard $(LOCAL_PATH)/*.c)

# Cross-platform common files
INCLUDE_LIST += $(LOCAL_PATH)/../../common/
ifeq ($(OS),Windows_NT)
	INCLUDE_LIST += ${shell dir $(LOCAL_PATH)\..\..\common\ /ad /b /s}
else
	INCLUDE_LIST += ${shell find $(LOCAL_PATH)/../../common/ -type d}
endif
SRC_LIST += $(filter %.c, $(call walk, $(LOCAL_PATH)/../../common))


$(info LOCAL_PATH:$(LOCAL_PATH))
$(info SRC_LIST:$(SRC_LIST))
$(info INCLUDE_LIST:$(INCLUDE_LIST))

LOCAL_C_INCLUDES := $(INCLUDE_LIST)
LOCAL_SRC_FILES := $(SRC_LIST:$(LOCAL_PATH)/%=%)

LOCAL_CFLAGS += -std=c99
LOCAL_CPPFLAGS := -fblocks
TARGET_PLATFORM := android-27
LOCAL_DISABLE_FATAL_LINKER_WARNINGS := true
LOCAL_LDLIBS += -Wl,--no-warn-shared-textrel
LOCAL_LDFLAGS += -fuse-ld=gold

include $(BUILD_SHARED_LIBRARY)

################################
Footer
© 2023 GitHub, Inc.
Footer navigation
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
cordova-plugin-hello-c/Android.mk at master · dpa99c/cordova-plugin-hello-c · GitHub 