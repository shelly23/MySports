
	 
	/*
     *	This content is generated from the API File Info.
     *	(Alt+Shift+Ctrl+I).
     *
     *	@desc
     *	@file 		log_in_page
     *	@date 		Saturday 01st of October 2022 02:28:35 PM
     *	@title 		Page 1
     *	@author
     *	@keywords
     *	@generator 	Export Kit v1.3.figma
     *
     */


    package com.example.mysports.activities;

    import android.app.Activity;
    import android.graphics.Color;
    import android.os.Build;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ImageView;

    import androidx.annotation.RequiresApi;

    import com.example.mysports.R;
    import com.github.mikephil.charting.charts.BarChart;
    import com.github.mikephil.charting.charts.LineChart;
    import com.github.mikephil.charting.components.XAxis;
    import com.github.mikephil.charting.components.YAxis;
    import com.github.mikephil.charting.data.BarData;
    import com.github.mikephil.charting.data.BarDataSet;
    import com.github.mikephil.charting.data.BarEntry;
    import com.github.mikephil.charting.data.Entry;
    import com.github.mikephil.charting.data.LineData;
    import com.github.mikephil.charting.data.LineDataSet;
    import com.github.mikephil.charting.formatter.ValueFormatter;
    import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

    import java.sql.Date;
    import java.util.ArrayList;
    import java.util.Comparator;
    import java.util.List;

    import persistence.daos.FBDayDAO;
    import persistence.daos.FBMonthDAO;
    import persistence.dtos.User;
    import persistence.validators.TextValidator;
    import service.DayService;
    import service.DayServiceImpl;

    public class statistik_activity extends Activity {

        private final ArrayList<String> xLabel = new ArrayList<>();
        private LineChart lineChart;
        private BarChart barChart;
        private LineDataSet lineDataSet;
        private LineData lineData;
        private List<Entry> lineEntries;
        private List<BarEntry> barEntries;
        private BarDataSet barDataSet;
        private BarData barData;
        private DayService dayService;
        private User user;
        private String yearStrLine;
        private String yearStrBar;

        private ImageView front1;
        private ImageView front2;
        private ImageView back1;
        private ImageView back2;

        private int currentYearLine;
        private int currentYearBar;

        public static int rgb(String hex) {
            int color = (int) Long.parseLong(hex.replace("#", ""), 16);
            int r = (color >> 16) & 0xFF;
            int g = (color >> 8) & 0xFF;
            int b = (color >> 0) & 0xFF;
            return Color.rgb(r, g, b);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.statistik);

            dayService = new DayServiceImpl(new FBDayDAO(), new FBMonthDAO(), new TextValidator());

            user = (User) getIntent().getSerializableExtra("USER");

            back1 = findViewById(R.id.back1);
            back2 = findViewById(R.id.back2);
            front1 = findViewById(R.id.front1);
            front2 = findViewById(R.id.front2);

            lineChart = findViewById(R.id.linechart);
            lineChart.getLegend().setEnabled(false);
            lineChart.getDescription().setEnabled(true);
            lineChart.getDescription().setPosition(575, 100);
            lineChart.getDescription().setTextSize(15);

            barChart = findViewById(R.id.barchart);
            barChart.getLegend().setEnabled(true);
            barChart.getDescription().setEnabled(true);
            barChart.getDescription().setPosition(575, 100);
            barChart.getDescription().setTextSize(15);
            barChart.setFitBars(true);
            barChart.setDrawValueAboveBar(false);

            Date date = new Date(System.currentTimeMillis());
            currentYearLine = date.getYear();
            currentYearBar = date.getYear();
            yearStrLine = currentYearLine + "";
            yearStrBar = currentYearBar + "";
            yearStrLine = yearStrLine.substring(1);
            yearStrBar = yearStrBar.substring(1);

            xLabel.add("Januar");
            xLabel.add("Februar");
            xLabel.add("März");
            xLabel.add("April");
            xLabel.add("Mai");
            xLabel.add("Juni");
            xLabel.add("Juli");
            xLabel.add("August");
            xLabel.add("September");
            xLabel.add("Oktober");
            xLabel.add("November");
            xLabel.add("Dezember");

            createLineChart(yearStrLine);
            createBarChart(yearStrBar);

            back1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentYearLine = currentYearLine - 1;
                    yearStrLine = currentYearLine + "";
                    yearStrLine = yearStrLine.substring(1);

                    createLineChart(yearStrLine);
                }
            });

            front1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentYearLine = currentYearLine + 1;
                    yearStrLine = currentYearLine + "";
                    yearStrLine = yearStrLine.substring(1);
                    createLineChart(yearStrLine);
                }
            });
            back2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentYearBar = currentYearBar - 1;
                    yearStrBar = currentYearBar + "";
                    yearStrBar = yearStrBar.substring(1);
                    createBarChart(yearStrBar);
                }
            });
            front2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentYearBar = currentYearBar + 1;
                    yearStrBar = currentYearBar + "";
                    yearStrBar = yearStrBar.substring(1);
                    createBarChart(yearStrBar);
                }
            });


        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        private void createLineChart(String yearStr) {
            try {
                lineEntries = dayService.getActiveDays(Integer.parseInt(yearStr), user.getId());
                if (lineEntries.size() < 12) {
                    for (int i = 0; i < 12; i++) {
                        final float temp = (float) i;
                        if (lineEntries.stream().noneMatch(entry -> (entry.getX() == temp))) {
                            lineEntries.add(new Entry(temp, 0));
                        }
                    }
                }
                lineEntries.sort(new Comparator<Entry>() {
                    @Override
                    public int compare(Entry entry, Entry t1) {
                        if (entry.getX() < t1.getX()) {
                            return -1;
                        }

                        if (entry.getX() == t1.getX()) {
                            return 0;
                        }

                        if (entry.getX() > t1.getX()) {
                            return 1;
                        }
                        return 0;
                    }
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelRotationAngle(45);
            xAxis.setLabelCount(12, true);

            YAxis yAxis = lineChart.getAxisLeft();
            lineChart.getAxisRight().setEnabled(false);

            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return xLabel.get((int) value);
                }
            });

            yAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "%";
                }
            });

            lineDataSet = new LineDataSet(lineEntries, "");
            lineData = new LineData(lineDataSet);
            lineChart.setData(lineData);
            lineChart.invalidate();
            lineChart.getDescription().setText("20" + yearStr);
            lineDataSet.setValueTextColor(Color.BLACK);
            lineDataSet.setColor(rgb("#F2994A"));
            lineDataSet.setCircleColor(R.color.ellipse_27_ek3_color);
            lineDataSet.setFillColor(R.color.ellipse_27_ek3_color);
            lineDataSet.setLineWidth(3f);
            lineDataSet.setValueTextSize(18f);

            lineDataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return "";
                }
            });


        }

        private void createBarChart(String yearStr) {
            try {
                barEntries = dayService.getTrainings(Integer.parseInt(yearStr), user.getId());
                if (barEntries.size() < 12) {
                    for (int i = barEntries.size(); i < 12; i++) {
                        barEntries.add(new BarEntry((float) i, 0));
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setLabelRotationAngle(45);
            xAxis.setLabelCount(12, true);

            YAxis yAxis = lineChart.getAxisLeft();
            lineChart.getAxisRight().setEnabled(false);

            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    if (value < 0) {
                        return "";
                    }
                    return xLabel.get((int) value);
                }
            });

            yAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return (int) value + "%";
                }
            });

            barDataSet = new BarDataSet(barEntries, "");
            barDataSet.setStackLabels(new String[]{"Kraft", "Ausdauer", "Entspannung / Aktivierung"});
            barDataSet.setColors(rgb("#F2994A"), rgb("#3EC28F"), rgb("#342FFF"));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(barDataSet);

            barData = new BarData(dataSets);

            barChart.setData(barData);
            barChart.invalidate();
            barChart.getDescription().setText("20" + yearStr);

        }

        private void getEntries() {
            lineEntries = new ArrayList<>();
            lineEntries.add(new Entry(0f, 50));
            lineEntries.add(new Entry(1f, 10));
            lineEntries.add(new Entry(2f, 10));
            lineEntries.add(new Entry(3f, 30));
            lineEntries.add(new Entry(4f, 40));
            lineEntries.add(new Entry(5f, 30));
            lineEntries.add(new Entry(6f, 30));
            lineEntries.add(new Entry(7f, 20));
            lineEntries.add(new Entry(8f, 0));
            lineEntries.add(new Entry(9f, 10));
            lineEntries.add(new Entry(10f, 30));
            lineEntries.add(new Entry(11f, 70));
        }
    }
	
	