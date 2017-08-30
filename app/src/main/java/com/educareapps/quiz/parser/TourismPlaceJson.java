/*
package com.educareapps.quiz.parser;

import android.content.Context;
import android.util.Log;

import com.educareapps.model.TourismPlaceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

*/
/**
 * Created by RK-REAZ on 8/6/2017.
 *//*


public class TourismPlaceJson {

    Context context;
    String JSONValue;
    JSONArray jsonArray;
    JSONArray jsonPhotoArray;
    public ArrayList<TourismPlaceModel> tourismArr;
    // for avatar text Color static value
    public static final String TAG_PACKAGE_ID = "package_id";
    public static final String TAG_COUNTRY = "country";
    public static final String TAG_PACKAGE_NAME = "package_name";
    public static final String TAG_PACKAGE_HOTEL = "hotel";
    public static final String TAG_PACKAGE_HOTEL_STAR = "hotel_standard";
    public static final String TAG_PLACE = "place";
    public static final String TAG_PRICE = "price_per_person";
    public static final String TAG_PACKAGE_DESCRIPTION = "descriptions";
    public static final String TAG_PHOTO_ARRAY = "photos";
    public static final String TAG_PHOTO_LINK = "photo_link";


    public TourismPlaceJson(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;

    }

    public void parser() {
        Log.d("JsonArray", jsonArray.toString());
        tourismArr = new ArrayList<>();
        try {
//            JSONArray jsonArray = new JSONArray(JSONValue);
            if (jsonArray != null)
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jresponse = jsonArray.getJSONObject(i);
                    TourismPlaceModel tourismPlaceModel = new TourismPlaceModel();

                    tourismPlaceModel.setPackageId(jresponse.getString(TAG_PACKAGE_ID));
                    tourismPlaceModel.setCountry(jresponse.getString(TAG_COUNTRY));
                    tourismPlaceModel.setPackageName(jresponse.getString(TAG_PACKAGE_NAME));

                    tourismPlaceModel.setHotel(jresponse.getString(TAG_PACKAGE_HOTEL));
                    tourismPlaceModel.setHotelStar(jresponse.getString(TAG_PACKAGE_HOTEL_STAR));
                    tourismPlaceModel.setPlace(jresponse.getString(TAG_PLACE));
                    tourismPlaceModel.setPricePerPerson(jresponse.getString(TAG_PRICE));
                    tourismPlaceModel.setDescriptions(jresponse.getString(TAG_PACKAGE_DESCRIPTION));
//                    jsonPhotoArray=new JSONArray();
                    jsonPhotoArray = jresponse.getJSONArray(TAG_PHOTO_ARRAY);
                    tourismPlaceModel.setDescriptions(jresponse.getString(TAG_PACKAGE_DESCRIPTION));


                    ArrayList<String> photoArr = new ArrayList<>();
                    if (jsonArray != null)
                        for (int j = 0; j < jsonPhotoArray.length(); j++) {
                            JSONObject photoJson = jsonPhotoArray.getJSONObject(j);
//                        photoJson.getString(TAG_PHOTO_LINK);
                            photoArr.add(photoJson.getString(TAG_PHOTO_LINK));
                        }
                    tourismPlaceModel.setImageArr(photoArr);
                    tourismArr.add(tourismPlaceModel);
                }


        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

}
*/
