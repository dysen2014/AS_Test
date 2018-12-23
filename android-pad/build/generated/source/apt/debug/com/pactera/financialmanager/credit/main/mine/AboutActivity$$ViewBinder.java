// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.mine;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AboutActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.mine.AboutActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298702, "field 'txtBack'");
    target.txtBack = finder.castView(view, 2131298702, "field 'txtBack'");
    view = finder.findRequiredView(source, 2131298741, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131298741, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
    view = finder.findRequiredView(source, 2131298697, "field 'txt'");
    target.txt = finder.castView(view, 2131298697, "field 'txt'");
    view = finder.findRequiredView(source, 2131298426, "field 'tvAbout'");
    target.tvAbout = finder.castView(view, 2131298426, "field 'tvAbout'");
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.txtTitle = null;
    target.layBack = null;
    target.txt = null;
    target.tvAbout = null;
  }
}
