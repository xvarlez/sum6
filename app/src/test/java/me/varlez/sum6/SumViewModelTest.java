package me.varlez.sum6;

import android.databinding.ObservableField;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.BehaviorSubject;

import static com.google.common.truth.Truth.assertThat;

public class SumViewModelTest {

    private static TestScheduler scheduler = new TestScheduler();
    private SumViewModel viewModel;

    @BeforeClass
    public static void setupClass() {
        RxJavaPlugins.setComputationSchedulerHandler(__ -> scheduler);
    }

    @Before
    public void setUp() {
        viewModel = new SumViewModel();
    }

    @Test
    public void modelIsNotNull() {
        assertThat(viewModel.getSum()).isNotNull();
    }

    @Test
    public void resultIsUpdated() {
        // Given
        viewModel.getNumberOne().set("6");
        viewModel.getNumberTwo().set("4");
        viewModel.getNumberThree().set("5");
        viewModel.getNumberFour().set("5");
        viewModel.getNumberFive().set("7");
        viewModel.getNumberSix().set("3");

        // When
        scheduler.advanceTimeTo(500, TimeUnit.MILLISECONDS);

        // Then
        assertThat(viewModel.getResult().get()).isEqualTo("30");
    }

    @Test
    public void exceptionShowsError() {
        // Given
        Exception exception = new RuntimeException("Exception");
        viewModel.numbersObservable = Observable.error(exception);

        // When
        viewModel.subscribe();

        // Then
        assertThat(viewModel.getResult().get()).isEqualTo("ERROR");
    }

    @Test
    public void toIntObservable_returnsRightValue() {
        // Given
        ObservableField<String> input = new ObservableField<>("8");
        TestObserver<Integer> observer = new TestObserver<>();

        // When
        Observable<Integer> output = viewModel.toIntObservable(input);
        output.subscribe(observer);

        // Then
        observer.assertNoErrors();
        observer.assertNotComplete();
        observer.assertValue(8);
    }

    @Test
    public void toIntObservable_badInput_returnsZero() {
        // Given
        ObservableField<String> input = new ObservableField<>("");
        TestObserver<Integer> observer = new TestObserver<>();

        // When
        Observable<Integer> output = viewModel.toIntObservable(input);
        output.subscribe(observer);

        // Then
        observer.assertNoErrors();
        observer.assertNotComplete();
        observer.assertValue(0);
    }

    @Test
    public void isResultAnimated_isInitializedTo_false() {
        // Given
        BehaviorSubject<Boolean> isResultAnimatedSubject = viewModel.getResultAnimationSubject();
        TestObserver<Boolean> observer = new TestObserver<>();

        // When
        isResultAnimatedSubject.subscribe(observer);

        // Then
        observer.assertNoErrors();
        observer.assertNotComplete();
        observer.assertValueCount(1);
        observer.assertValue(false);
    }

    @Test
    public void resultTouched_emits_invertedBoolean() {
        // Given
        BehaviorSubject<Boolean> isResultAnimatedSubject = viewModel.getResultAnimationSubject();
        TestObserver<Boolean> observer = new TestObserver<>();

        // When
        isResultAnimatedSubject.subscribe(observer);
        viewModel.resultTouched(null);

        // Then
        observer.assertNoErrors();
        observer.assertNotComplete();
        observer.assertValueCount(2);
        observer.assertValues(false, true);
    }

}