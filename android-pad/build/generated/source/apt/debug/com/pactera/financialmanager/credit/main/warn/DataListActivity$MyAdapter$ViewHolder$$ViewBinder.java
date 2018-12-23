// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.warn;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DataListActivity$MyAdapter$ViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.main.warn.DataListActivity.MyAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298573, "field 'tvName'");
    target.tvName = finder.castView(view, 2131298573, "field 'tvName'");
    view = finder.findRequiredView(source, 2131298663, "field 'tvValue'");
    target.tvValue = finder.castView(view, 2131298663, "field 'tvValue'");
  }

  @Override public void unbind(T target) {
    target.tvName = null;
    target.tvValue = null;
  }
}
