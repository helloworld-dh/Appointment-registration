package com.it.hospital_manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.it.hospital_manage.mapper.HospitalSetMapper;
import com.it.hospital_manage.mapper.ScheduleMapper;
import com.it.hospital_manage.model.HospitalSet;
import com.it.hospital_manage.service.ApiService;
import com.it.hospital_manage.util.HospitalException;
import com.it.hospital_manage.util.HttpRequestHelper;
import com.it.hospital_manage.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

    @Autowired
    private ApiService apiService;

    @Value("classpath:hospital.json")
    private Resource hospitalResource;

    @Override
    public String getHoscode() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getHoscode();
    }

    @Override
    public String getSignKey() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getSignKey();
    }

    private String getApiUrl() {
        HospitalSet hospitalSet = hospitalSetMapper.selectById(1);
        return hospitalSet.getApiUrl();
    }

    @Override
    public JSONObject getHospital() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hoscode", this.getHoscode());
        paramMap.put("timestap", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));
        JSONObject response = HttpRequestHelper.sendRequest(paramMap, this.getApiUrl() + "/api/hos/hospital/show");
        System.out.println(response.toJSONString());
        if (response != null && response.getIntValue("code") == 200) {
            JSONObject jsonObject = response.getJSONObject("data");
            return jsonObject;
        }
        return null;
    }

    @Override
    public boolean saveHospital(String data) {
        // JSONObject.parseObject(data)能将data(json)转化为jsonObject(object)
        JSONObject jsonObject = JSONObject.parseObject(data);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("hoscode",this.getHoscode());
        // jsonObject.getString获得key对应的value
        paramMap.put("hosname",jsonObject.getString("hosname"));
        paramMap.put("hostype",jsonObject.getString("hostype"));
        paramMap.put("provinceCode",jsonObject.getString("provinceCode"));
        paramMap.put("cityCode",jsonObject.getString("cityCode"));
        paramMap.put("districtCode",jsonObject.getString("districtCode"));
        paramMap.put("address",jsonObject.getString("address"));
        paramMap.put("intro",jsonObject.getString("intro"));
        paramMap.put("route",jsonObject.getString("route"));
        // 图片
        paramMap.put("logoData",jsonObject.getString("logoData"));
        // 如果JsonObject对象中的value是一个JSONObject对象，即根据key获取对应的JSONObject对象
        JSONObject bookingRule = jsonObject.getJSONObject("bookingRule");
        paramMap.put("bookingRule",bookingRule.toJSONString());

        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());
        paramMap.put("sign", MD5.encrypt(this.getSignKey()));

        JSONObject response =
                HttpRequestHelper.sendRequest(paramMap, this.getApiUrl() + "/api/hosp/saveHospital");
        System.out.println(response.toJSONString());

        if (response !=null && response.getIntValue("code")==200){
            return true;
        }else {
            throw new HospitalException(response.getString("message"),201);
        }
    }

    @Override
    public Map<String, Object> findDepartment(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public boolean saveDepartment(String data) {
        return false;
    }

    @Override
    public boolean removeDepartment(String depcode) {
        return false;
    }

    @Override
    public Map<String, Object> findSchedule(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public boolean saveSchedule(String data) {
        return false;
    }

    @Override
    public boolean removeSchedule(String hosScheduleId) {
        return false;
    }

    @Override
    public void saveBatchHospital() throws IOException {

    }
}
