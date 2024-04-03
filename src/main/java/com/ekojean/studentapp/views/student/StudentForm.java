package com.ekojean.studentapp.views.student;

import com.ekojean.studentapp.Entities.Student;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class StudentForm extends FormLayout {

    Binder<Student> studentBinder = new Binder<>(Student.class);

    IntegerField studentIdTF = new IntegerField("ID");

    TextField studentNameTF = new TextField("Name");
    TextField studentSurnameTF = new TextField("Surname");

    Button studentSaveButton = new Button("Save");
    Button studentDeleteButton = new Button("Delete");
    Button studentFormCloseButton = new Button("Cancel");

    Student student;

    public StudentForm() {
        studentBinder.forField(studentIdTF).bind(Student::getId, Student::setId);
        studentBinder.forField(studentNameTF).bind(Student::getName, Student::setName);
        studentBinder.forField(studentSurnameTF).bind(Student::getSurname, Student::setSurname);

        studentIdTF.setEnabled(false);
        
        add(
            studentIdTF,
            studentNameTF,
            studentSurnameTF,
            getButton()
        );
    }

    private HorizontalLayout getButton() {
        HorizontalLayout buttonLayout = new HorizontalLayout();

        studentSaveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        studentDeleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        studentFormCloseButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        studentSaveButton.addClickShortcut(Key.ENTER);
        studentFormCloseButton.addClickShortcut(Key.ESCAPE);

        buttonLayout.add(
            studentSaveButton,
            studentDeleteButton,
            studentFormCloseButton
        );

        return buttonLayout;
    } 

    public void setStudent(Student student) {
        this.student = student;
        studentBinder.readBean(student);
    }

    public Student getStudent() {
        Student student = new Student(studentIdTF.getValue(), studentNameTF.getValue(), studentSurnameTF.getValue());
        setStudent(student);
        return this.student;
    }
}
