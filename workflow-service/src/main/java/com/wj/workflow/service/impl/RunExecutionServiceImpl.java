package com.wj.workflow.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.workflow.entity.RunExecution;
import com.wj.workflow.mapper.RunExecutionMapper;
import com.wj.workflow.service.RunExecutionService;
@Service
public class RunExecutionServiceImpl extends ServiceImpl<RunExecutionMapper, RunExecution> implements RunExecutionService{

}
