// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MineFragment$$ViewBinder<T extends com.pactera.financialmanager.credit.main.MineFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131297806, "field 'mineTextName'");
    target.mineTextName = finder.castView(view, 2131297806, "field 'mineTextName'");
    view = finder.findRequiredView(source, 2131297807, "field 'mineTextOrg'");
    target.mineTextOrg = finder.castView(view, 2131297807, "field 'mineTextOrg'");
    view = finder.findRequiredView(source, 2131297808, "field 'mineTextSystem'");
    target.mineTextSystem = finder.castView(view, 2131297808, "field 'mineTextSystem'");
    view = finder.findRequiredView(source, 2131297813, "field 'mineTxtSecurity' and method 'onViewClicked'");
    target.mineTxtSecurity = finder.castView(view, 2131297813, "field 'mineTxtSecurity'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297814, "field 'mineTxtSuggest' and method 'onViewClicked'");
    target.mineTxtSuggest = finder.castView(view, 2131297814, "field 'mineTxtSuggest'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297810, "field 'mineTxtFollow' and method 'onViewClicked'");
    target.mineTxtFollow = finder.castView(view, 2131297810, "field 'mineTxtFollow'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297811, "field 'mineTxtMemo' and method 'onViewClicked'");
    target.mineTxtMemo = finder.castView(view, 2131297811, "field 'mineTxtMemo'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297809, "field 'mineTxtAbout' and method 'onViewClicked'");
    target.mineTxtAbout = finder.castView(view, 2131297809, "field 'mineTxtAbout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297812, "field 'mineTxtQuit' and method 'onViewClicked'");
    target.mineTxtQuit = finder.castView(view, 2131297812, "field 'mineTxtQuit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
    view = finder.findRequiredView(source, 2131298741, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131298741, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131298702, "field 'txtBack'");
    target.txtBack = finder.castView(view, 2131298702, "field 'txtBack'");
    view = finder.findRequiredView(source, 2131298697, "field 'txt'");
    target.txt = finder.castView(view, 2131298697, "field 'txt'");
    view = finder.findRequiredView(source, 2131298736, "field 'txtSecurityImg'");
    target.txtSecurityImg = finder.castView(view, 2131298736, "field 'txtSecurityImg'");
    view = finder.findRequiredView(source, 2131298739, "field 'txtSuggestImg'");
    target.txtSuggestImg = finder.castView(view, 2131298739, "field 'txtSuggestImg'");
    view = finder.findRequiredView(source, 2131298714, "field 'txtFollowImg'");
    target.txtFollowImg = finder.castView(view, 2131298714, "field 'txtFollowImg'");
    view = finder.findRequiredView(source, 2131298724, "field 'txtMemoImg'");
    target.txtMemoImg = finder.castView(view, 2131298724, "field 'txtMemoImg'");
    view = finder.findRequiredView(source, 2131298698, "field 'txtAboutImg'");
    target.txtAboutImg = finder.castView(view, 2131298698, "field 'txtAboutImg'");
    view = finder.findRequiredView(source, 2131298696, "field 'txQuitImg'");
    target.txQuitImg = finder.castView(view, 2131298696, "field 'txQuitImg'");
    view = finder.findRequiredView(source, 2131296281, "field 'CRMTextName'");
    target.CRMTextName = finder.castView(view, 2131296281, "field 'CRMTextName'");
  }

  @Override public void unbind(T target) {
    target.mineTextName = null;
    target.mineTextOrg = null;
    target.mineTextSystem = null;
    target.mineTxtSecurity = null;
    target.mineTxtSuggest = null;
    target.mineTxtFollow = null;
    target.mineTxtMemo = null;
    target.mineTxtAbout = null;
    target.mineTxtQuit = null;
    target.layBack = null;
    target.txtTitle = null;
    target.txtBack = null;
    target.txt = null;
    target.txtSecurityImg = null;
    target.txtSuggestImg = null;
    target.txtFollowImg = null;
    target.txtMemoImg = null;
    target.txtAboutImg = null;
    target.txQuitImg = null;
    target.CRMTextName = null;
  }
}
