package dbms.hibernate.relashonship.many_to_many;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import static dbms.hibernate.HibernateUtils.find;

public class CourseRepository {
    static void remove(Session session, Course removableCourse) {
        find(session, Course.class, removableCourse.getId()).ifPresent(
            entity -> {
                removeLikes(session, removableCourse);
                session.remove(entity);
            });
    }

    private static void removeLikes(Session session, Course removableCourse) {
        NativeQuery nativeQuery = session.createNativeQuery("DELETE FROM course_likes cl WHERE cl.course_id = ?");
        nativeQuery.setParameter(1, removableCourse.getId());
        nativeQuery.executeUpdate();
    }
}
