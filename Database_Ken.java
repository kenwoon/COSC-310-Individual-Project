import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
// import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database
{
    public static void main(String[] args)
    {
        // // Load CSV file
        // List<Product> products = loadCSV("dummy_data.csv");
        
        // // Print products list
        // for (Product p : products)
        // {
        //     System.out.println(p);
        // }

        // // Testing to see if saveCSV works
        // saveCSV("Test.csv", products);

        // // Testing to see if getProduct methods work
        // List<Product> searchList = getProductByStock(50, products);
        // for (Product p1 : searchList)
        // {
        //     System.out.println(p1);
        // }
    }

    public static List<Product> loadCSV(String filename)
    {
        List<Product> products = new ArrayList<>();
        Path pathToFile = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(pathToFile))
        {
            String line = br.readLine();
            line = br.readLine();
            while (line != null)
            {
                String[] attributes = line.split(",");
                Product product = createProduct(attributes);
                products.add(product);
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return products;
    }

    public static void saveCSV(String filename, List<Product> products)
    {
        File file = new File(filename);

        try (FileWriter fw = new FileWriter(file))
        {
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("id,name,currentStock,sellPrice,buyPrice,shipTimeMonths,shipTimeDays");
            bw.newLine();
            for (Product p : products)
            {
                bw.write(p.getId() + "," + p.getName() + "," + p.getCurrentStock() + "," + p.getSellPrice() + "," + p.getBuyPrice() + "," + p.getShipTimeMonths() + "," + p.getShipTimeDays());
                bw.newLine();
            }
            bw.close();
            fw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static Product createProduct(String[] metadata)
    {
        int id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        int currentStock = Integer.parseInt(metadata[2]);
        double sellPrice = Double.parseDouble(metadata[3]);;
        double buyPrice = Double.parseDouble(metadata[4]);
        int shipTimeMonths = Integer.parseInt(metadata[5]);
        int shipTimeDays = Integer.parseInt(metadata[6]);
        
        return new Product(id, name, currentStock, sellPrice, buyPrice, shipTimeMonths, shipTimeDays);
    }

    private static List<Product> getProductById(int id, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getId() == id)
                searchList.add(p);
        }

        return searchList;
    }

    private static List<Product> getProductByName(String name, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getName().equals(name))
                searchList.add(p);
        }

        return searchList;
    }

    private static List<Product> getProductByStock(int currentStock, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getCurrentStock() == currentStock)
                searchList.add(p);
        }

        return searchList;
    }

    private static List<Product> getProductBySellPrice(double sellPrice, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getSellPrice() == sellPrice)
                searchList.add(p);
        }

        return searchList;
    }

    private static List<Product> getProductByBuyPrice(double buyPrice, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getBuyPrice() == buyPrice)
                searchList.add(p);
        }

        return searchList;
    }

    private static List<Product> getProductBySTM(int shipTimeMonths, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getShipTimeMonths() == shipTimeMonths)
                searchList.add(p);
        }

        return searchList;
    }

    private static List<Product> getProductBySTD(int shipTimeDays, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getShipTimeDays() == shipTimeDays)
                searchList.add(p);
        }

        return searchList;
    }
}

class Product
{
    private int id;
    private String name;
    private int currentStock;
    private double sellPrice;
    private double buyPrice;
    private int shipTimeMonths;
    private int shipTimeDays;

    public Product(int id, String name, int currentStock, double sellPrice, double buyPrice, int shipTimeMonths, int shipTimeDays)
    {
        this.id = id;
        this.name = name;
        this.currentStock = currentStock;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.shipTimeMonths = shipTimeMonths;
        this.shipTimeDays = shipTimeDays;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getCurrentStock()
    {
        return currentStock;
    }

    public void setCurrentStock(int currentStock)
    {
        this.currentStock = currentStock;
    }

    public double getSellPrice()
    {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice)
    {
        this.sellPrice = sellPrice;
    }

    public double getBuyPrice()
    {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice)
    {
        this.buyPrice = buyPrice;
    }

    public int getShipTimeMonths()
    {
        return shipTimeMonths;
    }

    public void setShipTimeMonths(int shipTimeMonths)
    {
        this.shipTimeMonths = shipTimeMonths;
    }

    public int getShipTimeDays()
    {
        return shipTimeDays;
    }

    public void setShipTimeDays(int shipTimeDays)
    {
        this.shipTimeDays = shipTimeDays;
    }

    @Override
    public String toString()
    {
        return "Product [id = " + id + ", name = " + name + ", stock = " + currentStock + ", sell price = " + sellPrice + ", buy price = " + buyPrice + ", ship time months = " + shipTimeMonths + ", ship time days = " + shipTimeDays + "]";
    }
}