package com.it.ar.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.ar.common.result.Result;
import com.it.ar.common.utils.MD5;
import com.it.ar.hosp.service.HospitalSetService;
import com.it.ar.model.hosp.HospitalSet;
import com.it.ar.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "医院设置管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    // 查询医院设置表中的所有信息
    @ApiOperation("获取医院的所有设置信息")
    @GetMapping("/findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //逻辑删除
    @ApiOperation("医院信息逻辑删除设置")
    @DeleteMapping("/{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //带分页的条件查询
    @PostMapping("/findPageHospitalSet/{current}/{limit}")
    public Result findPageHospitalSet(@PathVariable long current,
                                      @PathVariable long limit,
                                      @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> hospitalSetPage = new Page<>(current,limit);
        //构造条件
        QueryWrapper<HospitalSet> hospitalSetQueryWrapper = new QueryWrapper<>();
        //医院名称
        String hosname = hospitalSetQueryVo.getHosname();
        if (!StringUtils.isEmpty(hosname)){
            hospitalSetQueryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
        }
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isEmpty(hoscode)){

            hospitalSetQueryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());
        }

        //调用方法实现条件查询
        Page<HospitalSet> page = hospitalSetService.page(hospitalSetPage, hospitalSetQueryWrapper);
        return Result.ok(page);

    }
    //添加医院
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));

        //调用service
        boolean save = hospitalSetService.save(hospitalSet);
        if (save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //根据ID获取医院
    @GetMapping("/getHospSet/{id}")
    public Result getHosp(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }
    //修改医院
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //批量删除医院
    @DeleteMapping("/batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList){
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }
    //医院设置的锁定和解锁
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status){
        //根据ID查询医院信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }
    //发送签名密钥
    @PutMapping("/sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        // 发送短信
        return Result.ok();
    }
}
