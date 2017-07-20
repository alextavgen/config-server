package com.playtech.configserver.vaadin;
import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleksandr on 17/07/2017.
 */
@SpringUI(path="/ui")
@Theme("valo")
public class ErrorCodeUI extends UI{
    /*@WebServlet(value = "/testvaadin", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ErrorCodeUI.class)
    public static class Servlet extends SpringVaadinServlet {
        {
            setServiceUrlPath("/testvaadin");
        }
    }*/
    private final ErrorCodeRepository repo;

    private final ErrorCodeEditor editor;

    final Grid<ErrorCode> grid;

    final TextField filter;

    private final Button addNewBtn;

    @Autowired
    public ErrorCodeUI(ErrorCodeRepository repo, ErrorCodeEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(ErrorCode.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New Error Code", VaadinIcons.PLUS);
    }

    @Override
    protected void init(VaadinRequest request) {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        grid.setHeight(300, Unit.PIXELS);
        grid.setColumns("id", "code", "descr", "Group");

        filter.setPlaceholder("Filter Code");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listErrorCodes(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editCustomer(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new ErrorCode(null, "","", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listErrorCodes(filter.getValue());
        });

        // Initialize listing
        listErrorCodes(null);
    }

    // tag::listCustomers[]
    void listErrorCodes(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            List<ErrorCode> target = new ArrayList<>();
            repo.findAll().forEach(target::add);
            grid.setItems(target);
        }
        else {
            grid.setItems(repo.findByCode(filterText));
        }
    }
    // end::listCustomers[]

}
