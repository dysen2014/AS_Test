// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.relevance_search;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RelevanceSearchActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.relevance_search.RelevanceSearchActivity> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131296870, "field 'customerType'");
    target.customerType = finder.castView(view, 2131296870, "field 'customerType'");
    view = finder.findRequiredView(source, 2131296869, "field 'customerName'");
    target.customerName = finder.castView(view, 2131296869, "field 'customerName'");
    view = finder.findRequiredView(source, 2131297506, "field 'ivOcr' and method 'onViewClicked'");
    target.ivOcr = finder.castView(view, 2131297506, "field 'ivOcr'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296648, "field 'cardType'");
    target.cardType = finder.castView(view, 2131296648, "field 'cardType'");
    view = finder.findRequiredView(source, 2131296647, "field 'cardNumber'");
    target.cardNumber = finder.castView(view, 2131296647, "field 'cardNumber'");
    view = finder.findRequiredView(source, 2131297887, "field 'ownedOutlets'");
    target.ownedOutlets = finder.castView(view, 2131297887, "field 'ownedOutlets'");
    view = finder.findRequiredView(source, 2131298376, "field 'telephoneNumber'");
    target.telephoneNumber = finder.castView(view, 2131298376, "field 'telephoneNumber'");
    view = finder.findRequiredView(source, 2131297541, "field 'level'");
    target.level = finder.castView(view, 2131297541, "field 'level'");
    view = finder.findRequiredView(source, 2131298328, "field 'submit' and method 'onViewClicked'");
    target.submit = finder.castView(view, 2131298328, "field 'submit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296473, "field 'btnOwnedOutlets' and method 'onViewClicked'");
    target.btnOwnedOutlets = finder.castView(view, 2131296473, "field 'btnOwnedOutlets'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297603, "field 'llOwnedOutlets' and method 'onViewClicked'");
    target.llOwnedOutlets = finder.castView(view, 2131297603, "field 'llOwnedOutlets'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.txtBack = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txt = null;
    target.customerType = null;
    target.customerName = null;
    target.ivOcr = null;
    target.cardType = null;
    target.cardNumber = null;
    target.ownedOutlets = null;
    target.telephoneNumber = null;
    target.level = null;
    target.submit = null;
    target.btnOwnedOutlets = null;
    target.llOwnedOutlets = null;
  }
}
