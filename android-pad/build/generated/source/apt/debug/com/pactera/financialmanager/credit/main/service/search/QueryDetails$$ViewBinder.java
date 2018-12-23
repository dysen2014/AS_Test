// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class QueryDetails$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.search.QueryDetails> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131296796, "field 'crmCustomerInfo' and method 'onViewClicked'");
    target.crmCustomerInfo = finder.castView(view, 2131296796, "field 'crmCustomerInfo'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297510, "field 'keyman' and method 'onViewClicked'");
    target.keyman = finder.castView(view, 2131297510, "field 'keyman'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297635, "field 'loanafter' and method 'onViewClicked'");
    target.loanafter = finder.castView(view, 2131297635, "field 'loanafter'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296795, "field 'crmCreditProve' and method 'onViewClicked'");
    target.crmCreditProve = finder.castView(view, 2131296795, "field 'crmCreditProve'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296798, "field 'crmEntOwner' and method 'onViewClicked'");
    target.crmEntOwner = finder.castView(view, 2131296798, "field 'crmEntOwner'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297257, "field 'frameLayoutTabDetails'");
    target.frameLayoutTabDetails = finder.castView(view, 2131297257, "field 'frameLayoutTabDetails'");
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txt = null;
    target.crmCustomerInfo = null;
    target.keyman = null;
    target.loanafter = null;
    target.crmCreditProve = null;
    target.crmEntOwner = null;
    target.frameLayoutTabDetails = null;
  }
}
