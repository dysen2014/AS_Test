// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CorrelationActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.search.CorrelationActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298702, "field 'txtBack'");
    target.txtBack = finder.castView(view, 2131298702, "field 'txtBack'");
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
    view = finder.findRequiredView(source, 2131298065, "field 'rbtn0' and method 'onViewClicked'");
    target.rbtn0 = finder.castView(view, 2131298065, "field 'rbtn0'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298066, "field 'rbtn1' and method 'onViewClicked'");
    target.rbtn1 = finder.castView(view, 2131298066, "field 'rbtn1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297253, "field 'fragmentCorrelation'");
    target.fragmentCorrelation = finder.castView(view, 2131297253, "field 'fragmentCorrelation'");
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.rbtn0 = null;
    target.rbtn1 = null;
    target.fragmentCorrelation = null;
  }
}
