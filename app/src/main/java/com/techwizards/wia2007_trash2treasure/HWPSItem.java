package com.techwizards.wia2007_trash2treasure;

import java.util.List;

public class HWPSItem {
    String houseType;
    List<CollectionType> collectionType;

    public HWPSItem(String houseType, List<CollectionType> collectionType) {
        this.houseType = houseType;
        this.collectionType = collectionType;
    }

    public String getHouseType() {
        return houseType;
    }

    public List<CollectionType> getCollectionType() {
        return collectionType;
    }

    public static class CollectionType {
        String type;
        String collectionDay;
        String nextCollectionDate;
        String icon;

        public CollectionType(String type, String collectionDay, String nextCollectionDate, String icon) {
            this.type = type;
            this.collectionDay = collectionDay;
            this.nextCollectionDate = nextCollectionDate;
            this.icon = icon;
        }

        public String getType() {
            return type;
        }

        public String getCollectionDay() {
            return collectionDay;
        }

        public String getNextCollectionDate() {
            return nextCollectionDate;
        }

        public String getIcon() {
            return icon;
        }
    }
}
