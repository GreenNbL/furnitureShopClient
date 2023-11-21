package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "deliveries")
public class Delivery implements Serializable {
    @Id
    @Column(name = "id_delivery")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDelivery;
    @Column(name = "status")
    private String status;
    @Column(name = "date_delivery")
    private Date dateDelivery;
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    public int getIdDelivery() {
        return idDelivery;
    }

    public void setIdDelivery(int idDelivery) {
        this.idDelivery = idDelivery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public String toString()
    {
        return "idDelivery: "+idDelivery+
                " статус: "+status+
                " дата доставки: "+dateDelivery;
    }
}
