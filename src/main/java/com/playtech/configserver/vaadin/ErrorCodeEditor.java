package com.playtech.configserver.vaadin;

import com.playtech.configserver.domain.ErrorCode;
import com.playtech.configserver.repositories.ErrorCodeRepository;
import com.vaadin.icons.VaadinIcons;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


@SpringComponent
@UIScope
public class ErrorCodeEditor extends VerticalLayout {

    private final ErrorCodeRepository repository;

    /**
     * The currently edited errorCode
     */
    private ErrorCode errorCode;

    /* Fields to edit properties in Customer entity */
    IntegerField id = new IntegerField("Error Id");
    //id.addValidator(new RegexpValidator("[-]?[0-9]*\\.?,?[0-9]+"), "This is not a number!");
    TextField code = new TextField("Error Code");
    TextField descr = new TextField("Description");
    TextField group = new TextField("Group");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcons.CHECK_CIRCLE_O);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcons.TRASH);
    CssLayout actions = new CssLayout(save, cancel, delete);

    Binder<ErrorCode> binder = new Binder<>(ErrorCode.class);

    @Autowired
    public ErrorCodeEditor(ErrorCodeRepository repository) {
        this.repository = repository;

        addComponents(id, code, descr, group, actions);

        // bind using naming convention
        binder.bindInstanceFields(this);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> repository.save(errorCode));
        delete.addClickListener(e -> repository.delete(errorCode));
        cancel.addClickListener(e -> editCustomer(errorCode));
        setVisible(false);
    }

    public interface ChangeHandler {

        void onChange();
    }

    public final void editCustomer(ErrorCode c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            errorCode = repository.findOne(c.getId().toString());
        }
        else {
            errorCode = c;
        }
        cancel.setVisible(persisted);

        // Bind errorCode properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(errorCode);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        id.selectAll();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}

