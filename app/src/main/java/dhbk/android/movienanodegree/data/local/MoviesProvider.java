package dhbk.android.movienanodegree.data.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.HashSet;

public class MoviesProvider extends ContentProvider {
    /**
     * Use an int for each URI we will run, this represents the different queries
     * A URI for all rows, and a URI for an individual row:
     */
    private static final int MOVIES = 100;
    private static final int MOVIE_BY_ID = 101;

    private static final int MOST_POPULAR_MOVIES = 201;
    private static final int HIGHEST_RATED_MOVIES = 202;

    private static final int MOST_RATED_MOVIES = 203;
    private static final int FAVORITES = 300;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDbHelper mDbHelper;

    // used to throw an error
    private static final String FAILED_TO_INSERT_ROW_INTO = "Failed to insert row into ";
    // movies._id = ?
    private static final String MOVIE_ID_SELECTION = MoviesContract.MovieEntry.TABLE_NAME + "." + MoviesContract.MovieEntry._ID + " = ? ";


    /**
     * a URIMatcher that will take in a URI and match it to the appropriate integer identifier we just defined
     * Builds a UriMatcher that is used to determine which database request is being made.
     * // All paths to the UriMatcher have a corresponding code to return when a match is found (the ints above).
     * // tức là khi ta access content provider này bằng uri và nó thấy khớp với uri, nó se return 1 số integer, ta dùng số này để
     * // access cho đúng data
     */
    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        // if we see a URI that ends with a given path, it will match with the URI for that table
        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES, MOVIES);
        // if an id is appended to the path we are looking for a row with that id.
        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES + "/#", MOVIE_BY_ID); // get a item (a row)
        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES + "/" + MoviesContract.PATH_MOST_POPULAR, MOST_POPULAR_MOVIES); // get a popular table
        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES + "/" + MoviesContract.PATH_HIGHEST_RATED, HIGHEST_RATED_MOVIES);
        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES + "/" + MoviesContract.PATH_MOST_RATED, MOST_RATED_MOVIES);
        uriMatcher.addURI(authority, MoviesContract.PATH_MOVIES + "/" + MoviesContract.PATH_FAVORITES, FAVORITES);

        return uriMatcher;
    }

    /**
     * we can define our other class variables such as our SQLiteOpenHelper which is used to access the database itself
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new MoviesDbHelper(getContext());
        return true;
    }

    /**
     * The getType method is used to find the MIME type of the results, either a directory of multiple results, or an individual item
     *
     * @param uri
     * @return
     */
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return MoviesContract.MovieEntry.CONTENT_DIR_TYPE;
            case MOVIE_BY_ID:
                return MoviesContract.MovieEntry.CONTENT_ITEM_TYPE;
            case MOST_POPULAR_MOVIES:
                return MoviesContract.MostPopularMovies.CONTENT_DIR_TYPE;
            case HIGHEST_RATED_MOVIES:
                return MoviesContract.HighestRatedMovies.CONTENT_DIR_TYPE;
            case MOST_RATED_MOVIES:
                return MoviesContract.MostRatedMovies.CONTENT_DIR_TYPE;
            case FAVORITES:
                return MoviesContract.Favorites.CONTENT_DIR_TYPE;
            default:
                return null;
        }
    }


    /**
     * @param uri           The URI (or table) that should be queried.
     * @param projection    A string array of columns that will be returned in the result set.
     * @param selection     A string defining the criteria for results to be returned.
     * @param selectionArgs Arguments to the above criteria that rows will be checked against.
     * @param sortOrder     A string of the column(s) and order to sort the result set by.
     * @return In order to query the database, we will switch based on the matched URI integer
     * and query the appropriate table as necessary.
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        checkColumns(projection);
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                cursor = mDbHelper.getReadableDatabase().query(
                        MoviesContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_BY_ID:
                cursor = getMovieById(uri, projection, sortOrder);
                break;
            case MOST_POPULAR_MOVIES:
                cursor = getMoviesFromReferenceTable(MoviesContract.MostPopularMovies.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            case HIGHEST_RATED_MOVIES:
                cursor = getMoviesFromReferenceTable(MoviesContract.HighestRatedMovies.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            case MOST_RATED_MOVIES:
                cursor = getMoviesFromReferenceTable(MoviesContract.MostRatedMovies.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            case FAVORITES:
                cursor = getMoviesFromReferenceTable(MoviesContract.Favorites.TABLE_NAME,
                        projection, selection, selectionArgs, sortOrder);
                break;
            default:
                return null;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnUri;
        long id;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                id = db.insertWithOnConflict(MoviesContract.MovieEntry.TABLE_NAME, null,
                        values, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = MoviesContract.MovieEntry.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case MOST_POPULAR_MOVIES:
                id = db.insert(MoviesContract.MostPopularMovies.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = MoviesContract.MostPopularMovies.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case HIGHEST_RATED_MOVIES:
                id = db.insert(MoviesContract.HighestRatedMovies.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = MoviesContract.HighestRatedMovies.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case MOST_RATED_MOVIES:
                id = db.insert(MoviesContract.MostRatedMovies.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = MoviesContract.MostRatedMovies.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            case FAVORITES:
                id = db.insert(MoviesContract.Favorites.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = MoviesContract.Favorites.CONTENT_URI;
                } else {
                    throw new android.database.SQLException(FAILED_TO_INSERT_ROW_INTO + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    /**
     * update one table and then all other table get updates too (because reference )
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                rowsUpdated = db.update(MoviesContract.MovieEntry.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                rowsDeleted = db.delete(MoviesContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_BY_ID:
                long id = MoviesContract.MovieEntry.getIdFromUri(uri);
                rowsDeleted = db.delete(MoviesContract.MovieEntry.TABLE_NAME,
                        MOVIE_ID_SELECTION, new String[]{Long.toString(id)});
                break;
            case MOST_POPULAR_MOVIES:
                rowsDeleted = db.delete(MoviesContract.MostPopularMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case HIGHEST_RATED_MOVIES:
                rowsDeleted = db.delete(MoviesContract.HighestRatedMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case MOST_RATED_MOVIES:
                rowsDeleted = db.delete(MoviesContract.MostRatedMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITES:
                rowsDeleted = db.delete(MoviesContract.Favorites.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    /**
     * Implement this to shut down the ContentProvider instance.
     */
    @Override
    public void shutdown() {
        mDbHelper.close();
        super.shutdown();
    }

    /**
     * add a lot of data into database -> has beginTransaction() method
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long id = db.insertWithOnConflict(MoviesContract.MovieEntry.TABLE_NAME,
                                null, value, SQLiteDatabase.CONFLICT_REPLACE);
                        if (id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    /**
     * get a movie depend on id from db
     * @param uri
     * @param projection
     * @param sortOrder
     * @return
     */
    private Cursor getMovieById(Uri uri, String[] projection, String sortOrder) {
        long id = MoviesContract.MovieEntry.getIdFromUri(uri);
        String selection = MOVIE_ID_SELECTION;
        String[] selectionArgs = new String[]{Long.toString(id)};
        return mDbHelper.getReadableDatabase().query(
                MoviesContract.MovieEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    /**
     * get the data from both tables
     * @see <a href="http://www.w3schools.com/sql/sql_join_inner.asp"></a>
     * @param tableName
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    private Cursor getMoviesFromReferenceTable(String tableName, String[] projection, String selection,
                                               String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        // tableName INNER JOIN movies ON tableName.movie_id = movies._id
        sqLiteQueryBuilder.setTables(tableName + " INNER JOIN " + MoviesContract.MovieEntry.TABLE_NAME + " ON "
                        + tableName + "." + MoviesContract.COLUMN_MOVIE_ID_KEY
                        + " = "
                        + MoviesContract.MovieEntry.TABLE_NAME + "." + MoviesContract.MovieEntry._ID);

        return sqLiteQueryBuilder.query(mDbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    /**
     * this method used to check if the column in request is valid by compare with columns in database
     * @param projection
     */
    private void checkColumns(String[] projection) {
        if (projection != null) {
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(MoviesContract.MovieEntry.getColumns()));
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection.");
            }
        }
    }

}
