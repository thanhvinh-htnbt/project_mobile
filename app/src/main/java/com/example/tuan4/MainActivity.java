package com.example.tuan4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] names = {" Nguyen Van A", " Nguyen Van B", " Nguyen Van C", " Nguyen Van D",
            " Nguyen Van E", " Nguyen Van F", " Nguyen Van G", " Nguyen Van H"};
    String[] phones = {"1", "2", "3", "4", "11", "22", "33", "44"};
    Integer[] icons = {R.drawable.ic_1, R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_1, R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_1, R.drawable.ic_2};
    ListView myListView; 
    TextView txtMsg;
    Button btn_gen;
    EditText edt_gen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomIconLabelAdapter information = new CustomIconLabelAdapter(this, R.layout.custom_layout_information, names
        , phones, icons);
//        information.getView()
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        myListView = (ListView) findViewById(R.id.my_list);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                txtMsg.setText("you choose: " + information.names[position]);
            }
        });

        ArrayAdapter<CustomIconLabelAdapter> aa = new ArrayAdapter<CustomIconLabelAdapter>(this, R.layout.custom_layout_information);
        myListView.setAdapter(aa);

        btn_gen = (Button) findViewById(R.id.btn_gen);
        edt_gen = (EditText) findViewById((R.id.edt_gen));

        btn_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        }//onCreate




}

class CustomIconLabelAdapter extends ArrayAdapter<String> {
    Context context;
    String[] names;
    String[] phones;
    Integer[] icons;
    public CustomIconLabelAdapter(Context context, int layoutToBeInflated, String[] names, String[] phones, Integer[] icons) {
        super(context, R.layout.custom_layout_information, names);
        this.context = context;
        this.names = names;
        this.phones = phones;
        this.icons = icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_layout_information, null);
        TextView name = (TextView) row.findViewById(R.id.tv_name);
        TextView phone = (TextView) row.findViewById(R.id.tv_phone);
        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        name.setText(names[position]);
        phone.setText(phones[position]);
        icon.setImageResource(icons[position]);
        return (row);
    }
} // CustomAdapter

