package Model.Sections;

import java.io.Serializable;

public class Lecture extends Section  implements Serializable {
    public Lecture(String sectionCode, int capacity, int enrollment, String professor) {
        super(sectionCode, capacity, enrollment, professor);
    }
}
