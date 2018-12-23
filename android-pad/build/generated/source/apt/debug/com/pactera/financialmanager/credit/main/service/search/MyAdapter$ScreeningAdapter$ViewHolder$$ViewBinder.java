// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyAdapter$ScreeningAdapter$ViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.search.MyAdapter.ScreeningAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131297935, "field 'picture'");
    target.picture = finder.castView(view, 2131297935, "field 'picture'");
    view = finder.findRequiredView(source, 2131296809, "field 'cusName'");
    target.cusName = finder.castView(view, 2131296809, "field 'cusName'");
    view = finder.findRequiredView(source, 2131296657, "field 'certId'");
    target.certId = finder.castView(view, 2131296657, "field 'certId'");
    view = finder.findRequiredView(source, 2131298106, "field 'remarkDate'");
    target.remarkDate = finder.castView(view, 2131298106, "field 'remarkDate'");
    view = finder.findRequiredView(source, 2131298108, "field 'remarkName'");
    target.remarkName = finder.castView(view, 2131298108, "field 'remarkName'");
    view = finder.findRequiredView(source, 2131298107, "field 'remarkInfo'");
    target.remarkInfo = finder.castView(view, 2131298107, "field 'remarkInfo'");
    view = finder.findRequiredView(source, 2131297581, "field 'llLay'");
    target.llLay = finder.castView(view, 2131297581, "field 'llLay'");
  }

  @Override public void unbind(T target) {
    target.picture = null;
    target.cusName = null;
    target.certId = null;
    target.remarkDate = null;
    target.remarkName = null;
    target.remarkInfo = null;
    target.llLay = null;
  }
}
