package cn.duan.community.service.impl;

import cn.duan.community.mapper.LogMapper;
import cn.duan.community.model.LoginLog;
import cn.duan.community.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LogMapper logMapper;
    @Override
    public void save(LoginLog loginLog) {
        logMapper.insert(loginLog);
    }
}
