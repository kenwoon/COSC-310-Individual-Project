// The following references the code from: https://www.tutorialspoint.com/jfreechart/jfreechart_bar_chart.htm

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class Graph extends ApplicationFrame
{  
   public static void CreateGraph()
   {
      Graph chart = new Graph("Transaction History", "Transaction Date vs Quantity");
      chart.pack();        
      RefineryUtilities.centerFrameOnScreen(chart);        
      chart.setVisible(true);
   }

   public Graph(String applicationTitle , String chartTitle)
   {
      super(applicationTitle);        
      JFreeChart barChart = ChartFactory.createBarChart
      (
         chartTitle,           
         "Date",            
         "Quantity",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false
      );   
      ChartPanel chartPanel = new ChartPanel(barChart);        
      chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));        
      setContentPane(chartPanel); 
   }
   
   private CategoryDataset createDataset()
   {
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      TransactionHistory tran = new TransactionHistory("dummy_transactions.csv");

      for (Transaction t : tran.transactions)
         dataset.addValue(t.getQuantity(), t.getName(), t.getDate());

      return dataset; 
   }
}