package com.ekojean.studentapp.Data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ekojean.studentapp.Entities.Student;
import com.ekojean.studentapp.Helper.DBConnector;
import com.ekojean.studentapp.Interfaces.IRepositoryDao;

public class StudentDao implements IRepositoryDao<Student> {

    @Override
    public List<Student> getModelList() {
        ArrayList<Student> studentList = new ArrayList<>();

        String query = "SELECT * FROM student";

        Statement statement = null;
        ResultSet resultSet = null;

        Student student;

        try {
            statement = DBConnector.getInstance().createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                student = new Student(
                    resultSet.getInt("id"), 
                    resultSet.getString("name"), 
                    resultSet.getString("surname")
                );

                studentList.add(student);
                
            }

            resultSet.close();
            statement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    @Override
    public boolean addModel(Student dataModel) {
        boolean isChacked = false;

        String query = "INSERT INTO student (name, surname) VALUES (?, ?)";

        PreparedStatement preparedStatement = null;

        int result;

        try {
            preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, dataModel.getName());
            preparedStatement.setString(2, dataModel.getSurname());

            result = preparedStatement.executeUpdate();

            if(result != -1)
                isChacked = true;
            else
                isChacked = false;

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isChacked;
    }

    @Override
    public boolean updateModel(Student dataModel) {
        boolean isChacked = false;

        String query = "UPDATE student SET name = ?, surname = ? WHERE id = ?";

        PreparedStatement preparedStatement = null;

        int result;

        try {
            preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1, dataModel.getName());
            preparedStatement.setString(2, dataModel.getSurname());
            preparedStatement.setInt(3, dataModel.getId());

            result = preparedStatement.executeUpdate();

            if(result != -1)
                isChacked = true;
            else
                isChacked = false;
            
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isChacked;
    }

    @Override
    public boolean deleteModel(Student dataModel) {
        boolean isChacked = false;

        String query = "DELETE FROM student WHERE id = ?";

        PreparedStatement preparedStatement = null;

        int result;

        try {
            preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, dataModel.getId());

            result = preparedStatement.executeUpdate();

            if(result != -1)
                isChacked = true;
            else
                isChacked = false;
            
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isChacked;
    }
}
