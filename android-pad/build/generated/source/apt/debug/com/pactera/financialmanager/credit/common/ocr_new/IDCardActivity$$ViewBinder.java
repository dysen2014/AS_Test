// Generated code from Butter Knife. Do not modify!
package com.pactera.financialmanager.credit.common.ocr_new;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class IDCardActivity$$ViewBinder<T extends com.pactera.financialmanager.credit.common.ocr_new.IDCardActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131297491, "field 'ivHead'");
    target.ivHead = finder.castView(view, 2131297491, "field 'ivHead'");
    view = finder.findRequiredView(source, 2131297260, "field 'galleryButtonFront'");
    target.galleryButtonFront = finder.castView(view, 2131297260, "field 'galleryButtonFront'");
    view = finder.findRequiredView(source, 2131297259, "field 'galleryButtonBack'");
    target.galleryButtonBack = finder.castView(view, 2131297259, "field 'galleryButtonBack'");
    view = finder.findRequiredView(source, 2131297355, "field 'idCardFrontButton'");
    target.idCardFrontButton = finder.castView(view, 2131297355, "field 'idCardFrontButton'");
    view = finder.findRequiredView(source, 2131297356, "field 'idCardFrontButtonNative'");
    target.idCardFrontButtonNative = finder.castView(view, 2131297356, "field 'idCardFrontButtonNative'");
    view = finder.findRequiredView(source, 2131297353, "field 'idCardBackButton'");
    target.idCardBackButton = finder.castView(view, 2131297353, "field 'idCardBackButton'");
    view = finder.findRequiredView(source, 2131297354, "field 'idCardBackButtonNative'");
    target.idCardBackButtonNative = finder.castView(view, 2131297354, "field 'idCardBackButtonNative'");
    view = finder.findRequiredView(source, 2131297456, "field 'infoTextView'");
    target.infoTextView = finder.castView(view, 2131297456, "field 'infoTextView'");
    view = finder.findRequiredView(source, 2131296368, "field 'activityIdcard'");
    target.activityIdcard = finder.castView(view, 2131296368, "field 'activityIdcard'");
  }

  @Override public void unbind(T target) {
    target.ivHead = null;
    target.galleryButtonFront = null;
    target.galleryButtonBack = null;
    target.idCardFrontButton = null;
    target.idCardFrontButtonNative = null;
    target.idCardBackButton = null;
    target.idCardBackButtonNative = null;
    target.infoTextView = null;
    target.activityIdcard = null;
  }
}
