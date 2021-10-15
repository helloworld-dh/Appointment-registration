package com.it.hospital_manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.it.hospital_manage.mapper.HospitalSetMapper;
import com.it.hospital_manage.mapper.ScheduleMapper;
import com.it.hospital_manage.model.HospitalSet;
import com.it.hospital_manage.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @Autowired
    private ApiService apiService;

    @Override
    public String getHoscode() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getHoscode();
    }

    @Override
    public String getSignKey() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getSignKey();
    }

    private String getApiUrl(){
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getApiUrl();
    }

    @Override
    public JSONObject getHospital() {


        return null;
    }

    @Override
    public boolean saveHospital(String data) {
        return false;
    }

    @Override
    public Map<String, Object> findDepartment(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public boolean saveDepartment(String data) {
        return false;
    }

    @Override
    public boolean removeDepartment(String depcode) {
        return false;
    }

    @Override
    public Map<String, Object> findSchedule(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public boolean saveSchedule(String data) {
        return false;
    }

    @Override
    public boolean removeSchedule(String hosScheduleId) {
        return false;
    }

    @Override
    public void saveBatchHospital() throws IOException {

    }
}
