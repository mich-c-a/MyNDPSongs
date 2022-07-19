package sg.edu.rp.c346.id21025446.myndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class listSongs extends AppCompatActivity {
    Button btnlv;
    ListView lvSongs;
    ArrayList<Song> al;
    ArrayList<Song> alRate;
    ArrayList<Song> alNotFive;
    CustomAdapter aa;
    public boolean filterOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_songs);

        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        btnlv = findViewById(R.id.btnlv);
        lvSongs = findViewById(R.id.lvSongs);

        al = new ArrayList<Song>();
        alRate = new ArrayList<Song>();
        alNotFive = new ArrayList<Song>();

//        aa = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1,al);
        aa = new CustomAdapter(this,R.layout.row,al);
        lvSongs.setAdapter(aa);

        DBHelper dbh = new DBHelper(listSongs.this);
        al.addAll(dbh.getAllSongs());


        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song data = al.get(i);
                Intent intent = new Intent(listSongs.this, editSongs.class);
                intent.putExtra("data", data);
                startActivity(intent);

            }
        });

        btnlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterOn = !filterOn;

                if (filterOn) {
                    for (int i = 0; i < al.size(); i++) {
                        if (al.get(i).getStars() != 5) {
                            alNotFive.add(al.get(i));
                        }
                    }
                    al.removeAll(alNotFive);

                }
                else {
                    al.clear();
                    al.addAll(dbh.getAllSongs());

                }
                aa.notifyDataSetChanged();
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(listSongs.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();
    }
}