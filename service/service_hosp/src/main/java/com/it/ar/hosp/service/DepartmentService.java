package com.it.ar.hosp.service;

import com.it.ar.model.hosp.Department;
import com.it.ar.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DepartmentService {

    // 上传科室接口
    void save (Map<String,Object> paramMap);

    // 查询科室接口
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);
}
