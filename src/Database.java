import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database
{
    List<Product> products;
    String filepath;
    public Database(String filename) {
        this.products = loadCSV(filename);
        this.filepath = filename;
    }
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

            bw.write("id,name,currentStock,sellPrice,buyPrice,shipTimeDays");
            bw.newLine();
            for (Product p : products)
            {
                bw.write(p.getId() + "," + p.getName() + "," + p.getCurrentStock() + "," + p.getSellPrice() + "," + p.getBuyPrice() + "," + p.getShipTimeDays());
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
    
    public void addProduct(Product p) {
        products.add(p);
    }
    public void removeProduct(Product p) {
        products.remove(p);
    }
    @Override
    public String toString() {
        String out = "";
        for (Product i : products)
            out += i.toString() + "\n";
        return out;
    }
    private static Product createProduct(String[] metadata)
    {
        int id = Integer.parseInt(metadata[0]);
        String name = metadata[1];
        int currentStock = Integer.parseInt(metadata[2]);
        double sellPrice = Double.parseDouble(metadata[3]);;
        double buyPrice = Double.parseDouble(metadata[4]);
        int shipTimeDays = Integer.parseInt(metadata[5]);
        
        return new Product(id, name, currentStock, sellPrice, buyPrice, shipTimeDays);
    }
    
    public static List<Product> getProductById(int id, List<Product> products)
    {
        List<Product> searchList = new ArrayList<>();
        
        for (Product p : products)
        {
            if (p.getId() == id)
                searchList.add(p);
        }
    
        return searchList;
    }

    public static Product[] getProductsAsArray(List<Product> products){
        return (Product[]) products.stream().toArray();
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