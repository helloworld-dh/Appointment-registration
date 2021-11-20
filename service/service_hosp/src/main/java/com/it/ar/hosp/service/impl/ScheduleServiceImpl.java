package com.it.ar.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.it.ar.hosp.repository.ScheduleRepository;
import com.it.ar.hosp.service.ScheduleService;
import com.it.ar.model.hosp.Department;
import com.it.ar.model.hosp.Schedule;
import com.it.ar.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    // 上传排班接口
    @Override
    public void save(Map<String, Object> paramMap) {
        // paramMap 转化成department对象
        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString, Schedule.class);

        // 根据医院编号和 排班编号进行查询
        Schedule scheduleExist =
                scheduleRepository.getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());

        if (scheduleExist!=null){
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleExist.setStatus(1);
            scheduleRepository.save(scheduleExist);
        }else {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }
    }

    // 查询排班接口
    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        // 创建Pageable对象，设置当前页和每页记录数  0是第一页
        PageRequest pageable = PageRequest.of(page - 1, limit);
        // 创建Example对象
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)   // 模糊查询
                .withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);    // 动态查询

        Page<Schedule> all = scheduleRepository.findAll(example, pageable);    // 分页
        return all;
    }

    // 删除排班
    @Override
    public void remove(String hoscode, String hosScheduleId) {
        // 根据医院编号和排班编号查询信息
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if (schedule!=null){
            scheduleRepository.deleteById(schedule.getId());
        }
    }
}
