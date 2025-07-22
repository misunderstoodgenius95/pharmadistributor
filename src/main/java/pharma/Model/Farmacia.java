package pharma.Model;

import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;

public class Farmacia {
    private String nome;
    private int id;
    private PGgeometry coordianate;

    public Farmacia(String nome, int id, PGgeometry coordianate) {
        this.nome = nome;
        this.id = id;
        this.coordianate = coordianate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCoordianate(PGgeometry coordianate) {
        this.coordianate = coordianate;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public PGgeometry getCoordianate() {
        return coordianate;
    }

   /* @Override
    public boolean equals(Object obj) {
     if(this==obj) return  true;
     if(obj==null) return false;
     if(getClass()!=obj.getClass()) return false;
     Farmacia other=(Farmacia) obj;
     return  other.getId() == this.getId() &&
             other.getNome().equals(this.getNome()) &&
             other.getCoordianate()==this.getCoordianate();




    }*/
}
