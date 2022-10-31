package io.github.wj0410.core.tools.restful.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.wj0410.core.tools.restful.annotation.Keyword;
import io.github.wj0410.core.tools.restful.annotation.Query;
import io.github.wj0410.core.tools.restful.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 构建查询Wrapper实体
 * 配合自定义的Query注解使用
 *
 * @author wangjie
 * @version 1.0
 * date 2021年03月31日14时51分
 */
public class QueryUtil {
    //不需要值
    static List<Keyword> notNeedParam = new ArrayList<>();

    static {
        notNeedParam.add(Keyword.is_null);
        notNeedParam.add(Keyword.is_not_null);
        notNeedParam.add(Keyword.order_asc);
        notNeedParam.add(Keyword.order_desc);
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    public static QueryWrapper buildWrapper(Object query) {
        if (query == null) {
            throw new ServiceException("query cannot be null!");
        }
        Class<?> clazz = query.getClass();
        //获取类所有属性
        List<Field> fields = getFields(clazz, new ArrayList<>());
        QueryWrapper queryWrapper = new QueryWrapper();
        for (Field field : fields) {
            //获取属性上的 @Query 注解
            Query annotation = field.getAnnotation(Query.class);
            try {
                field.setAccessible(true);//设置属性可以访问
                if (annotation != null && (field.get(query) != null
                        || notNeedParam.contains(annotation.value()))) {
                    //获取注解配置的操作类型值
                    Keyword keyword = annotation.value();
                    String column = annotation.column();
                    if (StringUtils.isBlank(column)) {
                        column = humpToLine(field.getName());
                    }
                    switch (keyword) {
                        case eq:
                            queryWrapper.eq(column, field.get(query));
                            break;
                        case like:
                            queryWrapper.like(column, field.get(query));
                            break;
                        case left_like:
                            queryWrapper.likeLeft(column, field.get(query));
                            break;
                        case right_like:
                            queryWrapper.likeRight(column, field.get(query));
                            break;
                        case gt:
                            queryWrapper.gt(column, field.get(query));
                            break;
                        case lt:
                            queryWrapper.lt(column, field.get(query));
                            break;
                        case ge:
                            queryWrapper.ge(column, field.get(query));
                            break;
                        case le:
                            queryWrapper.le(column, field.get(query));
                            break;
                        case not_in:
                            queryWrapper.notIn(column, field.get(query));
                            break;
                        case is_null:
                            queryWrapper.isNull(column);
                            break;
                        case is_not_null:
                            queryWrapper.isNotNull(column);
                            break;
                        case order_asc:
                            queryWrapper.orderByAsc(column);
                            break;
                        case order_desc:
                            queryWrapper.orderByDesc(column);
                            break;
                    }
                }
            } catch (IllegalAccessException e) {
                throw new ServiceException("Unknown error in build wrapper , field.getName():" + e);
            }
        }
        return queryWrapper;
    }

    private static List<Field> getFields(Class clazz, List<Field> fields) {
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getFields(superclass, fields);
        } else {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        }
        return fields;
    }

    /**
     * 下划线转驼峰
     */
    private static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    private static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}