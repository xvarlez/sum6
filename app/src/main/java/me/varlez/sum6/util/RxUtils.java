package me.varlez.sum6.util;

import android.databinding.Observable.OnPropertyChangedCallback;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Cancellable;

/**
 * Utility class to transform Android data binding Observable into RxJava Observables.
 * @see <a href="https://github.com/manas-chaudhari/android-mvvm/blob/cd7b90359410ac6af6757a6930db6d238be74c60/android-mvvm/src/main/java/com/manaschaudhari/android_mvvm/FieldUtils.java">https://github.com/manas-chaudhari/android-mvvm/blob/cd7b90359410ac6af6757a6930db6d238be74c60/android-mvvm/src/main/java/com/manaschaudhari/android_mvvm/FieldUtils.java</a>
 */
public final class RxUtils {
    @NonNull
    public static <T> Observable<T> toObservable(@NonNull final ObservableField<T> field) {

        return Observable.create(emitter -> {
            emitter.onNext(field.get());
            final OnPropertyChangedCallback callback = new OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(android.databinding.Observable observable, int i) {
                    emitter.onNext(field.get());
                }
            };

            field.addOnPropertyChangedCallback(callback);
            emitter.setCancellable(() -> field.removeOnPropertyChangedCallback(callback));
        });
    }
}