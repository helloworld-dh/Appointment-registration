package com.it.ar.hosp.controller.api;

import com.it.ar.common.exception.ArException;
import com.it.ar.common.helper.HttpRequestHelper;
import com.it.ar.common.result.Result;
import com.it.ar.common.result.ResultCodeEnum;
import com.it.ar.common.utils.MD5;
import com.it.ar.hosp.service.DepartmentService;
import com.it.ar.hosp.service.HospitalService;
import com.it.ar.hosp.service.HospitalSetService;
import com.it.ar.hosp.service.ScheduleService;
import com.it.ar.model.hosp.Department;
import com.it.ar.model.hosp.Hospital;
import com.it.ar.model.hosp.Schedule;
import com.it.ar.vo.hosp.DepartmentQueryVo;
import com.it.ar.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //上传医院接口
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        // 获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String)paramMap.get("sign");

        // 根据传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String)paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 把数据库查询签名进行MD5加密
        String signKeyMD5 = MD5.encrypt(signKey);

        // 判断签名是否一致
        if (!hospSign.equals(signKeyMD5)){
            throw new ArException(ResultCodeEnum.SIGN_ERROR);
        }

        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

        //调用service方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        // 获取传递过来的医院信息
        System.out.println(request.toString());
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        // 获取医院传递过来的签名,签名已经进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        // 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        // 判断签名是否一致
        if (!hospSign.equals(signKeyMd5)){
            throw new ArException(ResultCodeEnum.SIGN_ERROR);
        }
        // 调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }

    // 上传科室接口
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);
        // 获取医院编号
        String hoscode = (String) paramMap.get("hoscode");
        // 获取医院传递过来的签名,签名已经进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        // 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        // 判断签名是否一致
        if (!hospSign.equals(signKeyMd5)){
            throw new ArException(ResultCodeEnum.SIGN_ERROR);
        }

        // 调用service方法
        departmentService.save(paramMap);
        return Result.ok();
    }

    // 查询科室接口
    @PostMapping("/department/list")
    public Result findDepartment(HttpServletRequest request){
        // 获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // 医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //当前页和每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        // 获取医院传递过来的签名,签名已经进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        // 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        // 判断签名是否一致
        if (!hospSign.equals(signKeyMd5)){
            throw new ArException(ResultCodeEnum.SIGN_ERROR);
        }

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        // 调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }
    // 删除科室接口
    @PostMapping("/department/remove")
    public Result removeDepartment(HttpServletRequest request){
        // 获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // 医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //科室编号
        String depcode = (String) paramMap.get("depcode");

        // 获取医院传递过来的签名,签名已经进行MD5加密
        String hospSign = (String) paramMap.get("sign");
        // 根据传递过来医院编码，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        // 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        // 判断签名是否一致
        if (!hospSign.equals(signKeyMd5)){
            throw new ArException(ResultCodeEnum.SIGN_ERROR);
        }
        departmentService.remove(hoscode,depcode);
        return Result.ok();

    }

    // 上传排班接口
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        // 获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // 获取医院编号
//        String hoscode = (String) paramMap.get("hoscode");
//        // 获取医院传递过来的签名,签名已经进行MD5加密
//        String hospSign = (String) paramMap.get("sign");
//        // 根据传递过来医院编码，查询数据库，查询签名
//        String signKey = hospitalSetService.getSignKey(hoscode);
//        // 把数据库查询签名进行MD5加密
//        String signKeyMd5 = MD5.encrypt(signKey);
//        // 判断签名是否一致
//        if (!hospSign.equals(signKeyMd5)){
//            throw new ArException(ResultCodeEnum.SIGN_ERROR);
//        }
        scheduleService.save(paramMap);
        return Result.ok();
    }

    // 查询排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request){
        // 获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // 医院编号
        String hoscode = (String) paramMap.get("hoscode");

        // 科室编号
        String depcode = (String) paramMap.get("depcode");

        //当前页和每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

//        // 获取医院传递过来的签名,签名已经进行MD5加密
//        String hospSign = (String) paramMap.get("sign");
//        // 根据传递过来医院编码，查询数据库，查询签名
//        String signKey = hospitalSetService.getSignKey(hoscode);
//        // 把数据库查询签名进行MD5加密
//        String signKeyMd5 = MD5.encrypt(signKey);
//        // 判断签名是否一致
//        if (!hospSign.equals(signKeyMd5)){
//            throw new ArException(ResultCodeEnum.SIGN_ERROR);
//        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        // 调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page, limit, scheduleQueryVo);
        System.out.println(pageModel.getTotalElements());
        return Result.ok(pageModel);
    }

    // 删除排班
    @PostMapping("/schedule/remove")
    public Result remove(HttpServletRequest request){
        // 获取传递过来的科室信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        // 获取医院编号和排版编号
        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        // 获取医院传递过来的签名,签名已经进行MD5加密
//        String hospSign = (String) paramMap.get("sign");
//        // 根据传递过来医院编码，查询数据库，查询签名
//        String signKey = hospitalSetService.getSignKey(hoscode);
//        // 把数据库查询签名进行MD5加密
//        String signKeyMd5 = MD5.encrypt(signKey);
//        // 判断签名是否一致
//        if (!hospSign.equals(signKeyMd5)){
//            throw new ArException(ResultCodeEnum.SIGN_ERROR);
//        }

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

}
