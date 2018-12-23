// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.common.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WarnMainAdapter$TabAdapter$ContentViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.common.adapters.WarnMainAdapter.TabAdapter.ContentViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298345, "field 'tabImg'");
    target.tabImg = finder.castView(view, 2131298345, "field 'tabImg'");
    view = finder.findRequiredView(source, 2131298357, "field 'tabName'");
    target.tabName = finder.castView(view, 2131298357, "field 'tabName'");
    view = finder.findRequiredView(source, 2131297581, "field 'llLay'");
    target.llLay = finder.castView(view, 2131297581, "field 'llLay'");
  }

  @Override public void unbind(T target) {
    target.tabImg = null;
    target.tabName = null;
    target.llLay = null;
  }
}
