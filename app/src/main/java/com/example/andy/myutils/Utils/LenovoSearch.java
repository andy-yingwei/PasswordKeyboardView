package com.example.andy.myutils.Utils;
//如果需要在开发中用到RxAndroid，那么需要在
//Android Studio中添加依赖
//build.gradle中的
//dependencies {
//    compile fileTree(dir: 'libs', include: ['*.jar'])
      //支持Rxjava2
//    implementation 'io.reactivex.rxjava2:rxjava:2.2.19'
//    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
//
//}
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.andy.myutils.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;


public class LenovoSearch {

    private Activity mActivity;
    private Context mContext;
    private EditText mEditText;                             //搜索条
    private CustomPopupWindow mCustomPopupWindow;           //显示搜索联想的PopupWindow
    private ListView mListView;                             //搜索联想结果的列表
    private ArrayAdapter mArrayAdapter;                     //ListView的适配器
    private List<String> mSearchList = new ArrayList<>();   //搜索结果的数据源
    private static LenovoSearch lenovoSearch;
    private PublishSubject<String> publishSubject;


    public static LenovoSearch getInstance() {
        if (lenovoSearch == null) {
            lenovoSearch = new LenovoSearch();
        }
        return lenovoSearch;
    }

    public void LenovoSearch(Activity activity, Context context, EditText editText){
        initVariable(activity, context, editText);
        initCustomPopupWindow();
        rxJava();
        AddTextChangedListener(editText);
    }

    private void initVariable(Activity activity, Context context, EditText editText){
        this.mActivity = activity;
        this.mContext = context;
        this.mEditText = editText;
    }

    public void AddTextChangedListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    mCustomPopupWindow.dismiss();
                } else {
                    startSearch(s.toString());
                }
            }
        });

    }

    private void rxJava(){
        //创建被观察者
        publishSubject = PublishSubject.create();
        //200毫秒后没有字符没有改变时才去请求网络
        publishSubject.debounce(200, TimeUnit.MILLISECONDS)
                //filter操作符被观察产生的结果按照指定条件进行过滤，只有满足条件的结果才会提交给订阅者
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        //当搜索词为空时，不发起请求
                        return s.length() > 0;
                    }
                })
                //switchMap操作符会保存最新的提供者所产生的结果而舍弃旧的结果
                .switchMap(new Function<String, ObservableSource<String>>() {

                    @Override
                    public ObservableSource<String> apply(String query) throws Exception {
                        return getSearchObservable(query);
                    }

                })
                //observeOn操作符指定一个观察者在哪个调度器上观察这个被观察者
                .observeOn(AndroidSchedulers.mainThread())
                //subscribe操作符关联被观察与订阅者
                .subscribe(new DisposableObserver<String>() {

                    @Override
                    public void onNext(String s) {
                        //显示搜索联想的结果
                        showSearchResult(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        CompositeDisposable mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(mCompositeDisposable);
    }


    //开始搜索
    private void startSearch(String mString) {
        publishSubject.onNext(mString);
    }

    private Observable<String> getSearchObservable(final String mString) {
        return Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                Log.d("MyLog", "开始请求，关键词为：" + mString);
                try {
                    Thread.sleep(100); //模拟网络请求，耗时100毫秒
                } catch (InterruptedException e) {
                    if (!observableEmitter.isDisposed()) {
                        observableEmitter.onError(e);
                    }
                }
                if (!(mString.contains("科") || mString.contains("耐") || mString.contains("七"))) {
                    //没有联想结果，则关闭pop
                    //mPop.dismiss();
                    return;
                }
                Log.d("SearchActivity", "结束请求，关键词为：" + mString);
                observableEmitter.onNext(mString);
                observableEmitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 显示搜索结果
     */
    private void showSearchResult(String keyWords) {
        mSearchList.clear(); //先清空数据源
        switch (keyWords) {
            case "科比":
                mSearchList.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.kobe)));
                break;
            case "耐克":
                mSearchList.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.nike)));
                break;
            case "七夕":
                mSearchList.addAll(Arrays.asList(mContext.getResources().getStringArray(R.array.qixi)));
                break;
        }
        mArrayAdapter.notifyDataSetChanged();
        mCustomPopupWindow.showAsDropDown(mEditText, 0, 0); //显示搜索联想列表的pop
    }

    private void initCustomPopupWindow() {
        mCustomPopupWindow = new CustomPopupWindow.Builder(mActivity)
                .setContentView(R.layout.pop_search)
                .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setBackgroundAlpha(1f)
                .build();
        mListView = (ListView) mCustomPopupWindow.getItemView(R.id.search_list_lv);
        mArrayAdapter = new ArrayAdapter <String> (mContext, android.R.layout.simple_list_item_1, mSearchList);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mEditText.setText(mSearchList.get(position));
                //Intent intent = new Intent(mActivity, MainActivity.class);
                //intent.putExtra("result", mSearchList.get(position));
                //mActivity.startActivity(intent);
            }
        });
    }

}

