package io.github.wj0410.core.tools.redis;

import java.util.List;

public interface Executor<T> {
    List<T> getDbList();
}
