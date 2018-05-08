package fiap.com.br.ceepapp.model;

import java.io.Serializable;

public class Nota implements Serializable {

    private final String title;
    private final String description;

    public Nota(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}