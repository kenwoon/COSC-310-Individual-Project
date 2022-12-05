// The following references the code from: https://www.tutorialspoint.com/jfreechart/jfreechart_bar_chart.htm

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities; 

public class Graph extends JFrame
{  
   public static void CreateGraph(TransactionHistory tran)
   {
      Graph chart = new Graph("Transaction History", "Transaction Date vs Quantity", tran);
      chart.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      chart.pack();        
      RefineryUtilities.centerFrameOnScreen(chart);        
      chart.setVisible(true);
   }

   public Graph(String applicationTitle , String chartTitle, TransactionHistory tran)
   {
      super(applicationTitle);        
      JFreeChart barChart = ChartFactory.createBarChart
      (
         chartTitle,           
         "Date",            
         "Quantity",            
         createDataset(tran),          
         PlotOrientation.VERTICAL,           
         true, true, false
      );   
      ChartPanel chartPanel = new ChartPanel(barChart);        
      chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));        
      setContentPane(chartPanel); 
   }
   
   private CategoryDataset createDataset(TransactionHistory tran)
   {
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

      for (Transaction t : tran.transactions)
         dataset.addValue(t.getQuantity(), t.getName(), t.getDate());

      return dataset; 
   }
}