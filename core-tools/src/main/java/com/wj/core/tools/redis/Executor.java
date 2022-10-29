package com.wj.core.tools.redis;

import java.util.List;

public interface Executor<T> {
    List<T> getDbList();
}
