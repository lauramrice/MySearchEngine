package com.example.mysearchengine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.common.api.internal.BaseImplementation;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ResultsAdapter(Context context, int resource){
        super(context,resource);
    }

    public void add(ResultsModel object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ResultHolder resultHolder;

        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            resultHolder = new ResultHolder();
            resultHolder.tx_name = (TextView) row.findViewById(R.id.tx_name);
            resultHolder.tx_snippet = (TextView)row.findViewById(R.id.tx_snippet);
            resultHolder.tx_link = (TextView)row.findViewById(R.id.tx_link);
            row.setTag(resultHolder);

        }
        else{
            resultHolder = (ResultHolder) row.getTag();
        }
        ResultsModel resultsModel = (ResultsModel) this.getItem(position);

        resultHolder.tx_name.setText(resultsModel.getTitle());
        resultHolder.tx_snippet.setText(resultsModel.getSnippet());
        resultHolder.tx_link.setText(resultsModel.getLink());

        return row;
    }

    static class ResultHolder{
        TextView tx_name, tx_snippet, tx_link;
    }
}
