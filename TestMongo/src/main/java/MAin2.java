import java.util.*;

public class MAin2 {

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        for (int i=1; i<= 5; i++){
            Student stu = new Student(i);
            students.add(stu);
        }

        System.out.println(students.stream().mapToInt(Student::getPoint).sum());
    }

    private static class Student{
        private int point;

        public Student(int point) {
            this.point = point;
        }

        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }
    }
}
