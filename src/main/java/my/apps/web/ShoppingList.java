package my.apps.web;
import my.apps.db.ShoppingRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

@WebServlet("/shoppingList")
public class ShoppingList extends HttpServlet {

    private int counter;


    private ShoppingRepository shoppingRepository = new ShoppingRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        counter++;

        //get input as string
        String Product = request.getParameter("Product");
        Integer Quantity = Integer.valueOf(request.getParameter("Quantity"));


        // write results to response
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        addStyle(out);

        try {
            Shopping shopping = new Shopping (Product, Quantity);

            out.println("<h2>Product added</h2>");
            shoppingRepository.insert(shopping);

        } catch (ClassNotFoundException e) {
            out.println("<div class='error'><b>Unable initialize database connection<b></div>");
        } catch (SQLException e) {
            out.println("<div class='error'><b>Unable to write to database!<b></div>");
        }
        catch (IllegalArgumentException e){
            out.println ("<dif class='error'><b>Unable to parse date! Expected format is yyyy-MM-dd but was " + Quantity);
        }
        addGoBack(out);

        // finished writing, send to browser
        out.close();
    }

    private void addGoBack(PrintWriter out) {out.println("<a href='/'>Go Back</a>");}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counter++;

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<head>");
        out.println("<title> Products </title>");
        addStyle(out);
        out.println("</head>");

        try {
            out.println("<h3>Products</h3>");
                out.println("<table>");
                out.println("<tr>");
                out.println("<th>Product</th>");
                out.println("<th>Quantity</th>");
                out.println("</tr>");

            List<Shopping> shopping = shoppingRepository.read();
                for (Shopping Shopping : shopping) {
                        out.println("<tr>");
                        out.println("<td>"+ Shopping.getProduct() +"</td>");
                        out.println("<td>"+ Shopping.getQuantity()+"</td>");

                out.println("</tr>");
            }

            out.println("</table>");

        } catch (ClassNotFoundException e) {
            out.println("<div class='error'><b>Unable initialize database connection<b></div>");
        } catch (SQLException e) {
            out.println("<div class='error'><b>Unable to write to database! " +  e.getMessage() +"<b></div>");
        }
        out.close();
    }

    private void addStyle(PrintWriter out) {
        out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"/style.css\">");
    }


    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("init() called. Counter is: " + counter);
    }

    @Override
    public void destroy() {
        System.out.println("Destroying Servlet! Counter is:" + counter);
        super.destroy();
    }
}