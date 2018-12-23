// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.common.adapters;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WarnMainAdapter$MyContentAdapter$ContentViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.common.adapters.WarnMainAdapter.MyContentAdapter.ContentViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131297501, "field 'ivImg'");
    target.ivImg = finder.castView(view, 2131297501, "field 'ivImg'");
    view = finder.findRequiredView(source, 2131298573, "field 'tvName'");
    target.tvName = finder.castView(view, 2131298573, "field 'tvName'");
    view = finder.findRequiredView(source, 2131298576, "field 'tvNameInfo'");
    target.tvNameInfo = finder.castView(view, 2131298576, "field 'tvNameInfo'");
    view = finder.findRequiredView(source, 2131296650, "field 'cardItem'");
    target.cardItem = finder.castView(view, 2131296650, "field 'cardItem'");
    view = finder.findRequiredView(source, 2131297509, "field 'ivTrials'");
    target.ivTrials = finder.castView(view, 2131297509, "field 'ivTrials'");
    view = finder.findRequiredView(source, 2131297613, "field 'llWarnType'");
    target.llWarnType = finder.castView(view, 2131297613, "field 'llWarnType'");
  }

  @Override public void unbind(T target) {
    target.ivImg = null;
    target.tvName = null;
    target.tvNameInfo = null;
    target.cardItem = null;
    target.ivTrials = null;
    target.llWarnType = null;
  }
}
