package my.vaadin.app;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	private StudentService service = StudentService.getInstance();
	private Grid<Student> grid = new Grid<>(Student.class);
	private TextField filterText = new TextField();
	private StudentForm form = new StudentForm(this);
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        filterText.setPlaceholder("Text eingeben");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        
        Button clearFilterBtn = new Button("Filter leeren");
        clearFilterBtn.addClickListener(e -> filterText.clear());
        
        CssLayout filteringLayout = new CssLayout();
        filteringLayout.addComponents(filterText,clearFilterBtn);
        
        Button btnNeuerStudent = new Button("Neuen Studenten hinzufügen");
        btnNeuerStudent.addClickListener(e -> {
        	grid.asSingleSelect().clear();
        	form.setStudent(new Student());
        });
        
        HorizontalLayout neuStudentLayout = new HorizontalLayout(filteringLayout, btnNeuerStudent);
        
        
        grid.setColumns("vorname", "nachname", "semester");
        
        HorizontalLayout mainLayout = new HorizontalLayout(grid, form);
        mainLayout.setSizeFull();
        
        grid.setSizeFull();
        mainLayout.setExpandRatio(grid,1);
        
       layout.addComponents(neuStudentLayout, mainLayout);
       updateList();
        
        setContent(layout);
        
        form.setVisible(false);
        
        grid.asSingleSelect().addValueChangeListener(e -> {
        	if(e.getValue() == null) form.setVisible(false);
        	else form.setStudent(e.getValue());
        });
    }

	public void updateList() {
		List<Student> studenten = service.findAll(filterText.getValue());
		grid.setItems(studenten);
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
