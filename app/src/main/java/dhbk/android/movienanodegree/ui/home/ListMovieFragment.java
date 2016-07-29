package dhbk.android.movienanodegree.ui.home;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dhbk.android.movienanodegree.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * depends on position via Intent:
 * 0: it show the most popular movies.
 * 1: it show the highest rated movies.
 * 2: it show the most rated movies.
 */
public class ListMovieFragment extends Fragment implements ListMovieContract.View{
    private static final String ARG_POSITION = "position";
    private ListMovieContract.Presenter mPresenter;

    public ListMovieFragment() {
        // Required empty public constructor
    }


    @NonNull
    public static ListMovieFragment newInstance(int position) {
        ListMovieFragment listMovieFragment = new ListMovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        listMovieFragment.setArguments(args);
        return listMovieFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void setPresenter(ListMovieContract.Presenter presenter) {
        checkNotNull(presenter, "Present must not null");
        mPresenter = presenter;
    }
}
