package com.ssm.demo.service.impl;

import com.ssm.demo.dao.UserLogMapper;
import com.ssm.demo.domain.UserLog;
import com.ssm.demo.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author o_0sky
 * @date 2019/5/21 19:20
 */
@Service
public class UserLogServiceImpl implements UserLogService {
    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserLog record) {
        return userLogMapper.insert(record);
    }

    @Override
    public int insertSelective(UserLog record) {
        return userLogMapper.insertSelective(record);
    }

    @Override
    public UserLog selectByPrimaryKey(Integer id) {
        return userLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLog record) {
        return userLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserLog record) {
        return userLogMapper.updateByPrimaryKey(record);
    }
}
