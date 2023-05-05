package ru.mirea.maksimov.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import ru.mirea.maksimov.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            String input = binding.inputEditText.getText().toString();

                            // Проверяем, что введенное значение не является пустым
                            float totalPairs = 0;
                            if (!input.isEmpty()) {
                                totalPairs = Integer.parseInt(input);
                            }

                            // Если оба значения были введены, то рассчитываем среднее количество пар в день
                            if (totalPairs != 0) {
                                float averagePairsPerDay = (totalPairs / 26);
                                binding.resultTextView.setText(String.valueOf(averagePairsPerDay));
                            }
                        }
                    }
                }).start();
            }
        });
    }
}