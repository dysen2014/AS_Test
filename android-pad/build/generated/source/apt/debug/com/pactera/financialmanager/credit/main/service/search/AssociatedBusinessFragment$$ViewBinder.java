// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AssociatedBusinessFragment$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.search.AssociatedBusinessFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298012, "field 'pullLoadMore'");
    target.pullLoadMore = finder.castView(view, 2131298012, "field 'pullLoadMore'");
    view = finder.findRequiredView(source, 2131297934, "field 'pgb'");
    target.pgb = finder.castView(view, 2131297934, "field 'pgb'");
    view = finder.findRequiredView(source, 2131298538, "field 'tvHideData'");
    target.tvHideData = finder.castView(view, 2131298538, "field 'tvHideData'");
  }

  @Override public void unbind(T target) {
    target.pullLoadMore = null;
    target.pgb = null;
    target.tvHideData = null;
  }
}
