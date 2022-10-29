package entity;

import com.google.gson.annotations.SerializedName;

class UserImage {

    @SerializedName("height")
    int height;
    @SerializedName("width")
    int width;
    @SerializedName("url")
    String Url;
}

class UserExternalUrls {
    @SerializedName("spotify")
    String url;

    public String getUrl() {
        return url;
    }
}

class UserFollowers {
    @SerializedName("href")
    String href;
    @SerializedName("total")
    Long total;
}

public class User {

    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    @SerializedName("followers")
    private Followers followers;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    public String getDisplayName() {return displayName;}

    public String getId(){return id;}

    public String getType(){return type;}

    public String getExternalUrls() {
        return externalUrls.url;
    }

    public Followers getFollowers() {
        return followers;
    }
}


/**
 * User Profile response
 * {
 *         "display_name": "Amal Alexander Tharyan",
 *         "external_urls": {
 *         "spotify": "https://open.spotify.com/user/22w3l23dfnpu5vi2th3gvdbvi"
 *         },
 *         "followers": {
 *         "href": null,
 *         "total": 20
 *         },
 *         "href": "https://api.spotify.com/v1/users/22w3l23dfnpu5vi2th3gvdbvi",
 *         "id": "22w3l23dfnpu5vi2th3gvdbvi",
 *         "images": [
 *         {
 *         "height": null,
 *         "url": "https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=669918246377762&height=300&width=300&ext=1669629403&hash=AeTyTi7jMQQJce_ihDU",
 *         "width": null
 *         }
 *         ],
 *         "type": "user",
 *         "uri": "spotify:user:22w3l23dfnpu5vi2th3gvdbvi"
 *         }
 */
