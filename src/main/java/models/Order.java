package models;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrder;
//    @Column(name = "id_user")
//    private int idUser;
//    @Column(name = "id_furniture")
//    private int idFurniture;
    @Column(name = "amount")
    private int amount;
    @Column(name = "total_cost")
    private double totalCost;
    @Column(name = "date_order")
    private Date dateOrder;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_delivery")
    private Delivery delivery;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_furniture")
    private Furniture furniture;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;


    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return user.getIdUser();
    }

    public void setIdUser(int idUser) {
        this.user.setIdUser(idUser);
    }

    public int getIdFurniture() {
        return furniture.getIdFurniture();
    }

    public void setIdFurniture(int idFurniture) {
        this.furniture.setIdFurniture( idFurniture);
    }
    public int getIdDelivery() {
        return delivery.getIdDelivery();
    }

    public void setIdDelivery(int idDelivery) {
        this.delivery.setIdDelivery( idDelivery);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

//    public int getIdDelivery() {
//        return idDelivery;
//    }
//
//    public void setIdDelivery(int idDelivery) {
//        this.idDelivery = idDelivery;
//    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void setFurniture(Furniture furniture) {
        this.furniture = furniture;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString()
    {
        return "idOrder: "+ idOrder+
                " amount: "+ amount;
    }
}
