package com.it.ar.cmn.controller;

import com.it.ar.cmn.service.DictService;
import com.it.ar.common.result.Result;
import com.it.ar.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "数据字典接口")
@RestController
@CrossOrigin
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }

    //导出数据字典
    @GetMapping("/exportData")
    public void exportDict(HttpServletResponse response){
        dictService.exportDictData(response);
    }

    //导入数据字典
    @PostMapping("/importData")
    public Result importDict(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }
}
