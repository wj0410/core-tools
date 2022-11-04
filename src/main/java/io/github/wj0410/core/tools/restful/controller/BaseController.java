package io.github.wj0410.core.tools.restful.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.wj0410.core.tools.excel.ApachePoiExcelUtil;
import io.github.wj0410.core.tools.restful.dto.BaseDTO;
import io.github.wj0410.core.tools.restful.dto.PageQuery;
import io.github.wj0410.core.tools.restful.exception.ServiceException;
import io.github.wj0410.core.tools.restful.result.R;
import io.github.wj0410.core.tools.restful.util.QueryUtil;
import io.github.wj0410.core.tools.restful.util.ValidUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 统一规范 crud 控制层抽象类
 * S: Service
 * T: 数据传输对象DTO
 *
 * @author wangjie
 * @version 1.0
 * date 2022年01月05日14时46分
 */
public abstract class BaseController<S extends IService, T extends BaseDTO, Q extends PageQuery> {

    protected S baseService;

    @Autowired(required = false)
    protected void setBaseService(S baseService) {
        this.baseService = baseService;
    }

    /**
     * 分页查询
     * 使用时重写此方法
     *
     * @param query 查询参数 extends Page
     * @return R
     */
    @GetMapping("/page")
    protected R<IPage> page(Q query) {
        return R.data(baseService.page(query.initPage(), QueryUtil.buildWrapper(query)));
    }

    /**
     * 根据查询条件查询列表
     *
     * @param query 查询参数
     * @return R
     */
    @GetMapping("/list")
    protected R list(Q query) {
        return R.data(baseService.list(QueryUtil.buildWrapper(query)));
    }

    /**
     * 根据ID查询详情
     *
     * @param id 主键ID
     * @return R
     */
    @GetMapping("/info/{id}")
    protected R info(@PathVariable String id) {
        return R.data(baseService.getById(id));
    }

    /**
     * 新增
     *
     * @param dto 实体
     * @return R
     */
    @PostMapping("/save")
    protected R<Boolean> save(@RequestBody T dto) {
        ValidUtil.validSave(dto);
        uniqueSave(dto);
        return R.status(baseService.save(dto.buildEntity()));
    }

    /**
     * 新增
     *
     * @param dtos 实体
     * @return R
     */
    @PostMapping("/saveBatch")
    protected R<Boolean> saveBatch(@RequestBody List<T> dtos) {
        List batchList = new ArrayList();
        for (T dto : dtos) {
            ValidUtil.validSave(dto);
            uniqueSave(dto);
            batchList.add(dto.buildEntity());
        }
        return R.status(CollectionUtils.isNotEmpty(batchList) ? baseService.saveBatch(batchList) : false);
    }

    /**
     * 新增方法时，数据唯一校验
     *
     * @param dto dto
     */
    protected void uniqueSave(T dto) {
        List<Object[]> objs = ValidUtil.uniqueColumn(dto);
        if (CollectionUtils.isNotEmpty(objs)) {
            QueryWrapper queryWrapper = new QueryWrapper();
            for (Object[] o : objs) {
                queryWrapper.eq(String.valueOf(o[0]), o[1]);
            }
            int count = baseService.count(queryWrapper);
            if (count > 0) {
                String errorColumns = "";
                for (int i = 0; i < objs.size(); i++) {
                    if (i == objs.size() - 1) {
                        errorColumns += objs.get(i)[0];
                    } else {
                        errorColumns += objs.get(i)[0] + ",";
                    }
                }
                throw new ServiceException(errorColumns + " 已存在！");
            }
        }
    }

    /**
     * 修改
     *
     * @param dto 实体
     * @return R
     */
    @PostMapping("/update")
    protected R<Boolean> update(@RequestBody T dto) {
        ValidUtil.validUpdate(dto);
        return R.status(baseService.updateById(dto.buildEntity()));
    }

    /**
     * 新增或修改
     *
     * @param dto 实体
     * @return R
     */
    @PostMapping("/saveOrUpdate")
    protected R<Boolean> saveOrUpdate(@RequestBody T dto) {
        ValidUtil.validSave(dto);
        return R.status(baseService.saveOrUpdate(dto.buildEntity()));
    }

    /**
     * 删除
     *
     * @param dto 实体
     * @return R
     */
    @PostMapping("/delete")
    protected R<Boolean> delete(@RequestBody T dto) {
        ValidUtil.validDelete(dto);
        return R.status(baseService.removeById(dto.buildEntity()));
    }

    /**
     * 导出excel
     * 具体实现类必须重写 initExport 、page 方法
     *
     * @param query    检索条件
     * @param response response
     */
    @GetMapping("/exportExcel")
    protected void exportExcel(Q query, HttpServletResponse response) {
        // 调用分页方法，实现类若有自己的实现，则会调用自己的实现方法
        initExport();
        IPage iPage = page(query).getData();
        List records = iPage.getRecords();
        String[] exportTitle = this.exportTitle;
        if (exportTitle == null || exportTitle.length == 0) {
            throw new ServiceException("请设置要导出的列！格式为[excel表头名称:导出实体对应的属性名称:宽度]");
        }
        String sheetName = StringUtils.isBlank(this.sheetName) ? "sheet1" : this.sheetName;
        String fileName = StringUtils.isBlank(this.excelFileName) ? "导出结果.xlsx" : this.excelFileName;
        HSSFWorkbook workbook = ApachePoiExcelUtil.export(null, records, exportTitle, sheetName);
        ApachePoiExcelUtil.downLoadExcel(fileName, response, workbook);
    }

    /**
     * 设置导出属性
     */
    protected void initExport() {
        this.exportTitle = null;
        this.excelFileName = null;
        this.sheetName = null;
    }

    // 导出excel文件名
    protected String excelFileName;
    // excel导出标题
    protected String[] exportTitle;
    // 导出表名
    protected String sheetName;

}