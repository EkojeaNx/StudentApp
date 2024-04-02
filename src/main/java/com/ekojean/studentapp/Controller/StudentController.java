package com.ekojean.studentapp.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekojean.studentapp.Data.StudentDao;
import com.ekojean.studentapp.Entities.Student;
import com.ekojean.studentapp.Interfaces.IServices;
import com.ekojean.studentapp.Services.StudentServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "api/student")
public class StudentController {

    private final IServices<Student> studentServices;

    public StudentController() {
        this.studentServices = new StudentServices(new StudentDao());
    }

    @GetMapping()    
    public List<Student> getStudentList(String filterStudentText) {
        return studentServices.getModelList(filterStudentText);
    }

    @PostMapping()
    public boolean addStudent(@RequestBody Student student) {        
        return studentServices.addModel(student);
    }

    @PutMapping()
    public boolean updateStudent(@RequestBody Student student) {
        return studentServices.updateModel(student);
    }

    @DeleteMapping() 
    public boolean deleteStudent(@RequestBody Student student) {
        return studentServices.deleteModel(student);
    }
    
}
