package sg.edu.rp.c346.id21025446.myndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.assist.AssistContent;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etTitle;
    EditText etSinger;
    EditText etYear;
    Button btnInsert;
    Button btnShow;
    RadioGroup rgRating;
    RadioButton rbSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        btnInsert = findViewById(R.id.btnInsert);
        btnShow = findViewById(R.id.btnShow);
        rgRating = findViewById(R.id.rgRating);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String singer = etSinger.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                rbSelected = findViewById(rgRating.getCheckedRadioButtonId());
                int stars = Integer.parseInt(rbSelected.getText().toString());

                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertSong(title, singer, year, stars);

                if (inserted_id != -1){
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity.this, "Insert unsuccessful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                Intent intent = new Intent(MainActivity.this, listSongs.class);
                startActivity(intent);
            }
        });
    }
}