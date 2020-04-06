package aslapov.android.study.pallada.kisuknd.raids.presenter;

public interface IBasePresenter<V> {
    void attachView(V view);
    void detachView();
}
