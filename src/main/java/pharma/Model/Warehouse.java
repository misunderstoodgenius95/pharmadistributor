package pharma.Model;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;

public class Warehouse {
private  int id;
private String nome;
private PGgeometry pGgeometry;

    public Warehouse(int id, String nome, PGgeometry pGgeometry) {
        this.id = id;
        this.nome = nome;
        this.pGgeometry = pGgeometry;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public PGgeometry getpGgeometry() {
        return pGgeometry;
    }
}
