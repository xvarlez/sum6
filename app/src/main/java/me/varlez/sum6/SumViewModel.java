package me.varlez.sum6;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.Data;
import me.varlez.sum6.util.RxUtils;

@Data
public class SumViewModel extends ViewModel {
    private static final String TAG = "SumViewModel";

    private final ObservableField<String> numberOne = new ObservableField<>("2");
    private final ObservableField<String> numberTwo = new ObservableField<>("1");
    private final ObservableField<String> result = new ObservableField<>("");

    @VisibleForTesting
    Observable<String> numbersObservable;

    private Sum sum;

    public SumViewModel() {
        this.sum = new Sum();

        numbersObservable = Observable.combineLatest(
                toIntObservable(numberOne),
                toIntObservable(numberTwo),
                (i, i2) -> String.valueOf(sum.compute(i, i2)))
                .debounce(400, TimeUnit.MILLISECONDS);

        subscribe();
    }

    @VisibleForTesting
    void subscribe() {
        numbersObservable.subscribe(result::set, throwable -> {
                    result.set("ERROR");
                    Log.e(TAG, "error: ", throwable);
                });
    }

    @VisibleForTesting
    Observable<Integer> toIntObservable(ObservableField<String> field) {
        return RxUtils.toObservable(field)
                .map(stringValue -> {
                    try {
                        return Integer.valueOf(stringValue);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                });
    }

}
