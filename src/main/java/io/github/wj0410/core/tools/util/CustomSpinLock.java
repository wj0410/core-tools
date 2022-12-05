package io.github.wj0410.core.tools.util;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 自定义自旋锁
 * 使用场景：并发度不高，临界代码执行时间不长的场景。
 */
public class CustomSpinLock {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0; // 重入次数

    public void lock() {
        Thread cur = Thread.currentThread();
        if (owner.get() == cur) {
            // 重入
            count++;
            return;
        }
        // 自旋获取锁 如果 owner = 期望值null，则将 owner 修改为 cur
        while (!owner.compareAndSet(null, cur)) {
            System.out.println("自旋");
        }
    }

    public void unlock() {
        Thread cur = Thread.currentThread();
        if (owner.get() == cur) {
            if (count > 0) {
                count--;
            } else {
                owner.set(null);
            }
        }
    }
}
