package io.github.wj0410.core.tools.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.wj0410.core.tools.util.CommonUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 新增、修改时自动更新字段
 * 需要在实体类里加上 fill = FieldFill.INSERT_UPDATE
 *
 * @author wangjie
 * @version 1.0
 * date 2021年06月02日16时02分
 */
public class DefaultMetaObjectHandler implements MetaObjectHandler {
    static List<Class> needInsertIds = new ArrayList<>();

    static {

    }

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
        setFieldValByName("isDelete", Boolean.FALSE, metaObject);
        // 允许手动插入id
        if (!needInsertIds.stream().anyMatch((item -> item.isInstance(metaObject.getOriginalObject())))) {
            setFieldValByName("id", CommonUtil.createId(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
