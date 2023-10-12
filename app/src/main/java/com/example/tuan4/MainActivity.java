package com.example.tuan4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    String[] names = {" Nguyen Van A", " Nguyen Van B", " Nguyen Van C", " Nguyen Van D",
//            " Nguyen Van E", " Nguyen Van F", " Nguyen Van G", " Nguyen Van H"};
//    String[] phones = {"1", "2", "3", "4", "11", "22", "33", "44"};
//    Integer[] icons = {R.drawable.ic_1, R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_1, R.drawable.ic_2,R.drawable.ic_3,R.drawable.ic_1, R.drawable.ic_2};
    ListView myListView; 
    TextView txtMsg;
    Button btn_gen;
    EditText edt_gen;
    CustomIconLabelAdapter information;
    String[] pages;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CustomIconLabelAdapter information = new CustomIconLabelAdapter(this, R.layout.custom_layout_information, names
//        , phones, icons);
//        information.getView()
        txtMsg = (TextView) findViewById(R.id.txtMsg);

//khi người dùng nhấn vào item list view
        myListView = (ListView) findViewById(R.id.my_list);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int position, long id) {
                int i = information.getPosition(position);
                if(i>= information.count) return;
                txtMsg.setText("You choose: " + information.names[i]);
            }
        });

//        ArrayAdapter<CustomIconLabelAdapter> aa = new ArrayAdapter<CustomIconLabelAdapter>(this, R.layout.custom_layout_information, (List<CustomIconLabelAdapter>) information);
//        myListView.setAdapter(information);

        btn_gen = (Button) findViewById(R.id.btn_gen);
        edt_gen = (EditText) findViewById((R.id.edt_gen));
        spinner = (Spinner) findViewById(R.id.cbb_page);

// khi người dùng nhấn gen
        btn_gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(edt_gen.getText().toString());
                information = new CustomIconLabelAdapter(MainActivity.this, R.layout.custom_layout_information, n,5);
                myListView.setAdapter(information);
//                tạo Spinner hiển thị số trang
                pages = new String[information.maxPage];
                for (int i = 0;i<pages.length;i++)
                {
                    pages[i] = "Page: " + (i+1);
                    ;
                    spinner.setAdapter(new ArrayAdapter<String >(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, pages));
                }
            }
        });

        spinner.setOnItemSelectedListener(this);

        }//onCreate


//    khi người dùng chọn trang bất kỳ
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        int n = information.count - information.lineInPage*i;
//        if(n < information.lineInPage)
//        {
//            information.clear();
//            information.addAll(new String[n]);
//        }
//        else{
//
//        }
        information.page = i;
        myListView.setAdapter(information);

    }

//    nothing
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class CustomIconLabelAdapter extends ArrayAdapter<String> {
    private static Random random = new Random();
    public int lineInPage;
    Context context;
    String[] names;
    String[] phones;
    Integer[] icons;
    int count;//số lượng item người dùng gen
    int page = 0; //page hiện hành
    int maxPage = 1;// số lượng page tối đa
//    hàm tạo với tham số đầu vào
    public CustomIconLabelAdapter(Context context, int layoutToBeInflated, String[] names, String[] phones, Integer[] icons) {
        super(context, R.layout.custom_layout_information, phones);
        this.context = context;
        this.names = names;
        this.phones = phones;
        this.icons = icons;
        this.maxPage = 1;
        this.lineInPage = names.length;
        this.count = names.length;
    }
//    random ngẫu nhiên n dòng dữ liệu , với mỗi trang hiển thị m dòng (theo đề thì 5 dòng)
    public CustomIconLabelAdapter(Context context, int layoutToBeInflated, int n, int m) {
        super(context, R.layout.custom_layout_information, new String[m]);
        this.context = context;
        this.names = getRandomNames(n);
        this.phones = getRandomPhoneNumbers(n);
        Integer[] faces = {R.drawable.face_1, R.drawable.face_2, R.drawable.face_3,
                R.drawable.face_4, R.drawable.face_5, R.drawable.face_6};
        this.icons = getRandomFace(faces, n);
//        maxPage = (int) Math.ceil(n/m);
        count = n;
        maxPage = 0;
        while(n>0)
        {
            n-=m;
            maxPage++;
        }
        this.lineInPage = m;
    }

//    hàm getView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int i = getPosition(position);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_layout_information, null);
        if(i >= count) return row;
        TextView name = (TextView) row.findViewById(R.id.tv_name);
        TextView phone = (TextView) row.findViewById(R.id.tv_phone);
        ImageView icon = (ImageView) row.findViewById(R.id.icon);
        name.setText(names[i]);
        phone.setText(phones[i]);
        icon.setImageResource(icons[i]);
        return (row);
    }

//    hàm nhập vào vị trí item trên màn hình và trả về vị trí thực sự của item trong mảng
//    vd đang ở page 5, vị trí item trên màn hình là position = 2
//    suy ra vị trí của item trong mảng là 2 + 5*5 = 27
    public int getPosition(int position)
    {
        return position + this.page * this.lineInPage;
    }

//    random 1 mảng gồm n họ và tên
    public static String[] getRandomNames(int n) {
        String[] ho = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Huỳnh", "Phan", "Vũ", "Võ", "Đặng"};
        String[] tenDem = {"Văn", "Thị", "Hữu", "Xuân", "Minh", "Thanh", "Đức", "Thịnh", "Hải", "Quang"};
        String[] ten = {"Anh", "Mai", "Dũng", "Linh", "Hương", "Hải", "Thảo", "Bình", "Quốc", "Ngọc"};
        String[] randomNames = new String[n];

        for (int i = 0; i < n; i++) {
            String randomHo = ho[random.nextInt(ho.length)];
            String randomTenDem = tenDem[random.nextInt(tenDem.length)];
            String randomTen = ten[random.nextInt(ten.length)];

            String fullName = randomHo + " " + randomTenDem + " " + randomTen;
            randomNames[i] = fullName;
        }

        return randomNames;
    }
//    random 1 mảng gồm n số điện thoại
    public static String[] getRandomPhoneNumbers(int n) {
        String[] randomPhoneNumbers = new String[n];

        for (int i = 0; i < n; i++) {
            randomPhoneNumbers[i] = "0";

            for (int j = 0; j < 9; j++) {
                int a = random.nextInt(10); // Sinh ngẫu nhiên một chữ số từ 0 đến 9
                randomPhoneNumbers[i]+= a;
            }

        }

        return randomPhoneNumbers;
    }
//    random 1 mảng gồm n ảnh đại diện
    public static Integer[] getRandomFace(Integer[] faces, int n) {
        Integer[] randomFaces = new Integer[n];

        for (int i = 0; i < n; i++) {
            int a = random.nextInt(faces.length);
            randomFaces[i] = faces[a];
        }

        return randomFaces;
    }
} // CustomAdapter

