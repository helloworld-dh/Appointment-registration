package com.it.ar.hosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.ar.hosp.mapper.HospitalSetMapper;
import com.it.ar.hosp.service.HospitalSetService;
import com.it.ar.model.hosp.HospitalSet;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
}
