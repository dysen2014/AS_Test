package com.dysen.common_res.common.bean;

/**
 * Created by dysen on 1/28/2018.
 */

public class CommonBean {
    public static class CountryBean{
        String id, name;

        public String getId() {
            return id;
        }

        public CountryBean setId(String id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public CountryBean setName(String name) {
            this.name = name;
            return this;
        }
    }
}
