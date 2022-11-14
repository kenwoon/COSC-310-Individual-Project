 import com.opencsv.CSVReader;
 import com.opencsv.CSVReaderBuilder;

 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.sql.SQLOutput;
 import java.util.*;

 public class InventoryAnalysis {

     private static List<String[]> initial_data;
     private static String[] header_data;
     final static Dictionary<String, String[]> data = new Hashtable<>();
     final static String filepath = "./dummy_data.csv";
     final static String filename = "dummy_data.csv";
     private static String[][] rows;
     private static String[] ids;

     public static void main(String[] args){
//         load();
//         save();
//
//
//       //  check Data
//
//         StockCheck();
//      //   check Cols
//         System.out.println(Arrays.toString(header_data));
//
//
//         MainUI x = new MainUI(rows,header_data,filename);
//
//        // check Rows
//         for (String[] each: rows
//         ) {
//             System.out.println(Arrays.toString(each));
//         }
     }

     public static int[] getKeys(){
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

     public static void CheckStock(){
         for (Product each:Database.products
         ) {
            if (each.getCurrentStock()<50) System.out.println("Order more stock");
            if (each.getCurrentStock()<5) System.out.println("Out of Stock");
         }
     }

     public static void CheckDuplicates(){
         for (int i = 0; i < Database.products.size(); i++) {
             for (int j = 0; j < Database.products.size(); j++) {
                 if (i == j) continue;
                 if (Database.products.get(i).getName().equals(Database.products.get(j))){
                     System.out.printf("There is a duplicate product with the name : %s \t with id : %d",Database.products.get(i).getName(),Database.products.get(i).getId());
                 }
             }
         }
     }

     public static void totalInventoryCost(){
         double total_selling = 0.0;
         double total_buying = 0.0;
         for (Product each: Database.products
              ) {
             total_selling+= each.getCurrentStock()*each.getSellPrice();
             total_buying+= each.getCurrentStock()*each.getBuyPrice();
         }
         System.out.println("The current inventory selling value is "+ total_selling);
         System.out.println("The current inventory buying value is "+ total_buying);
     }



//     public static void load()
//     {
//         try {
//             // Create an object of file reader
//             // class with CSV file as a parameter.
//             FileReader filereader = new FileReader(filepath);
//
//             // create csvReader object and skip first Line
//             CSVReader csvReader = new CSVReaderBuilder(filereader).build();
//             header_data = csvReader.readNext();
//             initial_data = csvReader.readAll();
//
//             rows = new String[initial_data.size()][7];
//             ids = new String[initial_data.size()];
//
//             for (int i = 0; i < initial_data.size(); i++) {
//                 String[] temp = initial_data.get(i);
//                 rows[i] = temp;
//                 ids[i] = temp[0];
//                 data.put(temp[0], Arrays.copyOfRange(temp, 1, temp.length));
//
//             }
//
//         }
//         catch (Exception e) {
//             System.out.println("here");
//             e.printStackTrace();
//         }
//     }
//
//     public static void save(){
//         String output = Arrays.toString(header_data).replace("[","").replace("]","")+"\n";
//         int[] keys = getKeys();
//
//         for (int key:keys) output+=(key +","+ Arrays.toString(data.get(String.valueOf(key))).replace("]","").replace("[","")+"\n");
//
//         try {
//             FileWriter myWriter = new FileWriter(filepath);
//             myWriter.write(output);
//             myWriter.close();
//             System.out.println("Successfully wrote to the file.");
//         } catch (IOException e) {
//             System.out.println("An error occurred.");
//             e.printStackTrace();
//         }
//     }
 }


