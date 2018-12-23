// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.ui.credit;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CardEntryActivity$$ViewBinder<T extends com.pactera.financialmanager.ui.credit.CardEntryActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298034, "field 'queueCode'");
    target.queueCode = finder.castView(view, 2131298034, "field 'queueCode'");
    view = finder.findRequiredView(source, 2131298705, "field 'txtClose' and method 'onViewClicked'");
    target.txtClose = finder.castView(view, 2131298705, "field 'txtClose'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296437, "field 'btnBlueConn' and method 'onViewClicked'");
    target.btnBlueConn = finder.castView(view, 2131296437, "field 'btnBlueConn'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296442, "field 'btnCancel' and method 'onViewClicked'");
    target.btnCancel = finder.castView(view, 2131296442, "field 'btnCancel'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297038, "field 'editId'");
    target.editId = finder.castView(view, 2131297038, "field 'editId'");
    view = finder.findRequiredView(source, 2131297063, "field 'editName'");
    target.editName = finder.castView(view, 2131297063, "field 'editName'");
    view = finder.findRequiredView(source, 2131298044, "field 'radioMaster'");
    target.radioMaster = finder.castView(view, 2131298044, "field 'radioMaster'");
    view = finder.findRequiredView(source, 2131298045, "field 'radioSupplementary'");
    target.radioSupplementary = finder.castView(view, 2131298045, "field 'radioSupplementary'");
    view = finder.findRequiredView(source, 2131298039, "field 'radioBoth'");
    target.radioBoth = finder.castView(view, 2131298039, "field 'radioBoth'");
    view = finder.findRequiredView(source, 2131298152, "field 'rgroupApplyType'");
    target.rgroupApplyType = finder.castView(view, 2131298152, "field 'rgroupApplyType'");
    view = finder.findRequiredView(source, 2131296463, "field 'btnGetId' and method 'onViewClicked'");
    target.btnGetId = finder.castView(view, 2131296463, "field 'btnGetId'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296443, "field 'btnCheck' and method 'onViewClicked'");
    target.btnCheck = finder.castView(view, 2131296443, "field 'btnCheck'");
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
    target.queueCode = null;
    target.txtClose = null;
    target.btnBlueConn = null;
    target.btnCancel = null;
    target.editId = null;
    target.editName = null;
    target.radioMaster = null;
    target.radioSupplementary = null;
    target.radioBoth = null;
    target.rgroupApplyType = null;
    target.btnGetId = null;
    target.btnCheck = null;
  }
}
