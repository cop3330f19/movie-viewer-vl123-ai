/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.Video;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import java.util.ArrayList;

/**
 *
 * @author vlawrence6491
 */
public class Movies {
    
    //TMDB API Connection
    private static final TmdbApi CONNECTION = new TmdbApi ("5e36e91d035ee7e2728ce34b6180a554");
    
    //Language
    private static final String LANGUAGE = "en-us";
    
    
    //Return the list of currently playing 
    
   public ArrayList<MovieDb> getMovies(){
       TmdbMovies movies = new TmdbMovies(CONNECTION);
       MovieResultsPage result = movies.getNowPlayingMovies(LANGUAGE, 1, "");
       return (ArrayList<MovieDb>) result.getResults();
   }
   
   public ArrayList<Video> getVideos(int movieId){
       TmdbMovies movies = new TmdbMovies (CONNECTION);
       return (ArrayList<Video>) movies.getVideos(movieId, LANGUAGE);
       
   }
   }
    

