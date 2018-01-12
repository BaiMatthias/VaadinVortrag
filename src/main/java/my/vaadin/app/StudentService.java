package my.vaadin.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentService {

	
	private static StudentService instance;
	private static final Logger LOGGER = Logger.getLogger(StudentService.class.getName());

	private final HashMap<Long, Student> studenten = new HashMap<>();
	private long nextId = 0;

	private StudentService() {
	}

	
	public static StudentService getInstance() {
		if (instance == null) {
			instance = new StudentService();
			instance.ensureTestData();
		}
		return instance;
	}

	
	public synchronized List<Student> findAll() {
		return findAll(null);
	}

	
	public synchronized List<Student> findAll(String stringFilter) {
		ArrayList<Student> arrayList = new ArrayList<>();
		for (Student student : studenten.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| student.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(student.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}

	
	public synchronized List<Student> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Student> arrayList = new ArrayList<>();
		for (Student student : studenten.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| student.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(student.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(StudentService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	
	public synchronized long count() {
		return studenten.size();
	}

	
	public synchronized void loescheStudent(Student value) {
		studenten.remove(value.getId());
	}

	
	public synchronized void speichereStudent(Student entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Fehler: Null");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Student) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		studenten.put(entry.getId(), entry);
	}

	
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
					"Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
					"Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
					"Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
					"Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
					"Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
					"Jaydan Jackson", "Bernard Nilsen" };
			Random r = new Random(0);
			for (String name : names) {
				String[] split = name.split(" ");
				Student c = new Student();
				c.setVorname(split[0]);
				c.setNachname(split[1]);
				c.setStatus(StudentStatus.values()[r.nextInt(StudentStatus.values().length)]);
				int s = r.nextInt(12)+1;
				c.setSemester(String.valueOf(s));
				speichereStudent(c);
			}
		}
	}
}
