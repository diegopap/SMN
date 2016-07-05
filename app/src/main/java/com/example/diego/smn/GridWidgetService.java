package com.example.diego.smn;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by diego on 05/07/16.
 */
public class GridWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        // TODO Auto-generated method stub
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }

}
