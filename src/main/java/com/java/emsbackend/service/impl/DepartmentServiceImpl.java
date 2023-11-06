package com.java.emsbackend.service.impl;

import com.java.emsbackend.dto.DepartmentDto;
import com.java.emsbackend.entity.Department;
import com.java.emsbackend.exception.ResourceNotFoundException;
import com.java.emsbackend.mapper.DepartmentMapper;
import com.java.emsbackend.repository.DepartmentRepository;
import com.java.emsbackend.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {

        Department department = DepartmentMapper.mapToDepartment(departmentDto);
        Department savedDepartment = departmentRepository.save(department);

        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentID) {
        Department department = departmentRepository.findById(departmentID).orElseThrow(
                () -> new ResourceNotFoundException("Department does not exists with the given ID: " + departmentID)
        );

        return DepartmentMapper.mapToDepartmentDto(department);

    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map((department) -> DepartmentMapper.mapToDepartmentDto(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatedDepartment) {

        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department does not exist with a given ID: " + departmentId)
        );

        department.setDepartmentName(updatedDepartment.getDepartmentName());
        department.setDepartmentDescription(updatedDepartment.getDepartmentDescription());

        Department savedDepartmetn = departmentRepository.save(department);

        return DepartmentMapper.mapToDepartmentDto(savedDepartmetn);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department does not exist with a given ID: " + departmentId)
        );

        departmentRepository.deleteById(departmentId);
    }
}
