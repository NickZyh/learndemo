package com.example.mockmybatis.service;

import com.example.mockmybatis.dao.DemoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public void query() {
        demoDao.query();
    }
}
