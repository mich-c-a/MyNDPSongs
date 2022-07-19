package sg.edu.rp.c346.id21025446.myndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class editSongs extends AppCompatActivity {

    EditText etTitle;
    EditText etSinger;
    EditText etYear;
    RadioGroup rgRating;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;
    RadioButton rbSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_songs);
        etTitle = findViewById(R.id.etTitle);
        etSinger = findViewById(R.id.etSinger);
        etYear = findViewById(R.id.etYear);
        rgRating = findViewById(R.id.rgRating);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        etTitle.setText(data.getTitle());
        etSinger.setText(data.getSingers());
        etYear.setText(String.valueOf(data.getYear()));

        for (int x = 0; x <  rgRating.getChildCount(); x++){
            RadioButton selectedBtn = (RadioButton)rgRating.getChildAt(x);
            if(Integer.parseInt(selectedBtn.getText().toString()) == data.getStars()){
                rgRating.check(selectedBtn.getId());
            }
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(editSongs.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSinger.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                rbSelected = findViewById(rgRating.getCheckedRadioButtonId());
                data.setStars(Integer.parseInt(rbSelected.getText().toString()));
                dbh.updateSong(data);
                dbh.close();
                Intent intent = new Intent(editSongs.this, listSongs.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbh = new DBHelper(editSongs.this);
                dbh.deleteSong(data.getId());
                Intent intent = new Intent(editSongs.this, listSongs.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editSongs.this, listSongs.class);
                startActivity(intent);
            }
        });
    }
}