package com.ekojean.studentapp.Interfaces;

import java.util.List;

public interface IRepositoryDao<T extends IEntity> {
    public List<T> getModelList();
    public boolean addModel(T dataModel);
    public boolean updateModel(T dataModel);
    public boolean deleteModel(T dataModel);
}
