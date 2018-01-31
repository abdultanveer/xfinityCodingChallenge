package com.xfinity.ui.main;

/**
 * Created by Ansari on 1/31/2018.
 */

public class MainPresenter implements IMainPresenter {
    IMainView iMainView;

    public MainPresenter(MainActivity mainActivity){
        iMainView = mainActivity;
    }

    @Override
    public void onMenuItemToggleSelected() {
        iMainView.toggleList();
    }
}
