package com.example.demo.CircuitBreaker;

import java.util.concurrent.atomic.AtomicInteger;

public class SimpleCircuitBreaker<T> {
    private enum State { CLOSED, OPEN, HALF_OPEN }

    private State state;
    private final int failureThreshold;
    private final int timeout;
    private AtomicInteger failureCount;
    private long lastFailureTime;

    public SimpleCircuitBreaker(int failureThreshold, int timeout) {
        this.state = State.CLOSED;
        this.failureThreshold = failureThreshold;
        this.timeout = timeout;
        this.failureCount = new AtomicInteger(0);
    }

    // Hàm fallback mặc định
    private T defaultFallback(String message) {
        // Logic fallback mặc định
        return (T) ("Fallback message for: " + message);
    }

    public T call(Supplier<T> supplier, String message) {
        switch (state) {
            case OPEN:
                if (System.currentTimeMillis() - lastFailureTime > timeout) {
                    state = State.HALF_OPEN; // chuyển sang trạng thái Half-Open
                } else {
                    return defaultFallback(message); // gọi hàm fallback mặc định
                }
                break;

            case HALF_OPEN:
                try {
                    T result = supplier.get();
                    if (result != null) {
                        state = State.CLOSED; // Nếu thành công, quay về Closed
                        failureCount.set(0);
                    } else {
                        state = State.OPEN; // Nếu thất bại, quay về Open
                        lastFailureTime = System.currentTimeMillis();
                        failureCount.incrementAndGet();
                    }
                    return result != null ? result : defaultFallback(message);
                } catch (Exception e) {
                    state = State.OPEN; // Nếu có lỗi xảy ra, chuyển sang Open
                    lastFailureTime = System.currentTimeMillis();
                    failureCount.incrementAndGet();
                    return defaultFallback(message); // Gọi hàm fallback mặc định
                }

            case CLOSED:
                try {
                    T success = supplier.get();
                    if (success == null) {
                        failureCount.incrementAndGet();
                        if (failureCount.get() >= failureThreshold) {
                            state = State.OPEN; // Chuyển sang Open nếu vượt ngưỡng
                            lastFailureTime = System.currentTimeMillis();
                        }
                    }
                    return success != null ? success : defaultFallback(message);
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                    if (failureCount.get() >= failureThreshold) {
                        state = State.OPEN; // Chuyển sang Open nếu vượt ngưỡng
                        lastFailureTime = System.currentTimeMillis();
                    }
                    return defaultFallback(message); // Gọi hàm fallback mặc định
                }
        }
        return defaultFallback(message); // Gọi hàm fallback nếu không vào được trạng thái nào
    }
}