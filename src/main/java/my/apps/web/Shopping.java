package my.apps.web;

/**
 * Created by Oana on 4/23/2017.
 */
public class Shopping {

    private String Product;
    private Integer Quantity;


    public Shopping(String Product, Integer Quantity) {
        this.Product = Product;
        this.Quantity = Quantity;
    }

    public String getProduct() {
        return Product;
    }

    public Integer getQuantity() {
        return Quantity;
    }


    @Override
    public String toString() {
        return "Shopping{" +
                "Product='" + Product + '\'' +
                ", Quantity='" + Quantity + '\'' +
                '}';
    }
 }

