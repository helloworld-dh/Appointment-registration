package com.it.hospital_manage.service.impl;

import com.it.hospital_manage.mapper.OrderInfoMapper;
import com.it.hospital_manage.mapper.ScheduleMapper;
import com.it.hospital_manage.service.HospitalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private ScheduleMapper hospitalMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;


}
