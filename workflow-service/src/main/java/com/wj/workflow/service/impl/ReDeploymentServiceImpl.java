package com.wj.workflow.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wj.workflow.mapper.ReDeploymentMapper;
import com.wj.workflow.entity.ReDeployment;
import com.wj.workflow.service.ReDeploymentService;
@Service
public class ReDeploymentServiceImpl extends ServiceImpl<ReDeploymentMapper, ReDeployment> implements ReDeploymentService{

}
