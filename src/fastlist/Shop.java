
package fastlist;

import java.time.LocalDate;


public class Shop {
    
    private int tfId;
    private String tfName;
    private String tfKategorie;
    private String tfMarke;
    private LocalDate tfDatum;
    private int tfPreis;
    private String tfOrt;

    public Shop(int tfId, String tfName, String tfKategorie, String tfMarke, LocalDate tfDatum, int tfPreis, String tfOrt) {
        this.tfId = tfId;
        this.tfName = tfName;
        this.tfKategorie = tfKategorie;
        this.tfMarke = tfMarke;
        this.tfDatum = tfDatum;
        this.tfPreis = tfPreis;
        this.tfOrt = tfOrt;
    }

 public String getProduktname() {
    return tfName;
}

public String getKategorie() {
    return tfKategorie;
}

    public int getId() {
        return tfId;
    }

public String getMarke() {
    return tfMarke;
}

public LocalDate getAblaufdatum() {
    return tfDatum;
}

public int getVerkaufspreis() {
    return tfPreis;
}

public String getLagerort() {
    return tfOrt;
}

    
}
