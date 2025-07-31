package pharma.Model;

import algo.ShelfInfo;
import net.postgis.jdbc.PGgeometry;
import net.postgis.jdbc.geometry.Point;

import java.util.List;

public class Warehouse {
private  int id;
private String nome;
private PGgeometry pGgeometry;
private String address;
private String comune;
private String province;
private List<ShelfInfo> shelfInfos;



    public Warehouse() {

    }

    public Warehouse(int id, String nome, PGgeometry pGgeometry, String address, String province, String comune,List<ShelfInfo> shelfInfos) {
        this.id = id;
        this.nome = nome;
        this.pGgeometry = pGgeometry;
        this.address = address;
        this.province = province;
        this.comune = comune;
        this.shelfInfos=shelfInfos;
    }
    public Warehouse(int id, String nome, PGgeometry pGgeometry,List<ShelfInfo> shelfInfos) {
        this.id = id;
        this.nome = nome;
        this.pGgeometry = pGgeometry;
        this.shelfInfos=shelfInfos;
    }

    public Warehouse(int id, String nome, PGgeometry pGgeometry) {
        this.id = id;
        this.nome = nome;
        this.pGgeometry = pGgeometry;

    }



    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setpGgeometry(PGgeometry pGgeometry) {
        this.pGgeometry = pGgeometry;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public List<ShelfInfo> getShelfInfos() {
        return shelfInfos;
    }

    public void setShelfInfos(List<ShelfInfo> shelfInfos) {
        this.shelfInfos = shelfInfos;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getAddress() {
        return address;
    }

    public String getComune() {
        return comune;
    }

    public String getProvince() {
        return province;
    }
}
