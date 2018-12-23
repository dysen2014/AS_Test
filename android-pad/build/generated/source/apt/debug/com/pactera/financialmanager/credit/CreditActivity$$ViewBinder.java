// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CreditActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.CreditActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298333, "field 'tab0'");
    target.tab0 = finder.castView(view, 2131298333, "field 'tab0'");
    view = finder.findRequiredView(source, 2131298334, "field 'tab1'");
    target.tab1 = finder.castView(view, 2131298334, "field 'tab1'");
    view = finder.findRequiredView(source, 2131298337, "field 'tab2'");
    target.tab2 = finder.castView(view, 2131298337, "field 'tab2'");
    view = finder.findRequiredView(source, 2131296378, "field 'alphaIndicator'");
    target.alphaIndicator = finder.castView(view, 2131296378, "field 'alphaIndicator'");
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
    view = finder.findRequiredView(source, 2131298741, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131298741, "field 'txtTitle'");
  }

  @Override public void unbind(T target) {
    target.tab0 = null;
    target.tab1 = null;
    target.tab2 = null;
    target.alphaIndicator = null;
    target.layBack = null;
    target.txtTitle = null;
  }
}
