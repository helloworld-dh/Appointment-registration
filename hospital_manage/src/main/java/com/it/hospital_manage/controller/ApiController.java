package com.it.hospital_manage.controller;

import com.it.hospital_manage.mapper.HospitalSetMapper;
import com.it.hospital_manage.model.HospitalSet;
import com.it.hospital_manage.service.ApiService;
import com.it.hospital_manage.util.HospitalException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@Api(tags = "医院管理接口")
@Controller
@RequestMapping
public class ApiController extends BaseController{

    @Autowired
    private ApiService apiService;

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @RequestMapping("/hospitalSet/index")
    public String getHospitalSet(ModelMap model, RedirectAttributes redirectAttributes){
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        model.addAttribute("hospitalSet", hospitalSet);
        return "hospitalSet/index";
    }

    @RequestMapping("/hospital/index")
    public String createHospitalSet(ModelMap model,
                                    HttpServletRequest request,
                                    RedirectAttributes redirectAttributes){
        try {
            HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
            if (hospitalSet == null || StringUtils.isEmpty(hospitalSet.getHoscode())
                    || StringUtils.isEmpty(hospitalSet.getSignKey())){
                this.failureMessage("先设置医院code与签名key",redirectAttributes);
                return "redirect:/hospitalSet/index";
            }
            model.addAttribute("hospital", apiService.getHospital());
        }catch (HospitalException e){
            this.failureMessage(e.getMessage(),request);
        }catch (Exception e){
            this.failureMessage("数据异常",request);
        }
        return "hospital/index";
    }

    @RequestMapping(value = "/hospital/create")
    public String createHospital(ModelMap model){
        return "hospital/create";
    }

    @RequestMapping(value = "/hospital/save", method = RequestMethod.POST)
    public String saveHospital(String data, HttpServletRequest request){
        try {
            apiService.saveHospital(data);
        }catch (HospitalException e){
            return this.failurePage(e.getMessage(),request);
        }catch (Exception e){
            return this.failurePage("数据异常",request);
        }
        return this.successPage(null,request);
    }

    @RequestMapping("/department/list")
    public String findDepartment(ModelMap model,
                                 @RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10")int pageSize,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes){
        try{
            HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
            if (hospitalSet == null || StringUtils.isEmpty(hospitalSet.getHoscode())
                || StringUtils.isEmpty(hospitalSet.getSignKey())){
                this.failureMessage("先设置医院code与签名key", redirectAttributes);
                return "redirect:/hospitalSet/index";
            }
            model.addAllAttributes(apiService.findDepartment(pageNum, pageSize));
        }catch (HospitalException e){
            this.failureMessage(e.getMessage(), request);
        }catch (Exception e){
            this.failureMessage("数据异常",request);
        }
        return "department/index";

    }
}
