package TheMovieDB;

import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import javafx.scene.image.Image;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class MovieHandler {

    private static TmdbSearch gr_searchEntity;
    private final List<MovieDb> ma_movieList;
    private int mv_currentMovieCounter;

    static {
        try {
            gr_searchEntity = new TmdbApi(Main.readFromConfig("[API Key]")).getSearch();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MovieHandler(String iv_folderName) throws FileNotFoundException {
        MovieResultsPage ma_movieResults = gr_searchEntity.searchMovie(iv_folderName,null,"de",true,1);
        ma_movieList = ma_movieResults.getResults();
        Iterator<MovieDb> la_movieIterator = ma_movieList.iterator();
        //Delete movies without info / poster
        while(la_movieIterator.hasNext()) {
            MovieDb lr_currentMovie = la_movieIterator.next();
            if (lr_currentMovie.getPosterPath() == null) {
                la_movieIterator.remove();
            }
        }

        mv_currentMovieCounter = 0;
    }

    private boolean hasEntries() {
        boolean lv_hasEntries;
        return !ma_movieList.isEmpty();
    }

    public void nextMovie() {
        if (mv_currentMovieCounter+1 < ma_movieList.size()) {
            mv_currentMovieCounter++;
        } else {
            mv_currentMovieCounter = 0;
        }
    }

    public void previousMovie() {
        if (mv_currentMovieCounter == 0) {
            mv_currentMovieCounter = ma_movieList.size() -1;
        } else {
            mv_currentMovieCounter--;
        }
    }

    public String getMovieName() {
        MovieDb lr_movie =  ma_movieList.get(mv_currentMovieCounter);
        String lv_fullNameYear = lr_movie.getTitle() + " (" + lr_movie.getReleaseDate().substring(0,4) + ")";
        return lv_fullNameYear;
    }

    public Image getMoviePoster() {
        String lv_pathToPoster = "https://image.tmdb.org/t/p/original" + ma_movieList.get(mv_currentMovieCounter).getPosterPath();
        return new Image(lv_pathToPoster, 210, 280, false, true);
    }



}
