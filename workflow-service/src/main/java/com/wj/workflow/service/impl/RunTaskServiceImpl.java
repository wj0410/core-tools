package com.wj.workflow.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.workflow.mapper.RunTaskMapper;
import com.wj.workflow.entity.RunTask;
import com.wj.workflow.service.RunTaskService;
@Service
public class RunTaskServiceImpl extends ServiceImpl<RunTaskMapper, RunTask> implements RunTaskService{

}
