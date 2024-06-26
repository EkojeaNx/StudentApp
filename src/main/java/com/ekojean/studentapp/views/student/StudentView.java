package com.ekojean.studentapp.views.student;

import com.ekojean.studentapp.Controller.StudentController;
import com.ekojean.studentapp.Entities.Student;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Student")
@Route("student")
@RouteAlias(value = "")
public class StudentView extends VerticalLayout {

    Grid<Student> grid = new Grid<>(Student.class, false);

    TextField filterText = new TextField();

    Button addStudentButton = new Button(new Icon(VaadinIcon.PLUS_CIRCLE));
    Button refreshStudentButton = new Button(new Icon(VaadinIcon.REFRESH));
    Button closeFormButton = new Button(new Icon(VaadinIcon.CLOSE));

    StudentForm form;

    StudentController studentController;

    Dialog studentDeleteDialog = new Dialog();

    Button studentDeleteDialogButton = new Button("Delete");
    Button studentCancelDialogButton = new Button("Cancel");

    Student student;

    public StudentView(StudentController studentController) {
        this.studentController = studentController;

        

        configurationStudentDeleteDialog();

        configurationGrid();
        configurationForm();

        add(
            getViewHeader(),
            getToolBar(),
            getContent()
        );

        updateGridList();
        closeForm();

        
        studentDeleteDialogButton.addClickListener(e -> {
            studentController.deleteStudent(student);

            Notification.show(student.getFullName() + " Student Deleted!");

            formClear();

            updateGridList();
            studentDeleteDialog.close();
        });

        studentDeleteDialogButton.addClickShortcut(Key.ENTER);

        studentCancelDialogButton.addClickListener(e -> {
            studentDeleteDialog.close();
        });

        studentCancelDialogButton.addClickShortcut(Key.ESCAPE);

    }

    private void configurationStudentDeleteDialog() {
        studentDeleteDialog.add("Are you sure you want to delete this student permanently?");

        studentDeleteDialogButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        studentDeleteDialogButton.getStyle().set("margin-right", "auto");
        studentDeleteDialog.getFooter().add(studentDeleteDialogButton);

        studentCancelDialogButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        studentDeleteDialog.getFooter().add(studentCancelDialogButton);
    }

    private void updateGridList() {
        grid.setItems(studentController.getStudentList(filterText.getValue()));
    }

    private void closeForm() {
        form.setStudent(null);
        form.setVisible(false);
    }

    private VerticalLayout getViewHeader() {
        VerticalLayout header = new VerticalLayout();

        header.setSizeFull();
        header.setAlignItems(Alignment.CENTER);
        header.setJustifyContentMode(JustifyContentMode.CENTER);

        header.add(
            new H1("# Student Application #"),
            new Hr()
        );

        return header;
    }

    private void configurationGrid() {
        grid.addColumn(Student::getId).setHeader("ID").setSortable(true);
        grid.addColumn(Student::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Student::getSurname).setHeader("Surname").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        //updateGridList();

        grid.asSingleSelect().addValueChangeListener(e -> editStudent(e.getValue()));
    }
    
    private void editStudent(Student student) {
        if(student == null) {
            closeForm();
        } else {
            form.setStudent(student);
            form.setVisible(true);
        }

    }

    private void configurationForm() {
        form = new StudentForm();
        form.setWidth("25em");
    }

    private HorizontalLayout getToolBar() {
        HorizontalLayout toolBar = new HorizontalLayout();

        filterText.setPlaceholder("Filter by name...");
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterText.setTooltipText("Search to name and surname");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        filterText.addValueChangeListener(e -> {
            updateGridList();
        });

        refreshStudentButton.setTooltipText("Grid Refresh");
        addStudentButton.setTooltipText("Student Added");
        closeFormButton.setTooltipText("Student Edit Close");

        refreshStudentButton.addClickListener(e -> {
            updateGridList();
        });

        addStudentButton.addClickListener(e -> {
            if(!form.isVisible())
                addStudent();
            else
                closeForm();
        });

        closeFormButton.addClickListener(e -> {
            closeForm();
        });



        // Form
        form.studentSaveButton.addClickListener(e -> {
            student = form.getStudent();
            
            if(student.getId() == 0) {
                studentController.addStudent(student);

                Notification.show(student.getFullName() + " Student Added!");

                formClear();

                updateGridList();
            } else {
                studentController.updateStudent(student);

                Notification.show(student.getFullName() + " Student Updated!");

                formClear();

                updateGridList();
            }
        });

        form.studentDeleteButton.addClickListener(e -> {
            student = form.getStudent();

            studentDeleteDialog.open();
            
            studentDeleteDialog.setHeaderTitle("Delete student " + student.getFullName());
        });

        form.studentFormCloseButton.addClickListener(e -> {
            closeForm();
        });

        toolBar.add(
            filterText,
            refreshStudentButton,
            addStudentButton,
            closeFormButton
        );

        return toolBar;
    }

    private void formClear() {
        form.studentIdTF.setValue(0);
        form.studentNameTF.setValue("");
        form.studentSurnameTF.setValue("");
    }

    private void addStudent() {
        grid.asSingleSelect().clear();
        editStudent(new Student());
    }

    private Component getContent() {
        SplitLayout content = new SplitLayout(grid, form);
        //content.setFlexGrow(2, grid);
        //content.setFlexGrow(1, form);
        content.setSplitterPosition(75);
        content.setSizeFull();

        return content;
    }
}
