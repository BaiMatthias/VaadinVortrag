package my.vaadin.app;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class StudentForm extends FormLayout {
	
	private TextField vorname = new TextField("Vorname");
	private TextField nachname = new TextField("Nachname");
	private TextField semester = new TextField("Semester");
	
	private NativeSelect<StudentStatus> status = new NativeSelect<>("Status");
	
	private Button btnspeichern = new Button("Speichern");
	private Button btnloeschen = new Button("Löschen");
	
	private StudentService service = StudentService.getInstance();
	private Student student;
	private MyUI myUI;
	private Binder<Student> binder = new Binder<>(Student.class);
	
	public StudentForm(MyUI myUI){
		this.myUI = myUI;
		setSizeUndefined();
		HorizontalLayout buttons = new HorizontalLayout(btnspeichern, btnloeschen);
		addComponents(vorname, nachname, semester, status, buttons);
		
		status.setItems(StudentStatus.values());
	    btnspeichern.setStyleName(ValoTheme.BUTTON_FRIENDLY);
	    btnloeschen.setStyleName(ValoTheme.BUTTON_DANGER);
	    btnspeichern.setClickShortcut(KeyCode.ENTER);
	    
	    binder.bindInstanceFields(this);
	    
	    btnspeichern.addClickListener(e -> speichereStudent());
	    btnloeschen.addClickListener(e -> loescheStudent());
	    
	}

	public void setStudent(Student student) {
		this.student = student;
		this.student.setStatus(status.getValue());
		binder.setBean(student);
		
		btnloeschen.setVisible(student.isVorhanden());
		setVisible(true);
		vorname.selectAll();
	}
	
	private void loescheStudent(){
		service.loescheStudent(student);
		myUI.updateList();
		setVisible(false);
	}
	
	private void speichereStudent(){
		service.speichereStudent(student);
		myUI.updateList();
		setVisible(false);
	}
	
	
	

}
