// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BusinessDetails$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.search.BusinessDetails> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131296797, "field 'crmDuebillInfo' and method 'onViewClicked'");
    target.crmDuebillInfo = finder.castView(view, 2131296797, "field 'crmDuebillInfo'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296794, "field 'crmBusinessCountract' and method 'onViewClicked'");
    target.crmBusinessCountract = finder.castView(view, 2131296794, "field 'crmBusinessCountract'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
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
    view = finder.findRequiredView(source, 2131297255, "field 'frameLayoutBusinessDetails'");
    target.frameLayoutBusinessDetails = finder.castView(view, 2131297255, "field 'frameLayoutBusinessDetails'");
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txt = null;
    target.crmDuebillInfo = null;
    target.crmBusinessCountract = null;
    target.crmCustomerInfo = null;
    target.frameLayoutBusinessDetails = null;
  }
}
