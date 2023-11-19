package resource.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
public class Row {

    private String name;
    private Map<String, Object> fields;
    private int brojKolona;

    public Row() {
        this.fields = new HashMap<>();
        brojKolona = 0;
    }

    public void addField(String fieldName, Object value) {
        this.fields.put(fieldName, value);
        brojKolona++;
    }

    public void removeField(String fieldName) {
        this.fields.remove(fieldName);
        brojKolona--;
    }

    public int getBrojKolona() {
        return brojKolona;
    }
}
