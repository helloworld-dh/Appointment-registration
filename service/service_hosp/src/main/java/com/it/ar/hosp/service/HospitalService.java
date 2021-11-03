package com.it.ar.hosp.service;

import com.it.ar.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {
    // 上传医院接口
    void save(Map<String,Object> paramMap);

    // 根据医院编号查询
    Hospital getByHoscode(String hoscode);
}
