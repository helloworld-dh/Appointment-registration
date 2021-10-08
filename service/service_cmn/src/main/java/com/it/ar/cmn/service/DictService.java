package com.it.ar.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.ar.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {

    //根据id查询子数据
    List<Dict> findChildData(Long id);

    //到处数据字典接口
    void exportDictData(HttpServletResponse response);

    //导入数据
    void importDictData(MultipartFile multipartFile);
}
