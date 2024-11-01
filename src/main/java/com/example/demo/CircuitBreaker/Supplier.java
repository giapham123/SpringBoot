package com.example.demo.CircuitBreaker;

@FunctionalInterface
public interface Supplier<T> {
    T get();
}