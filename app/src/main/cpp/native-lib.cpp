#include <jni.h>
#include <string>

extern "C" {
int bsp_main(int argc, const char *argv[]);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_dasinwong_update_MainActivity_bsPatch(JNIEnv *env, jobject instance, jstring oldApkPath_,
                                               jstring patchPath_, jstring newApkPath_) {
    const char *oldApkPath = env->GetStringUTFChars(oldApkPath_, 0);
    const char *patchPath = env->GetStringUTFChars(patchPath_, 0);
    const char *newApkPath = env->GetStringUTFChars(newApkPath_, 0);
    const char *argv[] = {"", oldApkPath, newApkPath, patchPath};
    bsp_main(4, argv);

    env->ReleaseStringUTFChars(oldApkPath_, oldApkPath);
    env->ReleaseStringUTFChars(patchPath_, patchPath);
    env->ReleaseStringUTFChars(newApkPath_, newApkPath);
}