package com.pactera.financialmanager.ui.credit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xh on 2016-01-15.
 */
public class WatcherResultManager {

    private Map<SimpleInputEditWatcher, Boolean> m = new HashMap<SimpleInputEditWatcher, Boolean>();

    public void setOk(SimpleInputEditWatcher w) {
        m.put(w, true);
    }

    public void setError(SimpleInputEditWatcher w) {
        m.put(w, false);
    }

    public boolean isAllOk() {
        for (Boolean b : m.values()) {
            if (!b) return false;
        }
        return true;
    }

}
