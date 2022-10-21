// import com.opencsv.CSVReader;
// import com.opencsv.CSVReaderBuilder;

// import java.io.FileReader;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.*;

// public class InventoryAnalysis {

//     private static List<String[]> initial_data;
//     private static String[] header_data;
//     final static Dictionary<String, String[]> data = new Hashtable<>();
//     final static String filepath = "./dummy_data.csv";
//     final static String filename = "dummy_data.csv";
//     private static String[][] rows;
//     private static String[] ids;

//     public static void main(String[] args){
//         load();
//         save();


// //        check Data


// //        check Cols
// //        System.out.println(Arrays.toString(header_data));


// //        MainUI x = new MainUI(rows,header_data,filename);

// //        check Rows
// //        for (String[] each: rows
// //        ) {
// //            System.out.println(Arrays.toString(each));
// //        }
//     }

//     public static int[] getKeys(){
//         int[] temp = new int[data.size()];
//         int count = 0;
//         for (Enumeration<String> k = data.keys(); k.hasMoreElements();)
//         {
//             temp[count] = (Integer.parseInt(k.nextElement()));
//             count++;
//         }
//         Arrays.sort(temp);
//         return temp;
//     }




//     public static void add(String id ,String name, String currentStock, String sellPrice, String buyPrice, String shipTimeMonths, String shipTimeDays){
//         for (String each: ids
//         ) {
//             if (each.equals(id)) System.out.println("ID exists");
//             else data.put(id, new String[]{name, currentStock,sellPrice,buyPrice,shipTimeMonths,shipTimeDays});
//         }

//     }

//     public static void edit(String id, String name, String currentStock, String sellPrice, String buyPrice, String shipTimeMonths, String shipTimeDays){
//         remove(id);
//         add(id,name, currentStock,sellPrice,buyPrice,shipTimeMonths,shipTimeDays);
//     }

//     public static void remove(String id){
//         data.remove(id);
//     }

//     public static void load()
//     {
//         try {
//             // Create an object of file reader
//             // class with CSV file as a parameter.
//             FileReader filereader = new FileReader(filepath);

//             // create csvReader object and skip first Line
//             CSVReader csvReader = new CSVReaderBuilder(filereader).build();
//             header_data = csvReader.readNext();
//             initial_data = csvReader.readAll();

//             rows = new String[initial_data.size()][7];
//             ids = new String[initial_data.size()];

//             for (int i = 0; i < initial_data.size(); i++) {
//                 String[] temp = initial_data.get(i);
//                 rows[i] = temp;
//                 ids[i] = temp[0];
//                 data.put(temp[0], Arrays.copyOfRange(temp, 1, temp.length));

//             }

//         }
//         catch (Exception e) {
//             System.out.println("here");
//             e.printStackTrace();
//         }
//     }

//     public static void save(){
//         String output = Arrays.toString(header_data).replace("[","").replace("]","")+"\n";
//         int[] keys = getKeys();

//         for (int key:keys) output+=(key +","+ Arrays.toString(data.get(String.valueOf(key))).replace("]","").replace("[","")+"\n");

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
// }


