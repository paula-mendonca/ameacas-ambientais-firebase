package com.paulamendonca.ameacasambientaisfirebase;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Ameaca implements Serializable {
        private String end;
        private String data;
        private String desc;
        private String image;

        public Ameaca () {}

        public String getEnd () { return end; }
        public void setEnd (String end) { this.end = end; }

        public String getData() { return data; }
        public void setData(String data) { this.data = data; }

        public String getDesc() { return desc; }

        public void setDesc(String Desc) { this.desc = Desc; }

        @NonNull
        @Override
        public String toString() { return end + " " + data + " " + desc; }

        public String getImage () {
            return this.image;
        }
        public void setImage(String image) {
            this.image = image;
        }
}


