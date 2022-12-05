import com.notification.NotificationFactory;
import com.notification.NotificationManager;
import com.notification.NotificationFactory.Location;
import com.notification.manager.SimpleManager;
import com.platform.Platform;
import com.theme.ThemePackagePresets;
import com.utils.Time;

import java.awt.Color;
import java.util.*;

public class InventoryAnalysis
{
    final static Dictionary<String, String[]> data = new Hashtable<>();
    final static String filepath = "./dummy_data.csv";
    final static String filename = "dummy_data.csv";

    public static void main(String[] args) {}

    public static int[] getKeys()
    {
        int[] temp = new int[data.size()];
        int count = 0;
        for (Enumeration<String> k = data.keys(); k.hasMoreElements();)
        {
            temp[count] = (Integer.parseInt(k.nextElement()));
            count++;
        }
        Arrays.sort(temp);
        return temp;
    }

    public static void CheckStock(List<Product> products, MainUI ui)
    {
        for (Product each:products)
        {
            if (each.getCurrentStock() < 5)
            {
                ui.log(String.format("Inventory very low (< 5), you should order more stock for %s.", each.getName()));
                // Custom system notification utilizing the open source library found here:
                // https://github.com/spfrommer/JCommunique
                Platform.instance().setAdjustForPlatform(true);
                // Notification builder
                NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
                factory.addBuilder(Notification.class, new Notification.CustomBuilder());
                // Add the Notification
                NotificationManager manager = new SimpleManager(Location.NORTH);
                manager.addNotification(factory.build(Notification.class, "Low Inventory Warning", "Inventory very low for " + each.getName(), Color.RED), Time.infinite());
            }
            else if (each.getCurrentStock() < 30)
            {
                ui.log(String.format("Consider ordering more stock for %s, stock is < 30.", each.getName()));
                // Custom system notification utilizing the open source library found here:
                // https://github.com/spfrommer/JCommunique
                Platform.instance().setAdjustForPlatform(true);
                // Notification builder
                NotificationFactory factory = new NotificationFactory(ThemePackagePresets.cleanLight());
                factory.addBuilder(Notification.class, new Notification.CustomBuilder());
                // Add the Notification
                NotificationManager manager = new SimpleManager(Location.NORTH);
                manager.addNotification(factory.build(Notification.class, "Low Inventory Warning", "Inventory getting low for " + each.getName(), Color.ORANGE), Time.infinite());
            }
        }
    }

    public static void CheckDuplicates(List<Product> products)
    {
        for (int i = 0; i < products.size(); i++)
        {
            for (int j = 0; j < products.size(); j++)
            {
                if (i == j) continue;
                if (products.get(i).getName().equals(products.get(j)))
                    System.out.printf("There is a duplicate product with the name : %s \t with id : %d",products.get(i).getName(),products.get(i).getId());
            }
        }
    }

    public static void totalInventoryCost(List<Product> products)
    {
        double total_selling = 0.0;
        double total_buying = 0.0;
        for (Product each: products)
        {
            total_selling+= each.getCurrentStock()*each.getSellPrice();
            total_buying+= each.getCurrentStock()*each.getBuyPrice();
        }
        System.out.println("The current inventory selling value is "+ total_selling);
        System.out.println("The current inventory buying value is "+ total_buying);
    }
}


