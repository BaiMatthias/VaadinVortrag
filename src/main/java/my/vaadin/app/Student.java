package my.vaadin.app;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class Student implements Serializable, Cloneable {

	
	private Long id;

	private String vorname = "";

	private String nachname = "";

	private StudentStatus status;

	private String semester;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get the value of semester
	 *
	 * @return the value of semester
	 */
	public String getSemester() {
		return this.semester;
	}

	/**
	 * Set the value of semester
	 *
	 * @param semester
	 *            new value of semester
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * Get the value of status
	 *
	 * @return the value of status
	 */
	public StudentStatus getStatus() {
		return this.status;
	}

	/**
	 * Set the value of status
	 *
	 * @param status
	 *            new value of status
	 */
	public void setStatus(StudentStatus status) {
		this.status = status;
	}

	

	/**
	 * Get the value of nachname
	 *
	 * @return the value of nachname
	 */
	public String getNachname() {
		return this.nachname;
	}

	/**
	 * Set the value of nachname
	 *
	 * @param nachname
	 *            new value of nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/**
	 * Get the value of vorname
	 *
	 * @return the value of vorname
	 */
	public String getVorname() {
		return this.vorname;
	}

	/**
	 * Set the value of vorname
	 *
	 * @param vorname
	 *            new value of vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public boolean isVorhanden() {
		return id != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Student && obj.getClass().equals(getClass())) {
			return this.id.equals(((Student) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public Student clone() throws CloneNotSupportedException {
		return (Student) super.clone();
	}

	@Override
	public String toString() {
		return this.vorname + " " + this.nachname;
	}
	
}
