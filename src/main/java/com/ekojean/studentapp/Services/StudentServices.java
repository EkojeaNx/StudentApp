package com.ekojean.studentapp.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ekojean.studentapp.Entities.Student;
import com.ekojean.studentapp.Interfaces.IRepositoryDao;
import com.ekojean.studentapp.Interfaces.IServices;

@Service
public class StudentServices implements IServices<Student> {

    private final IRepositoryDao<Student> repositoryDao;

    public StudentServices(IRepositoryDao<Student> repositoryDao) {
        this.repositoryDao = repositoryDao;
    }

    @Override
    public List<Student> getModelList(String filterModelText) {
        if(!filterModelText.isEmpty())
            return repositoryDao.getFindModelList(filterModelText);
        else
            return repositoryDao.getModelList();
    }

    @Override
    public boolean addModel(Student dataModel) {
        return repositoryDao.addModel(dataModel);
    }

    @Override
    public boolean updateModel(Student dataModel) {
        return repositoryDao.updateModel(dataModel);
    }

    @Override
    public boolean deleteModel(Student dataModel) {
        return repositoryDao.deleteModel(dataModel);
    }

}
