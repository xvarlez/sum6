package me.varlez.sum6;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import lombok.Data;

@Data
public class SumViewModel extends ViewModel {
    private final ObservableField<String> numberOne = new ObservableField<>("2");
    private final ObservableField<String> numberTwo = new ObservableField<>("4");
    private final ObservableField<String> result = new ObservableField<>("12");


}
