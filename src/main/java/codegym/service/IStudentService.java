package codegym.service;

import codegym.model.Student;

import java.util.List;

public interface IStudentService {
    public List<Student> findAll();
    public void save (Student student);
    public void delete (long id);
    public Student findById (long id);

}
