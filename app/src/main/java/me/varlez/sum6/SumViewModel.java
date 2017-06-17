package me.varlez.sum6;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.View;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import lombok.Data;
import me.varlez.sum6.util.RxUtils;

@Data
public class SumViewModel extends ViewModel {
    private static final String TAG = "SumViewModel";

    private final ObservableField<String> numberOne = new ObservableField<>("2");
    private final ObservableField<String> numberTwo = new ObservableField<>("1");
    private final ObservableField<String> numberThree = new ObservableField<>("2");
    private final ObservableField<String> numberFour = new ObservableField<>("1");
    private final ObservableField<String> numberFive = new ObservableField<>("2");
    private final ObservableField<String> numberSix = new ObservableField<>("1");

    private final ObservableField<String> result = new ObservableField<>("");

    private Boolean isResultAnimated = false;
    private final BehaviorSubject<Boolean> resultAnimationSubject = BehaviorSubject.createDefault(isResultAnimated);


    @VisibleForTesting
    Observable<String> numbersObservable;

    private Sum sum;

    public SumViewModel() {
        this.sum = new Sum();

        List<Observable<Integer>> list = Arrays.asList(
                toIntObservable(numberOne),
                toIntObservable(numberTwo),
                toIntObservable(numberThree),
                toIntObservable(numberFour),
                toIntObservable(numberFive),
                toIntObservable(numberSix));

        numbersObservable = Observable.combineLatest(list,
                values -> String.valueOf(sum.compute(values)))
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

    public void resultTouched(View view) {
        isResultAnimated = !isResultAnimated;
        resultAnimationSubject.onNext(isResultAnimated);
    }

}
