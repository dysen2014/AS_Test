// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.rate;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContactCustomerActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.rate.ContactCustomerActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296927, "field 'dataRecyclerView'");
    target.dataRecyclerView = finder.castView(view, 2131296927, "field 'dataRecyclerView'");
    view = finder.findRequiredView(source, 2131298779, "field 'words'");
    target.words = finder.castView(view, 2131298779, "field 'words'");
    view = finder.findRequiredView(source, 2131298413, "field 'tv'");
    target.tv = finder.castView(view, 2131298413, "field 'tv'");
  }

  @Override public void unbind(T target) {
    target.dataRecyclerView = null;
    target.words = null;
    target.tv = null;
  }
}
