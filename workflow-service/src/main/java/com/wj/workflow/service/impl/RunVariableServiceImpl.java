package com.wj.workflow.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.workflow.mapper.RunVariableMapper;
import com.wj.workflow.entity.RunVariable;
import com.wj.workflow.service.RunVariableService;
@Service
public class RunVariableServiceImpl extends ServiceImpl<RunVariableMapper, RunVariable> implements RunVariableService{

}
