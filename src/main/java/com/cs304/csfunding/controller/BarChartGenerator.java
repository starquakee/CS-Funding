package com.cs304.csfunding.controller;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BarChartGenerator {
  public static void generateBarChart(int[] data, String outputFilePath) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    for (int i = 0; i < data.length; i++) {
      dataset.addValue(data[i], "Value", monthNames[i]);
    }

    JFreeChart chart = ChartFactory.createBarChart("Monthly Data", "Month", "Value", dataset);
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    BarRenderer renderer = (BarRenderer) plot.getRenderer();

    // Set custom colors for the bars
    renderer.setSeriesPaint(0, new Color(0, 0, 255));

    // Show labels for each bar
    renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    renderer.setDefaultItemLabelsVisible(true);
    CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
    renderer.setDefaultItemLabelGenerator(generator);
    renderer.setDefaultPositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
        TextAnchor.BOTTOM_CENTER));

    // Customize axis labels and ticks
    CategoryAxis domainAxis = plot.getDomainAxis();
    domainAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setTickLabelFont(new Font("Arial", Font.PLAIN, 12));

    // Save the chart to a file
    try {
      ChartUtils.saveChartAsPNG(new File(outputFilePath), chart, 1200, 600);
      System.out.println("Bar chart saved to: " + outputFilePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    int[] data = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120};
    String outputFilePath = "bar_chart.png";
    generateBarChart(data, outputFilePath);
  }
}
