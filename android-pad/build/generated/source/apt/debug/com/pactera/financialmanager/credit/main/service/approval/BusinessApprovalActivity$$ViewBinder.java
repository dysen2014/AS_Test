// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.approval;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BusinessApprovalActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.approval.BusinessApprovalActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131298333, "field 'tab0' and method 'onViewClicked'");
    target.tab0 = finder.castView(view, 2131298333, "field 'tab0'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298334, "field 'tab1' and method 'onViewClicked'");
    target.tab1 = finder.castView(view, 2131298334, "field 'tab1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298337, "field 'tab2' and method 'onViewClicked'");
    target.tab2 = finder.castView(view, 2131298337, "field 'tab2'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298338, "field 'tab3' and method 'onViewClicked'");
    target.tab3 = finder.castView(view, 2131298338, "field 'tab3'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298339, "field 'tab4' and method 'onViewClicked'");
    target.tab4 = finder.castView(view, 2131298339, "field 'tab4'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297609, "field 'llTab'");
    target.llTab = finder.castView(view, 2131297609, "field 'llTab'");
    view = finder.findRequiredView(source, 2131298012, "field 'pullLoadMore'");
    target.pullLoadMore = finder.castView(view, 2131298012, "field 'pullLoadMore'");
    view = finder.findRequiredView(source, 2131298538, "field 'tvHideData'");
    target.tvHideData = finder.castView(view, 2131298538, "field 'tvHideData'");
    view = finder.findRequiredView(source, 2131298744, "field 'uberPgsview'");
    target.uberPgsview = finder.castView(view, 2131298744, "field 'uberPgsview'");
    view = finder.findRequiredView(source, 2131296477, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131296477, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txt = null;
    target.tab0 = null;
    target.tab1 = null;
    target.tab2 = null;
    target.tab3 = null;
    target.tab4 = null;
    target.llTab = null;
    target.pullLoadMore = null;
    target.tvHideData = null;
    target.uberPgsview = null;
    target.btnSubmit = null;
  }
}
