package co.davidcastellanos.courseplatform.repository;

import co.davidcastellanos.courseplatform.model.Course;
import co.davidcastellanos.courseplatform.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findAll();

    List<Course> findByAssignedUsersNotContains(User user);

    List<Course> findAllByAssignedUsers(User user);

    void deleteById(Long id);

}
