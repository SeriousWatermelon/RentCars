package cn.ac.ict.config;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * 声明so库仿真系统的 仿真方法
 *
 * @author weiman cui
 * @date 2020/5/18 9:01
 */
public interface JNAConfig extends Library {

    JNAConfig INSTANCE = (JNAConfig) Native.loadLibrary("final_24", JNAConfig.class);

    void process(int days, int seed, Pointer pointer, String info);

}
