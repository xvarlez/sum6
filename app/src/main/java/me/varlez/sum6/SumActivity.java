package me.varlez.sum6;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import io.reactivex.disposables.Disposable;
import me.varlez.sum6.databinding.ActivitySumBinding;

public class SumActivity extends AppCompatActivity {

    private SumViewModel viewModel;
    private Disposable blinkingDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySumBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sum);
        viewModel = ViewModelProviders.of(this).get(SumViewModel.class);

        binding.setViewModel(viewModel);

        blinkingDisposable = viewModel.getResultAnimationSubject()
                .subscribe(isAnimated -> {
                    if (isAnimated) {
                        binding.textviewResult.startAnimation(AnimationUtils.loadAnimation(this, R.anim.blinking));
                    } else {
                        binding.textviewResult.clearAnimation();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        if (!blinkingDisposable.isDisposed()) {
            blinkingDisposable.dispose();
        }
        super.onDestroy();
    }
}
