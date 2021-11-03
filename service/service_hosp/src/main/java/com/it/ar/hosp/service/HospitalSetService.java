package com.it.ar.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.ar.model.hosp.HospitalSet;

public interface HospitalSetService extends IService<HospitalSet> {

    // 根据传递过来医院编码，查询数据库，查询签名
    public String getSignKey(String hoscode);


}
