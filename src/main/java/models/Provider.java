package models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "providers")
public class Provider implements Serializable {
    @Id
    @Column(name = "id_provider")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProvider;
    @Column(name = "country")
    private String country;
    @Column(name = "company")
    private String company;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "provider", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Furniture> furnitures;

    public int getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(int idProvider) {
        this.idProvider = idProvider;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Furniture> getFurnitures() {
        return furnitures;
    }

    public void setFurnitures(List<Furniture> furnitures) {
        this.furnitures = furnitures;
    }
    public String toString()
    {
        return "idProvider: "+ idProvider+
                " Country: "+ country+
                " Company: "+ company+
                " Email: "+ email;
    }

}
