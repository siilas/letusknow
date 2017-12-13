package br.com.silas.letusknow.component;

import android.content.Context;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

import br.com.silas.letusknow.utils.ColorUtils;

public class LetUsKnowChart extends BarChart {

    private Integer index;
    private List<IBarDataSet> valores;

    public LetUsKnowChart(Context context) {
        super(context);
        getAxisLeft().setAxisMinimum(0);
        getAxisRight().setAxisMinimum(0);
        getXAxis().setEnabled(false);
        LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, 500);
        setLayoutParams(layout);
        startValues();
    }

    public void setTitle(String title) {
        Description description = new Description();
        description.setText(title);
        setDescription(description);
    }

    public void addInformacao(Integer value, String description) {
        List<BarEntry> valor = new ArrayList<>();
        valor.add(new BarEntry(index, value));
        BarDataSet set = new BarDataSet(valor, description);
        set.setColor(ColorUtils.getRandomColor());
        valores.add(set);
        index++;
    }

    public void concluir() {
        BarData data = new BarData(valores);
        setData(data);
        invalidate();
        startValues();
    }

    private void startValues() {
        index = 0;
        valores = new ArrayList<>();
    }

}
