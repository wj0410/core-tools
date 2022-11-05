package io.github.wj0410.core.tools.restful.util;

import io.github.wj0410.core.tools.restful.annotation.NotBlank;
import io.github.wj0410.core.tools.restful.annotation.Operation;
import io.github.wj0410.core.tools.restful.annotation.Unique;
import io.github.wj0410.core.tools.restful.dto.BaseDTO;
import io.github.wj0410.core.tools.restful.exception.CKSException;
import io.github.wj0410.core.tools.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 参数非空校验
 *
 * @author wangjie
 * @version 1.0
 * date 2021年03月31日14时51分
 */
public class ValidUtil {

    /**
     * 新增业务场景下 字段非空校验
     *
     * @param entity 参数实体 一般为ORM对象
     */
    public static void validSave(Object entity) {
        valid(entity, Operation.SAVE);
    }

    /**
     * 修改业务场景下 字段非空校验
     *
     * @param entity 参数实体 一般为ORM对象
     */
    public static void validUpdate(Object entity) {
        valid(entity, Operation.UPDATE);
    }

    /**
     * 删除业务场景下 字段非空校验
     *
     * @param entity 参数实体 一般为ORM对象
     */
    public static void validDelete(Object entity) {
        valid(entity, Operation.DELETE);
    }

    /**
     * 删除业务场景下 字段非空校验
     *
     * @param entity 参数实体 一般为ORM对象
     * @param custom custom
     */
    public static void validCustom(Object entity, String custom) {
        valid(entity, custom);
    }

    private static void valid(Object entity, Operation oType) {
        if (entity == null) {
            throw new CKSException("entity cannot be null!");
        }
        Class<?> clazz = entity.getClass();
        //获取类所有属性
        List<Field> fields = getFields(clazz, new ArrayList<>());
        List<String> errorMsgs = new ArrayList<String>();
        for (Field field : fields) {
            //获取属性上的 @NotBlank 注解
            NotBlank notBlank = field.getAnnotation(NotBlank.class);
            if (notBlank != null) {
                field.setAccessible(true);//设置属性可以访问
                try {
                    //获取注解配置的操作类型值
                    Operation[] operation = notBlank.operation();
                    for (Operation operationType : operation) {
                        if (String.valueOf(operationType.name()).equals(oType.toString())) {
                            Object obj = field.get(entity);//获取值
                            if (obj == null) {
                                errorMsgs.add(field.getName());
                            } else {
                                //如果值是字符串类型
                                if ("class java.lang.String".equals(field.getGenericType().toString())
                                        && StringUtils.isBlank((String) obj)) {
                                    errorMsgs.add(field.getName());
                                }
                                //这里类型判断非空待补充...
                            }
                            break;
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new CKSException("Unknown error in parameter validation, field.getName():" + e);
                }
            }

        }
        if (errorMsgs.size() > 0) {
            throw new CKSException(String.format(DPMessage.NOT_EMPTY, StringUtils.join(errorMsgs, ",")));
        }
    }

    private static void valid(Object entity, String custom) {
        if (entity == null) {
            throw new CKSException("entity cannot be null!");
        }
        Class<?> clazz = entity.getClass();
        //获取类所有属性
        List<Field> fields = getFields(clazz, new ArrayList<>());
        List<String> errorMsgs = new ArrayList<String>();
        for (Field field : fields) {
            //获取属性上的 @NotBlank 注解
            NotBlank notBlank = field.getAnnotation(NotBlank.class);
            if (notBlank != null) {
                field.setAccessible(true);//设置属性可以访问
                try {
                    //获取注解配置的操作类型值
                    String[] csms = notBlank.custom();
                    for (String csm : csms) {
                        if (csm.equals(custom)) {
                            Object obj = field.get(entity);//获取值
                            if (obj == null) {
                                errorMsgs.add(field.getName());
                            } else {
                                //如果值是字符串类型
                                if ("class java.lang.String".equals(field.getGenericType().toString())
                                        && StringUtils.isBlank((String) obj)) {
                                    errorMsgs.add(field.getName());
                                }
                                //这里类型判断非空待补充...
                            }
                            break;
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new CKSException("Unknown error in parameter validation, field.getName():" + e);
                }
            }
        }
        if (errorMsgs.size() > 0) {
            throw new CKSException(String.format(DPMessage.NOT_EMPTY, StringUtils.join(errorMsgs, ",")));
        }
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

    public static <T extends BaseDTO> List<Object[]> uniqueColumn(Object entity) {
        if (entity == null) {
            throw new CKSException("entity cannot be null!");
        }
        Class<?> clazz = entity.getClass();
        //获取类所有属性
        List<Field> fields = getFields(clazz, new ArrayList<>());
        List<Object[]> objects = new ArrayList<>();
        for (Field field : fields) {
            //获取属性上的 @NotBlank 注解
            Unique unique = field.getAnnotation(Unique.class);
            if (unique != null) {
                field.setAccessible(true);//设置属性可以访问
                String tip = unique.tip();
                try {
                    Object obj = field.get(entity);//获取值
                    Object[] o = new Object[3];
                    o[0] = CommonUtil.humpToLine(field.getName());
                    o[1] = obj;
                    if(StringUtils.isNotEmpty(tip)){
                        o[2] = tip;
                    }
                    objects.add(o);
                } catch (IllegalAccessException e) {
                    throw new CKSException("Unknown error in parameter validation, field.getName():" + e);
                }
            }
        }
        return objects;
    }
}