package com.example.roomlibraryexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST_CODE = 100;
    private ImageView imgCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edtTitle = findViewById(R.id.edtName), edtAmt = findViewById(R.id.edtAmt);
        AppCompatButton btnAdd = findViewById(R.id.btn);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = edtTitle.getText().toString();
                String amount = edtAmt.getText().toString();

                if(title.isEmpty() || amount.isEmpty() ) {
                    Toast.makeText(MainActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseHelper.expenseDao().addTransaction(
                        new Expense(title , amount)
                );

                edtTitle.setText("");
                edtAmt.setText("");


                List<Expense> arrExpenses = databaseHelper.expenseDao().getAllExpense();
                for( Expense expense : arrExpenses)
                    Log.d("Data", "Title = " + expense.getTitle() + " Amount = " + expense.getAmount());
            }
        });

        imgCamera = findViewById(R.id.imgCamera);
        AppCompatButton btnCamera = findViewById(R.id.btnCamera);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                iCamera.addCategory(Intent.ACTION_PICK);
//                iCamera.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(Intent.createChooser(iCamera,"Camera"), CAMERA_REQUEST_CODE);
                startActivityForResult(iCamera, CAMERA_REQUEST_CODE);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {

            if(requestCode == CAMERA_REQUEST_CODE) {
                Log.d("Camera", CAMERA_REQUEST_CODE+"");

                Bitmap img= (Bitmap) data.getExtras().get("data");
                imgCamera.setImageBitmap(img);
            }

        }

    }
}