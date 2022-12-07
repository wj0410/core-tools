package io.github.wj0410.core.tools.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.wj0410.core.tools.util.CommonUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractMetaObjectHandler implements MetaObjectHandler {
    static List<Class> needInsertIds = new ArrayList<>();

    @Override
    public void insertFill(MetaObject metaObject) {
        try{
            setFieldValByName("createBy",getLoginUserId(),metaObject);
            setFieldValByName("updateBy",getLoginUserId(),metaObject);
        }catch (Exception e){
        }
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
        try{
            setFieldValByName("updateBy",getLoginUserId(),metaObject);
        }catch (Exception e){
        }
        setFieldValByName("updateTime", new Date(), metaObject);
    }

    protected abstract Long getLoginUserId();
}
