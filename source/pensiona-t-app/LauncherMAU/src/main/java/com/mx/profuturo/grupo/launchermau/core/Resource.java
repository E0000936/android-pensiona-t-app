package com.mx.profuturo.grupo.launchermau.core;

public interface Resource<T> {
    void success(T data);
    void error(int code);
    void error();
}
