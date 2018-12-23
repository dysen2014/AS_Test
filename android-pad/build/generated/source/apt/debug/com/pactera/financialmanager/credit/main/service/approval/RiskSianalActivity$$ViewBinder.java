// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.approval;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RiskSianalActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.approval.RiskSianalActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298702, "field 'txtBack'");
    target.txtBack = finder.castView(view, 2131298702, "field 'txtBack'");
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
    view = finder.findRequiredView(source, 2131298741, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131298741, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131298697, "field 'txt'");
    target.txt = finder.castView(view, 2131298697, "field 'txt'");
    view = finder.findRequiredView(source, 2131298012, "field 'pullLoadMore'");
    target.pullLoadMore = finder.castView(view, 2131298012, "field 'pullLoadMore'");
    view = finder.findRequiredView(source, 2131298538, "field 'tvHideData'");
    target.tvHideData = finder.castView(view, 2131298538, "field 'tvHideData'");
    view = finder.findRequiredView(source, 2131298744, "field 'uberPgsview'");
    target.uberPgsview = finder.castView(view, 2131298744, "field 'uberPgsview'");
    view = finder.findRequiredView(source, 2131296477, "field 'btnSubmit' and method 'onViewClicked'");
    target.btnSubmit = finder.castView(view, 2131296477, "field 'btnSubmit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txt = null;
    target.pullLoadMore = null;
    target.tvHideData = null;
    target.uberPgsview = null;
    target.btnSubmit = null;
  }
}
