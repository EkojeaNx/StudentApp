package com.ekojean.studentapp.views.student;

import com.ekojean.studentapp.Controller.StudentController;
import com.ekojean.studentapp.Entities.Student;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    StudentController studentController;

    public StudentView(StudentController studentController) {
        this.studentController = studentController;

        configurationGrid();

        add(
            getViewHeader(),
            getToolBar(),
            grid
        );
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

        grid.setItems(studentController.getStudentList());
    }

    private HorizontalLayout getToolBar() {
        HorizontalLayout toolBar = new HorizontalLayout();

        filterText.setPlaceholder("Filter by name...");
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterText.setTooltipText("Search to name and surname");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> {
            Notification.show(filterText.getValue());
        });

        toolBar.add(
            filterText
        );

        return toolBar;
    }
}
