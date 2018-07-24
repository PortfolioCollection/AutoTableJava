package Model.Sections;

import java.io.Serializable;

public class Practical extends Section  implements Serializable {

    public Practical(String sectionCode, int capacity, int enrollment, String professor) {
        super(sectionCode, capacity, enrollment, professor);
    }
}
