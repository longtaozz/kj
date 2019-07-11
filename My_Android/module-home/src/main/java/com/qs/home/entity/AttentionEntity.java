package com.qs.home.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AttentionEntity {

    private String nextPageToken;
    private String prevPageToken;
    private int requestCount;
    private int responseCount;
    private int totalResults;
    private List<ItemsEntity> items;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getPrevPageToken() {
        return prevPageToken;
    }

    public void setPrevPageToken(String prevPageToken) {
        this.prevPageToken = prevPageToken;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public int getResponseCount() {
        return responseCount;
    }

    public void setResponseCount(int responseCount) {
        this.responseCount = responseCount;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public static class ItemsEntity implements Parcelable {
        private String detail;
        private String href;
        private int id;
        private String img;
        private String name;
        private String pubDate;
        private int type;
        private String itemType;
        private List<ItemBanner> images;

        public ItemsEntity() {
        }

        protected ItemsEntity(Parcel in) {
            detail = in.readString();
            href = in.readString();
            id = in.readInt();
            img = in.readString();
            name = in.readString();
            pubDate = in.readString();
            type = in.readInt();
            itemType = in.readString();
            images = in.createTypedArrayList(ItemBanner.CREATOR);
        }

        public static final Creator<ItemsEntity> CREATOR = new Creator<ItemsEntity>() {
            @Override
            public ItemsEntity createFromParcel(Parcel in) {
                return new ItemsEntity(in);
            }

            @Override
            public ItemsEntity[] newArray(int size) {
                return new ItemsEntity[size];
            }
        };

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public List<ItemBanner> getImages() {
            return images;
        }

        public void setImages(List<ItemBanner> images) {
            this.images = images;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(detail);
            dest.writeString(href);
            dest.writeInt(id);
            dest.writeString(img);
            dest.writeString(name);
            dest.writeString(pubDate);
            dest.writeInt(type);
            dest.writeString(itemType);
            dest.writeTypedList(images);
        }

        public static class ItemBanner implements Parcelable {

            private String id;
            private String image;
            private String link;

            public ItemBanner() {
            }

            protected ItemBanner(Parcel in) {
                id = in.readString();
                image = in.readString();
                link = in.readString();
            }

            public static final Creator<ItemBanner> CREATOR = new Creator<ItemBanner>() {
                @Override
                public ItemBanner createFromParcel(Parcel in) {
                    return new ItemBanner(in);
                }

                @Override
                public ItemBanner[] newArray(int size) {
                    return new ItemBanner[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(image);
                dest.writeString(link);
            }
        }

    }
}
