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

public class TransactionHistory
{
    List<Transaction> transactions;
    String filepath;

    public TransactionHistory(String filename) {
        this.transactions = loadCSV(filename);
        this.filepath = filename;
    }
    
    public TransactionHistory() {
        this.transactions = new ArrayList<Transaction>();
        this.filepath = "";
    }

    public static List<Transaction> loadCSV(String filename)
    {
        List<Transaction> transactions = new ArrayList<>();
        Path pathToFile = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(pathToFile))
        {
            String line = br.readLine();
            line = br.readLine();
            while (line != null)
            {
                String[] attributes = line.split(",");
                Transaction transaction = createTransaction(attributes);
                transactions.add(transaction);
                line = br.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return transactions;
    }

    public static void saveCSV(String filename, List<Transaction> transactions)
    {
        File file = new File(filename);

        try (FileWriter fw = new FileWriter(file))
        {
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("date,name,quantity");
            bw.newLine();
            for (Transaction t : transactions)
            {
                bw.write(t.getDate() + "," + t.getName() + "," + t.getQuantity());
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
    
    public void addTransaction(Transaction t)
    {
        transactions.add(t);
    }

    @Override
    public String toString()
    {
        String out = "";
        for (Transaction i : transactions)
            out += i.toString() + "\n";
        return out;
    }

    private static Transaction createTransaction(String[] metadata)
    {
        String date = metadata[0];
        String name = metadata[1];
        int quantity = Integer.parseInt(metadata[2]);
        
        return new Transaction(date, name, quantity);
    }
    
    public static Transaction[] getTransactionsAsArray(List<Transaction> transactions)
    {
        return (Transaction[]) transactions.stream().toArray();
    }
}