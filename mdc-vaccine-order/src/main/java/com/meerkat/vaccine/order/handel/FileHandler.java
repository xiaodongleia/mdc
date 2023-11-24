package com.meerkat.vaccine.order.handel;


import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息
 *
 * @author zhujx
 * @date 2021/03/09 23:34
 */
public class FileHandler {

    private static final ThreadLocal<MultipartFile> HANDLE = new ThreadLocal<>();

    public static void set(MultipartFile file) {
        HANDLE.set(file);
    }

    public static MultipartFile get() {
        return HANDLE.get();
    }

    public static void remove() {
        HANDLE.remove();
    }
}