package com.example.thermonitor;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    int[] images   = { R.drawable.c , R.drawable.php , R.drawable.java , R.drawable.javascript , R.drawable.cc, R.drawable.python , R.drawable.sql };
    String[] names = { "c" , "php" , "java" , "java script" , "c++" , "Python" , "SQL" };

    private FirebaseAuth firebaseAuth;
    private TextView     textViewUserE;
    private Button       buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUserE = (TextView)findViewById(R.id.textViewUserE);
        buttonLogOut  = (Button)findViewById(R.id.buttonLogOut);

        buttonLogOut.setOnClickListener(this);

        firebaseAuth  = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()== null){
            finish();
            startActivity(new Intent(getApplicationContext(),Login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserE.setText("Welcome \n" + user.getEmail());


        ListView listView = (ListView)findViewById(R.id.ListView);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Intent intent0=new Intent(view.getContext(),C.class);
                    startActivity(intent0);
                }
                if (position==1){
                    Intent intent1=new Intent(view.getContext(),PHP.class);
                    startActivity(intent1);
                }
                if (position==2){
                    Intent intent2=new Intent(view.getContext(),Java.class);
                    startActivity(intent2);
                }
                if (position==3){
                    Intent intent3=new Intent(view.getContext(),JavaScript.class);
                    startActivity(intent3);
                }
                if (position==4){
                    Intent intent4=new Intent(view.getContext(),C_plus_plus.class);
                    startActivity(intent4);
                }
                if (position==5){
                    Intent intent5=new Intent(view.getContext(),Python.class);
                    startActivity(intent5);
                }
                if (position==6){
                    Intent intent6=new Intent(view.getContext(),SQL.class);
                    startActivity(intent6);
                }

            }
        });

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length ;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.customlayout,null);
            ImageView imageView=(ImageView)convertView.findViewById(R.id.imageView);
            TextView textView=(TextView)convertView.findViewById(R.id.textView);
            imageView.setImageResource(images[position]);
            textView.setText(names[position]);
            return convertView;
        }
    }

    @Override
    public void onClick(View v) {

        if (v == buttonLogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this , Login.class));
        }


    }
}
