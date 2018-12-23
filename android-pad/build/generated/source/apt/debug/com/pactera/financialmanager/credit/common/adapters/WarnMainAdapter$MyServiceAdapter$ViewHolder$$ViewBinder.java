// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.common.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WarnMainAdapter$MyServiceAdapter$ViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.common.adapters.WarnMainAdapter.MyServiceAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298538, "field 'tvHideData'");
    target.tvHideData = finder.castView(view, 2131298538, "field 'tvHideData'");
    view = finder.findRequiredView(source, 2131298744, "field 'uberPgsview'");
    target.uberPgsview = finder.castView(view, 2131298744, "field 'uberPgsview'");
    view = finder.findRequiredView(source, 2131298653, "field 'tvTitleColor'");
    target.tvTitleColor = finder.castView(view, 2131298653, "field 'tvTitleColor'");
    view = finder.findRequiredView(source, 2131298640, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131298640, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131297610, "field 'llTitle'");
    target.llTitle = finder.castView(view, 2131297610, "field 'llTitle'");
    view = finder.findRequiredView(source, 2131298012, "field 'pullLoadMore'");
    target.pullLoadMore = finder.castView(view, 2131298012, "field 'pullLoadMore'");
    view = finder.findRequiredView(source, 2131297569, "field 'llCard'");
    target.llCard = finder.castView(view, 2131297569, "field 'llCard'");
    view = finder.findRequiredView(source, 2131297572, "field 'llContentLay'");
    target.llContentLay = finder.castView(view, 2131297572, "field 'llContentLay'");
  }

  @Override public void unbind(T target) {
    target.tvHideData = null;
    target.uberPgsview = null;
    target.tvTitleColor = null;
    target.tvTitle = null;
    target.llTitle = null;
    target.pullLoadMore = null;
    target.llCard = null;
    target.llContentLay = null;
  }
}
