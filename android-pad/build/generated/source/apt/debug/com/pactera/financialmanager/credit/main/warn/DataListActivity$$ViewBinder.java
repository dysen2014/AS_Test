// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.warn;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DataListActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.warn.DataListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296972, "field 'dataRecyclerView'");
    target.dataRecyclerView = finder.castView(view, 2131296972, "field 'dataRecyclerView'");
    view = finder.findRequiredView(source, 2131298741, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131298741, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
  }

  @Override public void unbind(T target) {
    target.dataRecyclerView = null;
    target.txtTitle = null;
    target.layBack = null;
  }
}
