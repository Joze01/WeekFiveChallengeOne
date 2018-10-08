package com.applaudostudio.weekfivechallangeone.persistence.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/***
 * Class to use has contrat on helpers and table managers
 */
public class TheGuardianContact {

    public TheGuardianContact() {
    }

    public static final String CONTENT_AUTHORITY = "com.applaudostudio.persistence.news";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_NEWS = "news";
    public static final String PATH_READ_ME_LATTER = "readme";


    /***
     * table News Elements
     */
    public static final class News implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NEWS);
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NEW_ID = "newId";
        public static final String COLUMN_NEW_HEAD_LINE = "newHeadline";
        public static final String COLUMN_NEW_BODY_TEXT = "newBodyText";
        public static final String COLUMN_NEW_WEB_URL = "newWebUrl";
        public static final String COLUMN_NEW_THUMBNAIL = "newThumbnail";
        public static final String COLUMN_NEW_CATEGORY = "newCategory";
    }

    /***
     * Table readmeLatter elements
     */
    public static final class ReadMeLatter implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_READ_ME_LATTER);
        public static final String TABLE_NAME = "readMeLatter";
        public static final String COLUMN_NEW_ID = "newId";
        public static final String COLUMN_NEW_HEAD_LINE = "newHeadline";
        public static final String COLUMN_NEW_BODY_TEXT = "newBodyText";
        public static final String COLUMN_NEW_WEB_URL = "newWebUrl";
        public static final String COLUMN_NEW_THUMBNAIL = "newThumbnail";
        public static final String COLUMN_NEW_CATEGORY = "newCategory";

    }

}
