package codegym.controller;

import codegym.model.ClassRoom;
import codegym.model.Student;
import codegym.service.IClassRoomService;
import codegym.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class StudentController {
    @Autowired
    IStudentService studentService;

    @Autowired
    IClassRoomService classRoomService;

    @GetMapping("/students")
    public ModelAndView showALl() {
        ModelAndView modelAndView = new ModelAndView("/show");
        List<Student> students = studentService.findAll();
        modelAndView.addObject("students", students);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/create");
        modelAndView.addObject("student", new Student());
        List<ClassRoom> classRooms = classRoomService.findAll();
        modelAndView.addObject("classRooms", classRooms);
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute(value = "student") Student student, @RequestParam(value = "classRoomId") Long classRoomId, @RequestParam MultipartFile upImg) {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setId(classRoomId);
        student.setClassRoom(classRoom);

        String fileName = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("/Users/thaojuice/IdeaProjects/MD4-Repository-Student-Management/src/main/webapp/WEB-INF/img/" + fileName));
            student.setImg("/img/" + fileName);
            studentService.save(student);
        } catch (IOException e) {
            e.printStackTrace();
            student.setImg("/img/Onnasis.jpg");
        }
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEdit(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("student", studentService.findById(id));
        modelAndView.addObject("classrooms", classRoomService.findAll());
        return modelAndView;
    }
    @PostMapping("edit")
    public String edit(@ModelAttribute("student") Student student, @RequestParam("idClassroom") Long idClassroom, @RequestParam MultipartFile upImg) {
        ClassRoom classroom = new ClassRoom();
        classroom.setId(idClassroom);
        student.setClassRoom(classroom);
        String nameFile = upImg.getOriginalFilename();
        try {
            FileCopyUtils.copy(upImg.getBytes(), new File("/Users/thaojuice/IdeaProjects/MD4-Repository-Student-Management/src/main/webapp/WEB-INF/img/"+ nameFile) );
            student.setImg("/img/" + nameFile);
            studentService.save(student);
        } catch (IOException e) {
            e.printStackTrace();
            student.setImg("/img/Onnasis.jpg");
            studentService.save(student);
        }
        return "redirect:/students";
    }

    @GetMapping("/{id}/delete")
    public ModelAndView showDelete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("student", studentService.findById(id));
        modelAndView.addObject("classrooms", classRoomService.findAll());
        return modelAndView;
    }

    @PostMapping("delete")
    public String delete(@RequestParam(value = "id") Long id) {
        studentService.delete(id);
        return "redirect:/students";
    }


}
