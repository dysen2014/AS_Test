// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.study.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FilestudyAdapter$FilesAdapter$ViewHolder$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.study.adapter.FilestudyAdapter.FilesAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298662, "field 'fileType'");
    target.fileType = finder.castView(view, 2131298662, "field 'fileType'");
    view = finder.findRequiredView(source, 2131298640, "field 'fileName'");
    target.fileName = finder.castView(view, 2131298640, "field 'fileName'");
    view = finder.findRequiredView(source, 2131296469, "field 'btnOpenFile'");
    target.btnOpenFile = finder.castView(view, 2131296469, "field 'btnOpenFile'");
    view = finder.findRequiredView(source, 2131297501, "field 'img'");
    target.img = finder.castView(view, 2131297501, "field 'img'");
    view = finder.findRequiredView(source, 2131297581, "field 'llLay'");
    target.llLay = finder.castView(view, 2131297581, "field 'llLay'");
  }

  @Override public void unbind(T target) {
    target.fileType = null;
    target.fileName = null;
    target.btnOpenFile = null;
    target.img = null;
    target.llLay = null;
  }
}
