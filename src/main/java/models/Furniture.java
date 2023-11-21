package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "furnitures")
public class Furniture implements Serializable {
    @Id
    @Column(name = "id_furniture")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFurniture;
//    @Column(name = "id_provider")
//    private int idProvider;
    @Column(name = "name_furniture")
    private String nameFurniture;
    @Column(name = "price")
    private double price;
    @Column(name = "amount_stock")
    private int amounStock;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_provider")
    private Provider provider;
    @OneToMany(mappedBy = "furniture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> oreders;

    public int getIdProvider(){return provider.getIdProvider();};
    public void setIdProvider(int id){ provider.setIdProvider(id);};

    public int getIdFurniture() {
        return idFurniture;
    }

    public void setIdFurniture(int idFurniture) {
        this.idFurniture = idFurniture;
    }

//    public int getIdProvider() {
//        return idProvider;
//    }
//
//    public void setIdProvider(int idProvider) {
//        this.idProvider = idProvider;
//    }

    public String getNameFurniture() {
        return nameFurniture;
    }

    public void setNameFurniture(String nameFurniture) {
        this.nameFurniture = nameFurniture;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmounStock() {
        return amounStock;
    }

    public void setAmounStock(int amounStock) {
        this.amounStock = amounStock;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<Order> getOreders() {
        return oreders;
    }

    public void setOreders(List<Order> oreders) {
        this.oreders = oreders;
    }
}
