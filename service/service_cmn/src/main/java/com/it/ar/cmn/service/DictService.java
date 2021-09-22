package com.it.ar.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.ar.model.cmn.Dict;

import java.util.List;

public interface DictService extends IService<Dict> {

    //根据id查询子数据
    List<Dict> findChildData(Long id);
}
