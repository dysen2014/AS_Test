// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyAdapter$RelevanceAdapter$ViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.search.MyAdapter.RelevanceAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131297935, "field 'picture'");
    target.picture = finder.castView(view, 2131297935, "field 'picture'");
    view = finder.findRequiredView(source, 2131296809, "field 'cusName'");
    target.cusName = finder.castView(view, 2131296809, "field 'cusName'");
    view = finder.findRequiredView(source, 2131298104, "field 'relationship'");
    target.relationship = finder.castView(view, 2131298104, "field 'relationship'");
    view = finder.findRequiredView(source, 2131296524, "field 'businessTypes'");
    target.businessTypes = finder.castView(view, 2131296524, "field 'businessTypes'");
    view = finder.findRequiredView(source, 2131297882, "field 'overdueAmount'");
    target.overdueAmount = finder.castView(view, 2131297882, "field 'overdueAmount'");
    view = finder.findRequiredView(source, 2131297634, "field 'loanAmount'");
    target.loanAmount = finder.castView(view, 2131297634, "field 'loanAmount'");
    view = finder.findRequiredView(source, 2131297883, "field 'overdueDays'");
    target.overdueDays = finder.castView(view, 2131297883, "field 'overdueDays'");
    view = finder.findRequiredView(source, 2131297581, "field 'llLay'");
    target.llLay = finder.castView(view, 2131297581, "field 'llLay'");
  }

  @Override public void unbind(T target) {
    target.picture = null;
    target.cusName = null;
    target.relationship = null;
    target.businessTypes = null;
    target.overdueAmount = null;
    target.loanAmount = null;
    target.overdueDays = null;
    target.llLay = null;
  }
}
