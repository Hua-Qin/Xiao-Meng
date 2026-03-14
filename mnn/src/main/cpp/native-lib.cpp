#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_ai_assistance_operit_mnn_MnnNative_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "MNN Native Library";
    return env->NewStringUTF(hello.c_str());
}
