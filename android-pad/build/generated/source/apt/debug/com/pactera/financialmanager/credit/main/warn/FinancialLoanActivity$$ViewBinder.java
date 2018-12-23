// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.warn;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FinancialLoanActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.warn.FinancialLoanActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131298516, "field 'tvFdHandleman'");
    target.tvFdHandleman = finder.castView(view, 2131298516, "field 'tvFdHandleman'");
    view = finder.findRequiredView(source, 2131297205, "field 'etxtFdSubmit'");
    target.etxtFdSubmit = finder.castView(view, 2131297205, "field 'etxtFdSubmit'");
    view = finder.findRequiredView(source, 2131296462, "field 'btnFdSubmit' and method 'onViewClicked'");
    target.btnFdSubmit = finder.castView(view, 2131296462, "field 'btnFdSubmit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296972, "field 'dataRecyclerView'");
    target.dataRecyclerView = finder.castView(view, 2131296972, "field 'dataRecyclerView'");
    view = finder.findRequiredView(source, 2131296461, "field 'btnFdSearch' and method 'onViewClicked'");
    target.btnFdSearch = finder.castView(view, 2131296461, "field 'btnFdSearch'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296981, "field 'dealWith'");
    target.dealWith = finder.castView(view, 2131296981, "field 'dealWith'");
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txt = null;
    target.tvFdHandleman = null;
    target.etxtFdSubmit = null;
    target.btnFdSubmit = null;
    target.dataRecyclerView = null;
    target.btnFdSearch = null;
    target.dealWith = null;
  }
}
