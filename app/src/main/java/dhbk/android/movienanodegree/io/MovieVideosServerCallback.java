package dhbk.android.movienanodegree.io;

import java.util.ArrayList;

import dhbk.android.movienanodegree.models.MovieVideosResponse;

/**
 * Created by phongdth.ky on 8/10/2016.
 */
public interface MovieVideosServerCallback {
    /**
     * call SetShowOrHideVideoList() to hide or show the video recyclerlist
     */
    void onSetShowOrHideVideoList();

    /**
     * change the data in video adapter
     * @param movieVideos the new datas need to swap with old data
     */
    void onUpdateVideoAdapter(ArrayList<MovieVideosResponse.MovieVideo> movieVideos);
}
