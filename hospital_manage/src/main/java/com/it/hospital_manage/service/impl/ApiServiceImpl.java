package com.it.hospital_manage.service.impl;

import com.it.hospital_manage.mapper.HospitalSetMapper;
import com.it.hospital_manage.mapper.ScheduleMapper;
import com.it.hospital_manage.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @Autowired
    private ApiService apiService;

}
