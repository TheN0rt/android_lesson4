package ru.mirea.maksimov.looper;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.maksimov.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("result"));
            }
        };

        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);
            }
        });

        final Runnable runn = new Runnable() {
            public void run() {
                Log.d(MainActivity.class.getSimpleName(), "Ваш возраст: " + binding.ageInput.getText().toString());
                Log.d(MainActivity.class.getSimpleName(), "Ваша работа: " + binding.workInput.getText().toString());
            }
        };


        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age = Integer.parseInt(binding.ageInput.getText().toString());

                Thread t = new Thread(new Runnable() {
                    public void run() {
                        binding.workInput.postDelayed(runn, 1000 * age);
                    }
                });
                t.start();
            }
        });

    }
}