package io.github.wj0410.core.tools.restful.util;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

/**
 * @author wangjie
 * @version 1.0
 * date 2021年08月03日09时50分
 */
public class ObjectUtil extends ObjectUtils {
    public ObjectUtil() {
    }

    public static boolean isNotEmpty(@Nullable Object obj) {
        return !isEmpty(obj);
    }
}