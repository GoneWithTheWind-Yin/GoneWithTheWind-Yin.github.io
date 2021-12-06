package com.example.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;
import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.HIGradient;
import com.highsoft.highcharts.common.HIStop;
import com.highsoft.highcharts.common.hichartsclasses.*;
import com.highsoft.highcharts.core.HIChartView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class WeeklyDetail extends Fragment {
    private JSONObject weatherData;

    public WeeklyDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle args = getArguments();
        assert args != null;
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_weekly_detail, container, false);
        try {
            String weatherString = args.getString("WeatherData");
            weatherData = new JSONObject(weatherString);
            JSONObject data = weatherData.getJSONObject("data");
            JSONArray timelines = data.getJSONArray("timelines");
            JSONArray intervals = timelines.getJSONObject(0).getJSONArray("intervals");
            Object[][] seriesData = new Object[15][3];
            for (int i = 0; i < 15; ++i) {
                JSONObject tempData = intervals.getJSONObject(i);
                LocalDateTime parsedDate = LocalDateTime.parse(tempData.getString("startTime").substring(0, 19));
                seriesData[i][0] = parsedDate.toInstant(ZoneOffset.of("-8")).toEpochMilli();
                seriesData[i][1] = tempData.getJSONObject("values").getDouble("temperatureMin");
                seriesData[i][2] = tempData.getJSONObject("values").getDouble("temperatureMax");
            }
            HIChartView chartView = (HIChartView) rootView.findViewById(R.id.temperature_chart);
            HIOptions options = new HIOptions();

            HIChart chart = new HIChart();
            chart.setType("arearange");
            chart.setZoomType("x");
            options.setChart(chart);

            HITitle title = new HITitle();
            title.setText("Temperature variation by day");
            options.setTitle(title);

            HIXAxis xaxis = new HIXAxis();
            xaxis.setType("datetime");
            options.setXAxis(new ArrayList<HIXAxis>(){{add(xaxis);}});

            HIYAxis yaxis = new HIYAxis();
            yaxis.setTitle(new HITitle());
            options.setYAxis(new ArrayList<HIYAxis>(){{add(yaxis);}});

            HITooltip tooltip = new HITooltip();
            tooltip.setShadow(true);
            tooltip.setValueSuffix("°F");
            options.setTooltip(tooltip);

            HILegend legend = new HILegend();
            legend.setEnabled(false);
            options.setLegend(legend);

            HIArearange series = new HIArearange();
            LinkedList<HIStop> list = new LinkedList<>();
            list.add(new HIStop(0, HIColor.initWithRGB(240, 183, 132)));
            list.add(new HIStop(1, HIColor.initWithRGB(128, 183, 240)));
            HIColor hiColor = HIColor.initWithLinearGradient(new HIGradient(), list);
            series.setFillColor(hiColor);
            series.setLineColor(HIColor.initWithRGBA(255, 255, 255, 0));
            series.setName("Temperature variation by day");

            series.setData(new ArrayList<>(Arrays.asList(seriesData)));
            options.setSeries(new ArrayList<>(Arrays.asList(series)));

            chartView.setOptions(options);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}