package me.varlez.sum6;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.varlez.sum6.databinding.ActivitySumBinding;

public class SumActivity extends AppCompatActivity {

    private SumViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySumBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sum);
        viewModel = ViewModelProviders.of(this).get(SumViewModel.class);

        binding.setViewModel(viewModel);
    }
}
