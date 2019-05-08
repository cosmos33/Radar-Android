#include <jni.h>
#include <string>
#include <malloc.h>
#include <android/log.h>
#include <string.h>

#include <mutex>
#include <queue>
#include <list>


class Leaker{
private:
    void* leak;
public:
    Leaker() {
        leak = malloc(1024 * 1024);
    }

    virtual ~Leaker() {
        free(leak);
    }
};

std::mutex mutex;
std::list<Leaker *> queue;

extern "C"
JNIEXPORT void JNICALL
Java_com_cosmos_radar_TestMemoryAlertActivity_freeNativeMemory(JNIEnv *env, jobject instance) {

    for (int i = 0; i < 1024; ++i) {
        std::unique_lock<std::mutex> lk(mutex);
        if (!queue.empty()) {
            Leaker * leaker = queue.front();
            queue.remove(leaker);
            delete(leaker);
        }
        lk.unlock();
    }

}

extern "C"
JNIEXPORT void JNICALL
Java_com_cosmos_radar_TestMemoryAlertActivity_mallocNativeMemory(JNIEnv *env, jobject instance) {

    for (int i = 0; i < 1024; ++i) {
        std::unique_lock<std::mutex> lk(mutex);
        queue.push_back(new Leaker());
        lk.unlock();
    }

}