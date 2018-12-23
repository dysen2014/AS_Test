// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.main.service.rate;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RateActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.main.service.rate.RateActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131298477, "field 'tvCustName'");
    target.tvCustName = finder.castView(view, 2131298477, "field 'tvCustName'");
    view = finder.findRequiredView(source, 2131297119, "field 'etCustName' and method 'onViewClicked'");
    target.etCustName = finder.castView(view, 2131297119, "field 'etCustName'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298478, "field 'tvCustType'");
    target.tvCustType = finder.castView(view, 2131298478, "field 'tvCustType'");
    view = finder.findRequiredView(source, 2131297120, "field 'etCustType'");
    target.etCustType = finder.castView(view, 2131297120, "field 'etCustType'");
    view = finder.findRequiredView(source, 2131298456, "field 'tvCertNo'");
    target.tvCertNo = finder.castView(view, 2131298456, "field 'tvCertNo'");
    view = finder.findRequiredView(source, 2131297112, "field 'etCertNo'");
    target.etCertNo = finder.castView(view, 2131297112, "field 'etCertNo'");
    view = finder.findRequiredView(source, 2131298304, "field 'spinner1'");
    target.spinner1 = finder.castView(view, 2131298304, "field 'spinner1'");
    view = finder.findRequiredView(source, 2131298305, "field 'spinner2'");
    target.spinner2 = finder.castView(view, 2131298305, "field 'spinner2'");
    view = finder.findRequiredView(source, 2131298306, "field 'spinner3'");
    target.spinner3 = finder.castView(view, 2131298306, "field 'spinner3'");
    view = finder.findRequiredView(source, 2131298307, "field 'spinner4'");
    target.spinner4 = finder.castView(view, 2131298307, "field 'spinner4'");
    view = finder.findRequiredView(source, 2131298741, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131298741, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131298702, "field 'txtBack'");
    target.txtBack = finder.castView(view, 2131298702, "field 'txtBack'");
    view = finder.findRequiredView(source, 2131297513, "field 'layBack'");
    target.layBack = finder.castView(view, 2131297513, "field 'layBack'");
    view = finder.findRequiredView(source, 2131296477, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131296477, "field 'btnSubmit'");
    view = finder.findRequiredView(source, 2131297573, "field 'llCustName' and method 'onViewClicked'");
    target.llCustName = finder.castView(view, 2131297573, "field 'llCustName'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131297574, "field 'llCustType'");
    target.llCustType = finder.castView(view, 2131297574, "field 'llCustType'");
    view = finder.findRequiredView(source, 2131298732, "field 'txtRate'");
    target.txtRate = finder.castView(view, 2131298732, "field 'txtRate'");
    view = finder.findRequiredView(source, 2131297605, "field 'llRate'");
    target.llRate = finder.castView(view, 2131297605, "field 'llRate'");
    view = finder.findRequiredView(source, 2131298448, "field 'tvBusstype'");
    target.tvBusstype = finder.castView(view, 2131298448, "field 'tvBusstype'");
    view = finder.findRequiredView(source, 2131296441, "field 'btnBusstype'");
    target.btnBusstype = finder.castView(view, 2131296441, "field 'btnBusstype'");
    view = finder.findRequiredView(source, 2131297567, "field 'llBusstype' and method 'onViewClicked'");
    target.llBusstype = finder.castView(view, 2131297567, "field 'llBusstype'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131298447, "field 'tvBussrate'");
    target.tvBussrate = finder.castView(view, 2131298447, "field 'tvBussrate'");
    view = finder.findRequiredView(source, 2131296440, "field 'btnBussrate'");
    target.btnBussrate = finder.castView(view, 2131296440, "field 'btnBussrate'");
    view = finder.findRequiredView(source, 2131297566, "field 'llBussrate' and method 'onViewClicked'");
    target.llBussrate = finder.castView(view, 2131297566, "field 'llBussrate'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296543, "field 'bussrate'");
    target.bussrate = finder.castView(view, 2131296543, "field 'bussrate'");
    view = finder.findRequiredView(source, 2131298548, "field 'tvIndustry'");
    target.tvIndustry = finder.castView(view, 2131298548, "field 'tvIndustry'");
  }

  @Override public void unbind(T target) {
    target.tvCustName = null;
    target.etCustName = null;
    target.tvCustType = null;
    target.etCustType = null;
    target.tvCertNo = null;
    target.etCertNo = null;
    target.spinner1 = null;
    target.spinner2 = null;
    target.spinner3 = null;
    target.spinner4 = null;
    target.txtTitle = null;
    target.txtBack = null;
    target.layBack = null;
    target.btnSubmit = null;
    target.llCustName = null;
    target.llCustType = null;
    target.txtRate = null;
    target.llRate = null;
    target.tvBusstype = null;
    target.btnBusstype = null;
    target.llBusstype = null;
    target.tvBussrate = null;
    target.btnBussrate = null;
    target.llBussrate = null;
    target.bussrate = null;
    target.tvIndustry = null;
  }
}
