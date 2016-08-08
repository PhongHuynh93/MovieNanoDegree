package dhbk.android.movienanodegree.io;

/**
 * Created by phongdth.ky on 7/15/2016.
 */
public interface MovieSearchServerCallback {
    /**
     * change state of loading
     * @param b indicate the state of loading
     */
    void onSetLoading(boolean b);

    /**
     * a method indicate that we are successful to download and save datas to db.
     * so what to do next.
     */
    void onDownloadAndSaveToDbSuccess();


    /**
     * a method indicate that we are failed to download and save datas to db.
     * so what to do next.
     */
    void onDownloadAndSaveToDbFail();
}
