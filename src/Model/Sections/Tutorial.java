package Model.Sections;

import java.io.Serializable;

public class Tutorial extends Section  implements Serializable {
    public Tutorial(String sectionCode, int capacity, int enrollment, String professor) {
        super(sectionCode, capacity, enrollment, professor);
    }
}
