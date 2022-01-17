package codegym.service;

import codegym.model.ClassRoom;
import codegym.repository.ClassRoomRepo;

import java.util.List;

public interface IClassRoomService {
    List<ClassRoom> findAll();

}
