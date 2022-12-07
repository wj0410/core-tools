package io.github.wj0410.core.tools.mybatisplus;

/**
 * 新增、修改时自动更新字段
 * 需要在实体类里加上 fill = FieldFill.INSERT_UPDATE
 *
 * @author wangjie
 * @version 1.0
 * date 2021年06月02日16时02分
 */
public class DefaultMetaObjectHandler extends AbstractMetaObjectHandler {

    static {
    }

    @Override
    protected Long getLoginUserId() {
        return null;
    }
}
